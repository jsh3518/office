package com.office.service;

import java.util.List;

import com.office.entity.Role;

public interface RoleService {
	List<Role> listAllRoles();
	Role getRoleById(int roleId);
	boolean insertRole(Role role);
	boolean updateRoleBaseInfo(Role role);
	String deleteRoleById(int roleId);
	void updateRoleRights(Role role);
}
