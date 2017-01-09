package com.rhtop.buss.ocs.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhtop.buss.common.entity.BusinessDiary;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.ReadResult;
import com.rhtop.buss.common.entity.RelCustomerCategory;
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
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0001")
	public ReadResult<String> addCustomerAndCategory(HttpServletRequest request, @RequestBody Customer customer){
		ReadResult<String> readResult = new ReadResult<String>();
		String token = request.getHeader("token");
		String userId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		
		//检查校验是否通过
		if ("200".equals(result.get("code").toString())) {
			//检查传进来的客户对象是否为空，为空则返回错误信息码并结束操作。
			if(customer==null){
				readResult.setCode("500");
				readResult.setMessage("用户信息不能为空！");
				return readResult;
			}
			List<ContactsInfo> contacts = customer.getContacts();
			List<Category> categorys = customer.getCategorys();
			//客户对象不为空，完善客户对象
			customer.setCreateUser(userId);
			String customerId = UUID.randomUUID().toString().replace("-", "");
			customer.setCustomerId(customerId);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			customer.setCreateTime(now);
			customer.setCusCreateTime(now);
			customer.setCkStatus("00");
			//向数据库中添加一条客户数据。
		}
		return readResult;
	}
	
}
