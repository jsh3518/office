package com.office.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.entity.Role;
import com.office.entity.User;
import com.office.mapper.RoleMapper;
import com.office.mapper.UserMapper;
import com.office.service.RoleService;

@Service("RoleService")
public class RoleServiceImpl implements RoleService{
	@Resource(name="roleMapper")
	@Autowired
	private RoleMapper roleMapper;
	@Resource(name="userMapper")
	@Autowired
	private UserMapper userMapper;
	
	public List<Role> listAllRoles() {
		return roleMapper.listAllRoles();
	}

	public String deleteRoleById(int roleId) {
		User user = new User();
		user.setRoleId(roleId);
		try{
			if(userMapper.getCount(user)>0)
				return "该角色正在使用，请对用户解除角色后再进行删除操作！";
			else{
				roleMapper.deleteRoleById(roleId);
				return "删除成功！";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "删除失败！";
		}
	}

	public Role getRoleById(int roleId) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleById(roleId);
	}

	public boolean insertRole(Role role) {
		// TODO Auto-generated method stub
		if(roleMapper.getCountByName(role)>0)
			return false;
		else{
			roleMapper.insertRole(role);
			return true;
		}
	}

	public boolean updateRoleBaseInfo(Role role) {
		// TODO Auto-generated method stub
		if(roleMapper.getCountByName(role)>0)
			return false;
		else{
			roleMapper.updateRoleBaseInfo(role);
			return true;
		}
	}
	
	public void updateRoleRights(Role role) {
		// TODO Auto-generated method stub
		roleMapper.updateRoleRights(role);
	}

}
