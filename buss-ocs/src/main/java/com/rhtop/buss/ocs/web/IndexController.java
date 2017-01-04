package com.rhtop.buss.ocs.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.web.BaseController;

@Controller
@RequestMapping
public class IndexController extends BaseController {
	@Autowired
	private RestService service;
	@RequestMapping("/")
	public String index(Model model,HttpServletRequest request){
		model.addAttribute("tree",loadTree());
		return "index";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String,Object> loadTree(){
		Subject subject = SecurityUtils.getSubject();
		User user =(User)subject.getPrincipal();
		Map<String,Object> map = service.invoke("module-permissionTree-"+user.getUserId(),new HashMap() );
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/login/success")
	private String loginSuccess(){
		service.invoke("user-login",new HashMap());
		return "redirect:/";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/user/logout")
	private String logout(){
		service.invoke("user-logout",new HashMap());
		return "redirect:/logout";
	}
}
