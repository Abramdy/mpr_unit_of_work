package uow;

import domain.*;

public interface IUnitOfWork {

	public void saveChanges();
	public void undo();

	public void markAsNew(Entity entity, IUnitOfWorkRepository repo);
	public void markAsDeleted(Entity entity, IUnitOfWorkRepository repo);
	public void markAsChanged(Entity entity, IUnitOfWorkRepository repo);

}
