package com.rhtop.buss.ocs.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Member;
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
	@ResponseBody
	public Member login(HttpServletRequest request){
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		SecurityUtils.getSubject().login(token);
		
		//生成token
		Map<String , Object> payload=new HashMap<String, Object>();
		Date date=new Date();
		payload.put("uid", "admin");//用户ID
		payload.put("iat", date.getTime());//生成时间
		payload.put("ext",date.getTime()+1000*60*60);//过期时间1小时
		String userToken=Jwt.createToken(payload);
		
		
		
//		model.addAttribute("tree","");
//		return "index";
		return null;
	}
	
}
