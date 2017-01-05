package com.rhtop.buss.ocs.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.ReadResult;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.security.UserLoginToken;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.utils.Jwt;
import com.rhtop.buss.common.web.BaseController;

@Controller
@RequestMapping("app")
public class AppLoginController extends BaseController {
	@Autowired(required=false)
	@Qualifier("restService")
	private RestService service;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/login")
	@ResponseBody
	public ReadResult<Member> login(HttpServletRequest request){
		ReadResult<Member> readResult = new ReadResult<Member>();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String devType = request.getParameter("devType"); 
		try{
			UserLoginToken loginToken = new UserLoginToken();
			loginToken.setHost(request.getHeader("host"));
			loginToken.setPassword(password.toCharArray());
			loginToken.setRememberMe(false);
			loginToken.setUsername(userName);
			loginToken.setMobileCodePass(true);
			loginToken.setVerifitionCodePass(true);
//			UserLoginToken
			SecurityUtils.getSubject().login(loginToken);
			User user = (User) service.invoke("user-" + userName, "GET", new HashMap(), User.class);
			Member member = (Member) service.invoke("member-" + user.getUserId(), "GET", new HashMap(), Member.class);
			//生成token
			Map<String , Object> payload=new HashMap<String, Object>();
			Date date=new Date();
			payload.put("uid", user.getUserId()+devType);//用户ID+设备类型
			payload.put("iat", date.getTime());//生成时间
			payload.put("ext",date.getTime()+1000*60*60);//过期时间1小时
			String token=Jwt.createToken(payload);
			member.setToken(token);
			readResult.setCode("200");
			readResult.setMessage("登录成功");
			readResult.setResObject(member);
		}catch(Exception e){
			e.printStackTrace();
			readResult.setCode("999");
			readResult.setMessage("登录失败");
		}
		return readResult;
	}
	
}
