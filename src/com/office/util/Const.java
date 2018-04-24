package com.office.util;

import org.springframework.context.ApplicationContext;

public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_USER_ID = "sessionUserId";
	public static final String SESSION_USER_RIGHTS = "sessionUserRights";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String ACCESS_TOKEN = "access_token";//调用微软Office接口需要使用token
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(regedit)|(toRegedit)|(getOrgans)|(checkUser)|(checkCode)|(checkMpnId)).*";	//不对匹配该值的访问路径拦截（正则）
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
}
