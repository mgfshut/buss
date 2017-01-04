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
import com.rhtop.buss.common.utils.Jwt;
import com.rhtop.buss.common.web.BaseController;

@Controller
@RequestMapping("APP")
public class AppLoginController extends BaseController {
	@Autowired
	private RestService service;
	@RequestMapping("/login")
	public String index(Model model,HttpServletRequest request){
//		Subject subject = SecurityUtils.getSubject();
//		User User = (User) subject.getPrincipals();
//		//生成token
//		Map<String , Object> payload=new HashMap<String, Object>();
//		Date date=new Date();
//		payload.put("uid", User.getUserId());//用户ID
//		payload.put("iat", date.getTime());//生成时间
//		payload.put("ext",date.getTime()+1000*60*60);//过期时间1小时
//		String token=Jwt.createToken(payload);
//		model.addAttribute("token",token);  
		model.addAttribute("tree","");
		return "index";
	}
	
}
