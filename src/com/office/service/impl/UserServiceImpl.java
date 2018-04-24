package com.office.service.impl;

import java.util.List;

import com.office.entity.User;
import com.office.mapper.UserMapper;
import com.office.service.UserService;

public class UserServiceImpl implements UserService {

	private UserMapper userMapper;
	
	public User getUserById(Integer userId) {
		return userMapper.getUserById(userId);
	}

	public boolean insertUser(User user) {
		int count = userMapper.getCountByName(user.getLoginname());
		if(count>0){
			return false;
		}else{
			userMapper.insertUser(user);
			return true;
		}
		
	}

	public int getCount(User user) {
		return userMapper.getCount(user);
	}
	
	public List<User> listPageUser(User user){
		return userMapper.listPageUser(user);
	}

	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

	public void updateUserBaseInfo(User user){
		userMapper.updateUserBaseInfo(user);
	}
	
	public void updateUserRights(User user){
		userMapper.updateUserRights(user);
	}
	
	/**
	 * 根据用户登录名查询用户信息
	 * @param loginname
	 * @return
	 */
	public List<User> getUserByName(String loginname) {
		return userMapper.getUserByName(loginname);
	}
	
	public User getUserByNameAndPwd(String loginname, String password) {
		User user = new User();
		user.setLoginname(loginname);
		user.setPassword(password);
		return userMapper.getUserInfo(user);
	}
	
	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void deleteUser(int userId){
		userMapper.deleteUser(userId);
	}

	public User getUserAndRoleById(Integer userId) {
		return userMapper.getUserAndRoleById(userId);
	}

	public void updateLastLogin(User user) {
		userMapper.updateLastLogin(user);
	}

	public List<User> listAllUser() {
		return userMapper.listAllUser();
	}
	
	public List<User> getUsers(User user) {
		return userMapper.getUsers(user);
	}
	
	public void updateUserStatus(User user){
		userMapper.updateUserStatus(user);
	}

	public void updatePassword(Integer userId, String password) {
		User user = new User();
		user.setUserId(userId);
		user.setPassword(password);
		userMapper.updateUser(user);
	}
}
