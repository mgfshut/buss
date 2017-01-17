package com.rhtop.buss.biz.web;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.hash.Hashing;
import com.rhtop.buss.common.entity.Module;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.ModuleService;
import com.rhtop.buss.biz.service.UserService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/user")
public class UserController  extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private ModuleService moduleService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/insertUser.json")
	public InfoResult<User> insertUser(@RequestBody User user){
		InfoResult<User> infoResult = new InfoResult<User>();
		user.setUserId(UUID.randomUUID().toString());
		user.setCreateUser(user.getUpdateUser());
		user.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		user.setUpdateUser(user.getUpdateUser());
		user.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = userService.insertUser(user);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("新增成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("新增失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteUser.json")
	public InfoResult<User> deleteUser(@RequestParam("userId") String userId){
		InfoResult<User> infoResult = new InfoResult<User>();
		int num = userService.deleteUser(userId);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("删除成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("删除失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
     * 修改
     */
	@ResponseBody
	@RequestMapping("/updateUser.json")
	public InfoResult<User> updateUser(@RequestBody User user){
		InfoResult<User> infoResult = new InfoResult<User>();
		user.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = userService.updateUser(user);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("修改成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("修改失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
	 * 根据条件分页查询信息列表
	 */
	@ResponseBody
	@RequestMapping(value="/pager")
	public InfoResult<User> listPageUser(Page page,User user){
		InfoResult<User> infoResult = new InfoResult<User>();
		user.setPage(page);
		List<User> userList = userService.listPageUser(user);
		infoResult.setCode("200");
		infoResult.setResList(userList);
		infoResult.setPage(user.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey.json")
	public InfoResult<User> selectByPrimaryKey(@RequestParam("userId") String userId) {
		InfoResult<User> infoResult = new InfoResult<User>();
		infoResult.setCode("200");
		User user = userService.selectByPrimaryKey(userId);
		infoResult.setResObject(user);
		return infoResult;
	}
	
	@RequestMapping("login/{userName}")
	@ResponseBody
	public User findAccount(@PathVariable("userName") String userName) {
		User user = new User();
		user.setUserName(userName);
		List<User> list = userService.listUsers(user);
		if(list.size()>0){
			User uu = list.get(0);
			List<Module> moduleList = moduleService.listModulesByUserId(uu.getUserId());
			List<String> permissionList = new ArrayList<String>();
			for(Module module:moduleList){
				permissionList.add(module.getAuthName());
			}
			uu.setPermissionList(permissionList);
			return uu;
		}else{
			return null;
		}
	}
	
	@RequestMapping("/{userName}")
	@ResponseBody
	public User getByUserName(@PathVariable("userName") String userName){
		User user = new User();
		user.setUserName(userName);
		List<User> list = userService.listUsers(user);
		if(list.size()>0){
			User uu = list.get(0);
			return uu;
		}else{
			return null;
		}
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public User login(@RequestParam("userId") String userId) {
		User user = userService.selectByPrimaryKey(userId);
		return user;
	}

	@RequestMapping("/logout")
	@ResponseBody
	public User logout(@RequestParam("userId") String userId) {
		User user = userService.selectByPrimaryKey(userId);
		return user;
	}
	
	@RequestMapping("/modifyUserPass")
	@ResponseBody
	public HtmlMessage modifyUserPass(
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "newPassword", required = false) String newPassword,
			@RequestParam(value = "newPassword1", required = false) String newPassword1) {
		if (newPassword.equals(newPassword1)) {
			User user = userService.selectByPrimaryKey(userId);
			if (user.getUserPassword().equals(Hashing.md5().hashString(password, Charset.forName("utf-8")).toString())) {
				user.setUserPassword(Hashing.md5().hashString(newPassword, Charset.forName("utf-8")).toString());
				user.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
				user.setUpdateUser(userId);
				userService.updateUser(user);
				return new HtmlMessage("200", "密码修改成功");
			} else {
				return new HtmlMessage("300", "当前输入密码错误!");
			}
		} else {
			return new HtmlMessage("300", "两次输入密码不相符!");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/modifyPassword")
	public ResultInfo modifyPassword(@RequestParam("body") String body) {
		JSONObject jsonObject=JSONObject.fromObject(body);
		User user = (User)JSONObject.toBean(jsonObject, User.class);
		ResultInfo readResult = new ResultInfo();
		
		if (user.getNewPassword().equals(user.getNewPassword1())) {
			User uu = userService.selectByPrimaryKey(user.getUpdateUser());
			if (user.getUserPassword().equals(Hashing.md5().hashString(uu.getUserPassword(), Charset.forName("utf-8")).toString())) {
				user.setUserPassword(Hashing.md5().hashString(user.getNewPassword(), Charset.forName("utf-8")).toString());
				user.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
				userService.updateUser(user);
				readResult.setCode("200");
				readResult.setMessage("修改成功!");
			} else {
				readResult.setCode("300");
				readResult.setMessage("当前输入密码错误!");
			}
		} else {
			readResult.setCode("300");
			readResult.setMessage("两次输入密码不相符!");
		}
		return readResult;
	}
	
}