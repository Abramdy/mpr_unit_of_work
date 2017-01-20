package repo.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.User;
import mappers.IMapResultSetToEntity;
import repo.IUserRepository;
import uow.IUnitOfWork;

public class HsqlUserRepository extends Repository<User> 
	implements IUserRepository
{
	protected PreparedStatement selectByLogin;
	protected PreparedStatement selectByLoginAndPassword;
	
	protected String selectByLoginSql = "select * from t_sys_users where login = ?";
	protected String selectByLoginAndPasswordSql = "select * from t_sys_users where login = ? and password = ?";

	protected HsqlUserRepository(
		Connection connection,
		IMapResultSetToEntity<User> mapper,
		IUnitOfWork uow
	) {
		super(connection, mapper, uow);
		
		try {
			selectByLogin = connection.prepareStatement(selectByLoginSql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void setUpUpdateQuery(User entity) throws SQLException {
		update.setString(1, entity.getLogin());
		update.setString(2, entity.getPassword());
		update.setInt(3, entity.getId());
	}

	protected void setUpInsertQuery(User entity) throws SQLException {
		insert.setString(1, entity.getLogin());
		insert.setString(2, entity.getPassword());
	}

	protected String getTableName() {
		return "t_sys_users";
	}

	protected String getUpdateQuery() {
		return "update t_sys_users set (login, password) = (?, ?)"
			+ "where id = ?";
	}

	protected String getInsertQuery() {
		return "insert into t_sys_users (login, password) values (?, ?)";
	}

	public List<User> withLogin(String login) {
		List<User> result = new ArrayList<User>();
		
		try {
			selectByID.setString(1, login);
			ResultSet rs = selectByLogin.executeQuery();
			while(rs.next())
			{
				result.add(mapper.map(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public List<User> withLoginAndPassword(String login, String password) {
		List<User> result = new ArrayList<User>();
		
		try {
			selectByLoginAndPassword.setString(1, login);
			selectByLoginAndPassword.setString(2, password);
			ResultSet rs = selectByLoginAndPassword.executeQuery();
			while(rs.next())
			{
				result.add(mapper.map(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void setupPermissions(User user) {
		// Zaimplementowaæ zale¿noœæ miêdzy tablic¹ u¿ytkowników a rolami/uprawnieniami
	}
	
}
