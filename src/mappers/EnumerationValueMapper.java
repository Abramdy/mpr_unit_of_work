package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.EnumerationValue;

public class EnumerationValueMapper implements IMapResultSetToEntity<EnumerationValue> {

	public EnumerationValue map(ResultSet rs) throws SQLException {
		EnumerationValue result = new EnumerationValue();
		result.setId(rs.getInt("id"));
		result.setIntKey(rs.getInt("intKey"));
		result.setStringKey(rs.getString("stringKey"));
		result.setValue(rs.getString("value"));
		result.setName(rs.getString("name"));
		return result;
	}
	
}
