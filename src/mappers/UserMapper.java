package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.User;

public class UserMapper implements IMapResultSetToEntity<User> {

	public User map(ResultSet rs) throws SQLException {
		User result = new User();
		result.setId(rs.getInt("id"));
		result.setLogin(rs.getString("login"));
		result.setPassword(rs.getString("password"));
		return result;
	}
	
}
