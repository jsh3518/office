package com.office.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.Menu;
import com.office.entity.Role;
import com.office.entity.User;
import com.office.service.MenuService;
import com.office.service.UserService;
import com.office.util.Const;
import com.office.util.MD5Util;
import com.office.util.RightsHelper;
import com.office.util.Tools;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;
	
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginGet(){
		return "login";
	}
	
	/**
	 * 请求登录，验证用户
	 * @param session
	 * @param loginname
	 * @param password
	 * @param code
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView loginPost(HttpSession session,@RequestParam String loginname,@RequestParam String password,@RequestParam String code)
			throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);
		ModelAndView mv = new ModelAndView();
		String errInfo = "";
		if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){

			//根据用户登录名查询用户列表，如果长度对应1，则存在重复账号；如果长度为0，则查询无结果。
			List<User> userList = userService.getUserByName(loginname);
			if(userList.size()>1){
				errInfo = "存在重复的用户名，请联系系统管理员！";
			}else if(userList.size()==0){
				errInfo = "用户不存在！";
			}else{
				//User user = userService.getUserByNameAndPwd(loginname, password);
				User user = userList.get(0);
				if(user!=null){
					if(MD5Util.validPassword(password, user.getPassword())){
						user.setLastLogin(new Date());
						userService.updateLastLogin(user);
						session.setAttribute(Const.SESSION_USER, user);
						session.removeAttribute(Const.SESSION_SECURITY_CODE);
					}else{
						errInfo = "用户名或密码错误！";
					}
				}else{
					errInfo = "用户不存在！";
				}
			}
		}else{
			errInfo = "验证码输入有误！";
		}
		if(Tools.isEmpty(errInfo)){
			mv.setViewName("redirect:index.html");
		}else{
			mv.addObject("errInfo", errInfo);
			mv.addObject("loginname",loginname);
			mv.addObject("password",password);
			mv.setViewName("login");
		}
		return mv;
	}
	
	/**
	 * 访问系统首页
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(HttpSession session,Model model){
		User user = (User)session.getAttribute(Const.SESSION_USER);
		user = userService.getUserAndRoleById(user.getUserId());
		Role role = user.getRole();
		String roleRights = role!=null ? role.getRights() : "";
		String userRights = user.getRights();
		//避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
		session.setAttribute(Const.SESSION_ROLE_RIGHTS, roleRights); //将角色权限存入session
		session.setAttribute(Const.SESSION_USER_RIGHTS, userRights); //将用户权限存入session
		
		List<Menu> menuList = menuService.listAllMenu();
		if(Tools.notEmpty(userRights) || Tools.notEmpty(roleRights)){
			for(Menu menu : menuList){
				menu.setHasMenu(RightsHelper.testRights(userRights, menu.getMenuId()) || RightsHelper.testRights(roleRights, menu.getMenuId()));
				if(menu.isHasMenu()){
					List<Menu> subMenuList = menu.getSubMenu();
					for(Menu sub : subMenuList){
						sub.setHasMenu(RightsHelper.testRights(userRights, sub.getMenuId()) || RightsHelper.testRights(roleRights, sub.getMenuId()));
					}
				}
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("menuList", menuList);
		return "index";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/default")
	public String defaultPage(){
		return "default";
	}
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(Const.SESSION_USER_RIGHTS);
		return "login";
	}
}
