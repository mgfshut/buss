package com.rhtop.buss.biz.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;








import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.biz.service.MemberService;
import com.rhtop.buss.biz.service.UserService;
import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.ReadResult;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.security.UserLoginToken;
import com.rhtop.buss.common.utils.Jwt;
import com.rhtop.buss.common.web.BaseController;

@Controller
@RequestMapping("service/app")
public class AppLoginController extends BaseController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	@ResponseBody
	public ReadResult<Member> login(HttpServletRequest request){
		ReadResult<Member> readResult = new ReadResult<Member>();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String devType = request.getParameter("devType");
		try{
			UsernamePasswordToken loginToken = new UsernamePasswordToken(userName, password,
					false, request.getHeader("host"));
//			UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
			SecurityUtils.getSubject().login(loginToken);
			User user = userService.selectByUserName(userName);
			Member member = memberService.selectByPrimaryKey(user.getUserId());
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
