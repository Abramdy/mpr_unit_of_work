package repo.impl;

import java.sql.Connection;

import mappers.EnumerationValueMapper;
import mappers.UserMapper;
import repo.IEnumerationValueRepository;
import repo.IRepositoryCatalog;
import repo.IUserRepository;
import uow.IUnitOfWork;

public class RepositoryCatalog implements IRepositoryCatalog {

	private Connection connection;
	private IUnitOfWork uow;
	
	public RepositoryCatalog(Connection connection, IUnitOfWork uow) {
		super();
		this.connection = connection;
		this.uow = uow;
	}
	
	public IUserRepository users() {
		return new HsqlUserRepository(connection, new UserMapper(), uow);
	}

	public IEnumerationValueRepository enumerations() {
		return new HsqlEnumerationValueRepository(connection, new EnumerationValueMapper(), uow);
	}
	
	public void save() {
		uow.saveChanges();
	}

}
