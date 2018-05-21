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

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
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
import com.office.util.Mail;
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
			out = response.getWriter();
			out.write(Integer.toString(count));
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
				message = "原密码错误！";
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
	
	/**
	 * 忘记密码页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toRenew")
	public String toRenew(Model model){
		return "user/password_renew";
	}
	
	/**
	 * 更新密码
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/renewPassword")
	public void renewPassword(HttpServletRequest request,PrintWriter out,String loginname){

		String errInfo = "fail";
		//根据用户登录名查询用户列表，如果长度大于1，则存在重复账号；如果长度为0，则查询无结果。
		List<User> userList = userService.getUserByName(loginname);
		if(userList.size()>1){
			errInfo = "存在重复的用户名，请联系系统管理员！";
		}else if(userList.size()==0){
			errInfo = "用户不存在！";
		}else{
			User user = userList.get(0);
			String password = Tools.getStringRandom(6);
			userService.updatePassword(user.getUserId(),MD5Util.getEncryptedPwd(password));
			user.setPassword(password);
			sendMail(user,"password",request);
			errInfo = "success";
		}

		out.write(errInfo);
		out.flush();
		out.close();
	}
	
	/**
	 * 发送邮件通知代理商信息
	 * @param user
	 * @param method
	 * @param request
	 */
	private void sendMail(User user,String method,HttpServletRequest request){
		
		Mail mail = new Mail();
		Multipart multipart = new MimeMultipart(); // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
		BodyPart contentPart = new MimeBodyPart(); // 设置邮件的文本内容
		StringBuffer textBuffer = new StringBuffer();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		textBuffer.append("<font style='font-weight:bold;' size='4'>尊敬的用户(").append(user.getUsername()).append(")</font><br>");
		if("audit".equals(method)){
			textBuffer.append("<p style='text-indent:2em'>您的账号已被审核成功，请您登陆系统。</p>");
		}
		if("password".equals(method)){
			textBuffer.append("<p style='text-indent:2em'>您的密码已被重置为【"+user.getPassword()+"】。请您登陆系统后在“基本信息管理”-“修改密码”菜单下重新修改密码。</p>");
		}
		textBuffer.append("<p style='text-indent:2em'>登录地址：<a href='").append(basePath).append("'>").append(basePath).append("</a></p>");
		textBuffer.append("<p style='text-indent:2em'>*本邮件是从不受监控的电子邮件地址发出的。请不要回复本邮件。</p>");
		textBuffer.append("<hr width='400px' color='red' align='left' size=10/>");
		textBuffer.append("<b>&nbsp;&nbsp;Office365 Support Team</b><br>");
		textBuffer.append("<b>&nbsp;&nbsp;伟仕佳杰中国北京 集团总部</b><br>");
		textBuffer.append("&nbsp;&nbsp;<img src='cid:qrCode'><br>");
		textBuffer.append("<font color='red' size ='1'>&nbsp;&nbsp;关注伟仕佳杰官方微信，更多商机，更多共赢</font><br>");
		textBuffer.append("&nbsp;&nbsp;地 址：北京市海淀区长春桥路11号万柳亿城大厦A座6/7层 100089<br>");
		textBuffer.append("&nbsp;&nbsp;电话：400-890-1909<br>");
		textBuffer.append("&nbsp;&nbsp;邮箱：<a href='mailto:365service@ecschina.com'>365service@ecschina.com</a><br>");
		textBuffer.append("&nbsp;&nbsp;网 址：<a href='www.ecschina.com'>www.ecschina.com</a><br>");
		textBuffer.append("<p><font color='grey'>本电子邮件以及其所传输的任何文件均为保密信息，仅供传送双方的个人或实体使用。如果您不是指定的收件人，那么我们在此通知您，严格禁止对这些信息进行披露、复制、分发或采取以这些信息内容为基础的任何行动。如果您是由于发送错误收到这封电子邮件或不是指定的收件人，请回电子邮件通知发件人并立即删除该电子邮件（及其所附任何文件）。除非这封电子邮件另有明文指出，否则此电子邮件的发送者的任何观点或意见纯属个人目的，不一定代表佳杰科技（中国）有限公司。佳杰科技（中国）有限公司不负责由于该电子邮件传播相关此病毒而造成的任何损害赔偿责任</font></p>");
		try {
			MimeBodyPart qrCode = new MimeBodyPart();
			String imagePath = request.getSession().getServletContext().getRealPath("/") + "images"+File.separator;
			DataHandler dh1 = new DataHandler(new FileDataSource(imagePath + "image002.png"));// 图片路径
			qrCode.setDataHandler(dh1);
			qrCode.setContentID("qrCode");
			contentPart.setContent(textBuffer.toString(), "text/html;charset=utf-8");
			multipart.addBodyPart(contentPart);
			multipart.addBodyPart(qrCode);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mail.sendMail(user.getEmail(),"账号密码修改", multipart);
	}
}
