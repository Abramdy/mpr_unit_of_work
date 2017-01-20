package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.RolesPermissions;

public class RolesPermissionsMapper implements IMapResultSetToEntity<RolesPermissions> {

	public RolesPermissions map(ResultSet rs) throws SQLException {
		RolesPermissions result = new RolesPermissions();
		result.setRoleId(rs.getInt("user_id"));
		result.setPermissionId(rs.getInt("permission_id"));
		return result;
	}

}
