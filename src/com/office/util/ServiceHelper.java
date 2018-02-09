package com.office.util;

import com.office.service.MenuService;
import com.office.service.OrganService;
import com.office.service.RoleService;
import com.office.service.UserService;


/**
 * @author lihel
 * 获取Spring容器中的service bean
 */
public final class ServiceHelper {
	
	public static Object getService(String serviceName){
		//WebApplicationContextUtils.
		return Const.WEB_APP_CONTEXT.getBean(serviceName);
	}
	
	public static UserService getUserService(){
		return (UserService) getService("userService");
	}
	
	public static RoleService getRoleService(){
		return (RoleService) getService("roleService");
	}
	
	public static MenuService getMenuService(){
		return (MenuService) getService("menuService");
	}
	
	public static OrganService getOrganService(){
		return (OrganService) getService("organService");
	}
}
