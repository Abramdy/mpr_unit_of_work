package repo;

import java.util.List;

import domain.PagingInfo;

public interface IRepository<TEntity> {
	
	public TEntity withId(int id);
	public List<TEntity> allOnPage(PagingInfo page);
	public void add(TEntity entity);
	public void delete(TEntity entity);
	public void modify(TEntity entity);
	public int count();

}
