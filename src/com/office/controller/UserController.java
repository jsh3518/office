package com.office.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.Menu;
import com.office.entity.Organ;
import com.office.entity.Role;
import com.office.entity.User;
import com.office.service.MenuService;
import com.office.service.OrganService;
import com.office.service.RoleService;
import com.office.service.UserService;
import com.office.util.Const;
import com.office.util.FileUploadUtil;
import com.office.util.MD5Util;
import com.office.util.RightsHelper;
import com.office.util.Tools;
import com.office.view.UserExcelView;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private OrganService organService;
	
	private static Logger logger = Logger.getLogger(UserController.class);//输出Log日志
	/**
	 * 显示用户列表
	 * @param user
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(User user){
		//List<User> userList = userService.listAllUser(page);
		List<User> userList = userService.listPageUser(user);
		List<Role> roleList = roleService.listAllRoles();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/users");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("user", user);
		return mv;
	}
	
	/**
	 * 请求新增用户页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add")
	public String toAdd(Model model){
		List<Role> roleList = roleService.listAllRoles();
		model.addAttribute("roleList", roleList);
		return "user/user_info";
	}
	
	
	/**
	 * 用户注册初始化页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toRegedit")
	public String toRegedit(Model model){
		List<Organ> provinList = organService.listOrganByLevel(1);
		model.addAttribute("provinList", provinList);
		return "user/user_regist";
	}
	
	/**
	 * 用户注册
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="/regedit",method=RequestMethod.POST)
	public ModelAndView regedit(HttpServletRequest request,HttpSession session,User user) {
		user.setPassword(MD5Util.getEncryptedPwd(user.getPassword()));
		ModelAndView mv = new ModelAndView();
		boolean result = true;
		
        try {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;  
			MultipartFile mfile = (MultipartFile)mRequest.getFile("buslic");
			String filePath = request.getSession().getServletContext().getRealPath("/") + "files"+File.separator;
			logger.info("文件上传路径："+filePath);
			String fileName = user.getTax() +"@_@" + mfile.getOriginalFilename();
			user.setFile(fileName);//附件名称
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
			FileUploadUtil.uploadFile(mfile,filePath+fileName);

        } catch (Exception e) {
			mv.addObject("msg","附件上传失败！");
			result = false;
        }  
		

		if(user.getUserId()==null || user.getUserId().intValue()==0){
			if(!userService.insertUser(user)){
				mv.addObject("msg","用户名已存在！");
				result = false;
			}
		}else{
			userService.updateUserBaseInfo(user);
		}


		mv.addObject("user",user);
		mv.addObject("result",result);
		List<Organ> provinList = organService.listOrganByLevel(1);
		mv.addObject("provinList", provinList);
		List<Organ> cityList = organService.listOrganByParent(2, user.getProvincialId());
		mv.addObject("cityList", cityList);
		List<Organ> countyList = organService.listOrganByParent(3,user.getCityId());
		mv.addObject("countyList", countyList);
		mv.setViewName("user/user_regist");
		return mv;
	}
	
	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public ModelAndView saveUser(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		ModelAndView mv = new ModelAndView();
		String password = user.getPassword() ;
		if(password!=null&&!"".equals(password)){
		//用户密码加密
			user.setPassword(MD5Util.getEncryptedPwd(password));
		}
		if(user.getUserId()==null || user.getUserId().intValue()==0){
			if(userService.insertUser(user)==false){
				mv.addObject("msg","failed");
			}else{
				mv.addObject("msg","success");
			}
		}else{
			userService.updateUserBaseInfo(user);
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 请求编辑用户页面
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/edit")
	public ModelAndView toEdit(@RequestParam int userId){
		ModelAndView mv = new ModelAndView();
		User user = userService.getUserById(userId);
		List<Role> roleList = roleService.listAllRoles();
		mv.addObject("user", user);
		mv.addObject("roleList", roleList);
		mv.setViewName("user/user_info");
		return mv;
	}
	
	/**
	 * 删除某个用户
	 * @param userId
	 * @param out
	 */
	@RequestMapping(value="/delete")
	public void deleteUser(@RequestParam int userId,PrintWriter out){
		userService.deleteUser(userId);
		out.write("success");
		out.close();
	}
	
	/**
	 * 请求用户授权页面
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/auth")
	public String auth(@RequestParam int userId,Model model){
		List<Menu> menuList = menuService.listAllMenu();
		User user = userService.getUserById(userId);
		String userRights = user.getRights();
		if(Tools.notEmpty(userRights)){
			for(Menu menu : menuList){
				menu.setHasMenu(RightsHelper.testRights(userRights, menu.getMenuId()));
				if(menu.isHasMenu()){
					List<Menu> subRightsList = menu.getSubMenu();
					for(Menu sub : subRightsList){
						sub.setHasMenu(RightsHelper.testRights(userRights, sub.getMenuId()));
					}
				}
			}
		}
		JSONArray arr = JSONArray.fromObject(menuList);
		String json = arr.toString();
		json = json.replaceAll("menuId", "id").replaceAll("menuName", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
		model.addAttribute("zTreeNodes", json);
		model.addAttribute("userId", userId);
		return "authorization";
	}
	
	/**
	 * 保存用户权限
	 * @param userId
	 * @param menuIds
	 * @param out
	 */
	@RequestMapping(value="/auth/save")
	public void saveAuth(@RequestParam int userId,@RequestParam String menuIds,PrintWriter out){
		BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
		User user = userService.getUserById(userId);
		user.setRights(rights==null?"":rights.toString());
		userService.updateUserRights(user);
		out.write("success");
		out.close();
	}
	
	/**
	 * 导出用户信息到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView export2Excel(){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户名");
		titles.add("名称");
		titles.add("角色");
		titles.add("最近登录");
		dataMap.put("titles", titles);
		List<User> userList = userService.listAllUser();
		dataMap.put("userList", userList);
		UserExcelView erv = new UserExcelView();
		ModelAndView mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	/**
	 * 判断用户名是否重复（判断税号是否重复）
	 * @param loginname
	 * @param tax
	 * @param response
	 */
	@RequestMapping(value="/checkUser")
	public void checkUser(@RequestParam(value="loginname",required=false) String loginname,@RequestParam(value="tax",required=false) String tax,HttpServletResponse response){
        User user = new User();
        user.setLoginname(loginname);
        user.setTax(tax);
        int count = userService.getCount(user);
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			out.write(Integer.toString(count));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	/**
	 * 判断验证码是否正确
	 * @param code
	 * @param response
	 */
	@RequestMapping(value="/checkCode")
	public void checkCode(String code,HttpServletResponse response,HttpSession session){
		String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);
		String msg = "";
		if(Tools.isEmpty(sessionCode)||!sessionCode.equalsIgnoreCase(code)){
			msg = "验证码不正确！";
		}
		
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			out.write(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 显示待审核用户（代理商）列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/toAudit")
	public ModelAndView getUserList(User user){
		user.setStatus(0);
		user.setType(1);//用户类型：0,管理员;1,代理商
		List<User> userList = userService.getUsers(user);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/user_list");
		mv.addObject("userList", userList);
		mv.addObject("user", user);
		return mv;
	}
	
	/**
	 * 请求编辑用户页面
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/forAudit")
	public ModelAndView forAudit(@RequestParam int userId){
		ModelAndView mv = new ModelAndView();
		User user = userService.getUserById(userId);
		mv.addObject("user", user);

		List<Organ> provinList = organService.listOrganByLevel(1);
		mv.addObject("provinList", provinList);
		List<Organ> cityList = organService.listOrganByParent(2, user.getProvince()==null?"": user.getProvince().getOrgId());
		mv.addObject("cityList", cityList);
		List<Organ> countyList = organService.listOrganByParent(3,user.getCity()==null?"":user.getCity().getOrgId());
		mv.addObject("countyList", countyList);
		mv.setViewName("user/user_audit");
		return mv;
	}
	
	/**
	 * 审核用户（代理商）
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/userAudit")
	public void userAudit(PrintWriter out,@RequestParam int userId,@RequestParam int roleId){
		User user = new User();
		
		user.setRoleId(roleId);//代理商角色
		user.setStatus(1);//已审核
		user.setUserId(userId);
		userService.updateUserStatus(user);
		out.write("success");
		out.flush();
		out.close();
	}
	
	/**
	 * 用户明细信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/detail")
	public ModelAndView detail(HttpSession session){
		ModelAndView mv = new ModelAndView();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		mv.addObject("user", user);

		List<Organ> provinList = organService.listOrganByLevel(1);
		mv.addObject("provinList", provinList);
		if(user!=null){
			List<Organ> cityList = organService.listOrganByParent(2, user.getProvincialId());
			mv.addObject("cityList", cityList);
			List<Organ> countyList = organService.listOrganByParent(3,user.getCityId());
			mv.addObject("countyList", countyList);
		}
		mv.setViewName("user/user_detail");
		return mv;
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/updateUser")
	public String updateUser(HttpSession session,User user,Model model){

		userService.updateUser(user);
		user = userService.getUserById(user.getUserId());
		session.setAttribute(Const.SESSION_USER, user);
		model.addAttribute("user", user);
		List<Organ> provinList = organService.listOrganByLevel(1);
		model.addAttribute("provinList", provinList);
		List<Organ> cityList = organService.listOrganByParent(2, user.getProvincialId());
		model.addAttribute("cityList", cityList);
		List<Organ> countyList = organService.listOrganByParent(3,user.getCityId());
		model.addAttribute("countyList", countyList);	
		return "user/user_detail";
	}
	
	/**
	 * 用户密码
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/password")
	public String password(Model model){
		return "user/password";
	}
	
	/**
	 * 更新密码
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/changePassword")
	public void changePassword(PrintWriter out,HttpSession session,@RequestParam String password,@RequestParam String newPassword){
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String message = "";
		try {
			if(MD5Util.validPassword(password, user.getPassword())){
				userService.updatePassword(user.getUserId(),MD5Util.getEncryptedPwd(newPassword));
				message = "success";
			}else{
				message = "用户名或密码错误！";
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			message = "密码修改失败！";
			e.printStackTrace();
		}finally {
			out.write(message);
			out.flush();
			out.close();
		}
	}
}
