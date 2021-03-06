package com.office.service;

import java.util.List;

import com.office.entity.User;

public interface UserService {
	User getUserById(Integer userId);
	
	boolean insertUser(User user);
	
	void updateUser(User user);
	
	User getUserByNameAndPwd(String username,String password);
	
	void updateUserBaseInfo(User user);
	
	void updateUserRights(User user);
	
	void deleteUser(int userId);
	
	List<User> listPageUser(User user);
	
	void updateLastLogin(User user);
	
	User getUserAndRoleById(Integer userId);
	
	List<User> listAllUser();
	
	List<User> getUserByName(String loginname);
	
	int getCount(User user);
	
	List<User> getUsers(User user);
	
	void updateUserStatus(User user);
	
	void updatePassword(Integer userId,String password);
}
