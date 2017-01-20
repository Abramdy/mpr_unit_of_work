package repo;

import java.util.List;

import domain.*;

public interface IEnumerationValueRepository extends IRepository<EnumerationValue> {

	public List<EnumerationValue> withName(String name);
	public List<EnumerationValue> withIntKey(int intKey, String name);
	public List<EnumerationValue> withStringKey(String stringKey, String name);

}	
