package repo;

import java.util.List;

import domain.*;

public interface IUserRepository extends IRepository<User>{

	public List<User> withLogin(String login);
	public List<User> withLoginAndPassword(String login, String password);
	public void setupPermissions(User user);

}
