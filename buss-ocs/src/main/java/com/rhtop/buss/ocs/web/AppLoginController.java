package com.rhtop.buss.ocs.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.ReadResult;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.security.UserLoginToken;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.utils.Jwt;
import com.rhtop.buss.common.web.BaseController;

@RestController
@RequestMapping("app")
//设置跨域支持
@CrossOrigin
public class AppLoginController extends BaseController {
	@Autowired(required=false)
	@Qualifier("restService")
	private RestService service;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/login")
	public ReadResult<Member> login(HttpServletRequest request, @RequestBody User user){
		ReadResult<Member> readResult = new ReadResult<Member>();
		System.out.println(user.getUserName());
		System.out.println(user.getUserPassword());
		System.out.println(request.getHeader("token"));
		try{
			UserLoginToken loginToken = new UserLoginToken();
			loginToken.setHost(request.getHeader("host"));
			loginToken.setPassword(user.getUserPassword().toCharArray());
			loginToken.setRememberMe(false);
			loginToken.setUsername(user.getUserName());
			loginToken.setMobileCodePass(true);
			loginToken.setVerifitionCodePass(true);
//			UserLoginToken
			SecurityUtils.getSubject().login(loginToken);
			User kuser = (User) service.invoke("user-" + user.getUserName(), "GET", new HashMap(), User.class);
			Member member = (Member) service.invoke("member-role-" + kuser.getUserId(), "GET", new HashMap(), Member.class);
			//生成token
			Map<String , Object> payload=new HashMap<String, Object>();
			Date date=new Date();
			payload.put("uid", kuser.getUserId());//用户ID+设备类型
			payload.put("iat", date.getTime());//生成时间
			payload.put("ext",date.getTime()+1000*60*60);//过期时间1小时
			String token=Jwt.createToken(payload);
			member.setToken(token);
			readResult.setCode("200");
			readResult.setMessage("登录成功");
			readResult.setResObject(member);
			System.out.println("登录成功");
		}catch(Exception e){
			e.printStackTrace();
			readResult.setCode("999");
			readResult.setMessage("登录失败");
		}
		return readResult;
	}
	
}
