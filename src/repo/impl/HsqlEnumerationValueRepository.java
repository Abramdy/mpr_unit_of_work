package repo.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.EnumerationValue;
import domain.User;
import mappers.IMapResultSetToEntity;
import repo.IEnumerationValueRepository;
import uow.IUnitOfWork;

public class HsqlEnumerationValueRepository extends Repository<EnumerationValue>
	implements IEnumerationValueRepository
{
	protected PreparedStatement selectByName;
	protected PreparedStatement selectByIntKeyAndName;
	protected PreparedStatement selectByStringKeyAndName;
	
	protected String selectByNameSql = "select * from t_sys_enums where name = ?";
	protected String selectByIntKeyAndNameSql = "select * from t_sys_enums where intKey = ? and name = ?";
	protected String selectByStringKeyAndNameSql = "select * from t_sys_enums where stringKey = ? and name = ?";

	protected HsqlEnumerationValueRepository(
		Connection connection,
		IMapResultSetToEntity<EnumerationValue> mapper,
		IUnitOfWork uow
	) {
		super(connection, mapper, uow);
		
		try {
			selectByName = connection.prepareStatement(selectByNameSql);
			selectByIntKeyAndName = connection.prepareStatement(selectByIntKeyAndNameSql);
			selectByStringKeyAndName = connection.prepareStatement(selectByStringKeyAndNameSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void setUpUpdateQuery(EnumerationValue entity) throws SQLException {
		update.setInt(1, entity.getIntKey());
		update.setString(2, entity.getStringKey());
		update.setString(3, entity.getValue());
		update.setString(4, entity.getName());
		update.setInt(5, entity.getId());
	}

	protected void setUpInsertQuery(EnumerationValue entity) throws SQLException {
		insert.setInt(1, entity.getIntKey());
		insert.setString(2, entity.getStringKey());
		insert.setString(3, entity.getValue());
		insert.setString(4, entity.getName());
	}

	protected String getTableName() {
		return "t_sys_enums";
	}

	protected String getUpdateQuery() {
		return "update t_sys_enums set (intKey, stringKey, value, name) = (?, ?, ?, ?)"
			+ "where id = ?";
	}

	protected String getInsertQuery() {
		return "insert into t_sys_enums (intKey, stringKey, value, name) values (?, ?, ?, ?)";
	}

	public List<EnumerationValue> withName(String name) {
		List<EnumerationValue> result = new ArrayList<EnumerationValue>();
		
		try {
			selectByName.setString(1, name);
			ResultSet rs = selectByName.executeQuery();
			while(rs.next())
			{
				result.add(mapper.map(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public List<EnumerationValue> withIntKey(int intKey, String name) {
		List<EnumerationValue> result = new ArrayList<EnumerationValue>();
		
		try {
			selectByIntKeyAndName.setInt(1, intKey);
			selectByIntKeyAndName.setString(2, name);
			ResultSet rs = selectByIntKeyAndName.executeQuery();
			while(rs.next())
			{
				result.add(mapper.map(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public List<EnumerationValue> withStringKey(String stringKey, String name) {
		List<EnumerationValue> result = new ArrayList<EnumerationValue>();
		
		try {
			selectByStringKeyAndName.setString(1, stringKey);
			selectByStringKeyAndName.setString(2, name);
			ResultSet rs = selectByStringKeyAndName.executeQuery();
			while(rs.next())
			{
				result.add(mapper.map(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
