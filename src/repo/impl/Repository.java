package repo.impl;

import mappers.IMapResultSetToEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Entity;
import domain.PagingInfo;
import repo.IRepository;
import uow.IUnitOfWork;
import uow.IUnitOfWorkRepository;

public abstract class Repository<TEntity extends Entity> 
	implements IRepository<TEntity>, IUnitOfWorkRepository
{
	
	protected Connection connection;
	protected IMapResultSetToEntity<TEntity> mapper;
	protected IUnitOfWork uow;

	protected PreparedStatement selectByID;
	protected PreparedStatement insert;
	protected PreparedStatement delete;
	protected PreparedStatement update;
	protected PreparedStatement selectAll;

	protected String selectByIDSql=
		"SELECT * FROM "
		+ getTableName()
		+ " WHERE id=?";
	protected String deleteSql=
		"DELETE FROM "
		+ getTableName()
		+ " WHERE id=?";
	protected String selectAllSql=
		"SELECT * FROM " + getTableName();
	
	protected Repository(
		Connection connection,
		IMapResultSetToEntity<TEntity> mapper,
		IUnitOfWork uow
	) {
		this.uow = uow;
		this.mapper = mapper;
		this.connection = connection;

		try {
			selectByID = connection.prepareStatement(selectByIDSql);
			insert = connection.prepareStatement(getInsertQuery());
			delete = connection.prepareStatement(deleteSql);
			update = connection.prepareStatement(getUpdateQuery());
			selectAll = connection.prepareStatement(selectAllSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public TEntity withId(int id){
		try{
			selectByID.setInt(1, id);
			ResultSet rs = selectByID.executeQuery();
			while(rs.next()){
				return mapper.map(rs);
			}
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return null;
	}

	public List<TEntity> allOnPage(PagingInfo page) {
		// Zmieniæ dzia³anie aby na pobieraæ listê na zawartoœci instancji klasy page
		List<TEntity> result = new ArrayList<TEntity>();
		
		try {
			ResultSet rs = selectAll.executeQuery();
			while(rs.next())
			{
				result.add(mapper.map(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public int count() {
		return 0;
	}
	
	public void add(TEntity entity) {
		uow.markAsNew(entity, this);
	}

	public void delete(TEntity entity) {
		uow.markAsDeleted(entity, this);
	}
	
	public void modify(TEntity entity) {
		uow.markAsChanged(entity, this);
	}

	public void persistAdd(Entity entity) {
		try {
			setUpInsertQuery((TEntity)entity);
			insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void persistUpdate(Entity entity) {
		try {
			setUpUpdateQuery((TEntity)entity);
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void persistDelete(Entity entity) {
		try {
			delete.setInt(1, entity.getId());
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void setUpUpdateQuery(TEntity entity) throws SQLException;
	protected abstract void setUpInsertQuery(TEntity entity) throws SQLException;
	protected abstract String getTableName();
	protected abstract String getUpdateQuery();
	protected abstract String getInsertQuery();
	
}
