package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.UserRoles;

public class UserRolesMapper implements IMapResultSetToEntity<UserRoles> {

	public UserRoles map(ResultSet rs) throws SQLException {
		UserRoles result = new UserRoles();
		result.setUserId(rs.getInt("user_id"));
		result.setRoleId(rs.getInt("role_id"));
		return result;
	}

}
