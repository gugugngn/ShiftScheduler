package dao;

import java.util.List;

import domain.User;

public interface UserDao {
	
	public List<User> findAll() throws Exception;
	
	public User findById(Integer id) throws Exception;
	
	public void insert(User user) throws Exception;
	
	public void update(User user) throws Exception;
	
	public void deleteById(User user) throws Exception;
	
}
