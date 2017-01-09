package com.rhtop.buss.ocs.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.ReadResult;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.security.UserLoginToken;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.utils.Jwt;
import com.rhtop.buss.common.utils.PropertyUtil;

/**
 * 对外接口的读取功能控制器，内部接口按照操作类型分为两类， 信息采集相关接口的命名为前缀In+四位编号0001依次递增,
 * 交易和合同相关接口的命名微前缀Dl+四位编号0001依次递增。
 * 
 * @author MakeItHappen
 * 
 */
@RestController
@RequestMapping(value = "/interface")
// 设置跨域支持
@CrossOrigin
public class OutController {
	@Autowired(required=false)
	@Qualifier("restService")
	private RestService service;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/login")
	public ResultInfo login(HttpServletRequest request, @RequestBody User user){
		ResultInfo readResult = new ResultInfo();
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
			Member member = (Member) service.invoke("member-" + kuser.getUserId(), "GET", new HashMap(), Member.class);
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
			PropertyUtil propertyUtil = new PropertyUtil("properties/token.properties");
			//从配置文件中读取上传文件的存放根路径
			String readToken = propertyUtil.readValue(kuser.getUserId());
			if(!token.equals(readToken)){
				propertyUtil.setValue(kuser.getUserId(), token);
			}
			System.out.println(readToken);
		}catch(Exception e){
			e.printStackTrace();
			readResult.setCode("999");
			readResult.setMessage("登录失败");
		}
		return readResult;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/readData/viewMember")
	public ResultInfo readData(HttpServletRequest request,
			@RequestBody Member member) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if (!"200".equals(result.get("code").toString())) {
			JSONObject jsonUser = JSONObject.fromObject(member); 
			readResult = (ResultInfo) service.invoke("readData-viewMember", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/viewMember")
	public ResultInfo writeData(HttpServletRequest request,
			@RequestBody Member member) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			JSONObject jsonUser = JSONObject.fromObject(member); 
			readResult = (ResultInfo) service.invoke("writeData-viewMember", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0001")
	public ResultInfo addCustomerAndCategory(HttpServletRequest request,@RequestBody Customer customer){
//		System.out.println(customer.getCusName());
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String userId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if (!"200".equals(result.get("code").toString())) {
			customer.setUpdateUser(userId);
			JSONObject jsonCustomer = JSONObject.fromObject(customer);
			readResult = (ResultInfo) service.invoke("writeData-In0001", "POST", jsonCustomer.toString() , ResultInfo.class);
		}
		return readResult;
	}

	
	/**
	 * 客户经理查询所属的客户信息，条件查询（地区，类型，渠道）
	 * @param request
	 * @param customer
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2001")
	public ResultInfo listCustomers(HttpServletRequest request,@RequestBody Customer customer){
		
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if (!"200".equals(result.get("code").toString())) {
			customer.setCreateUser(memberId);
			readResult.setResObject(customer);
			JSONObject jsonUser = JSONObject.fromObject(customer);
			readResult = (ResultInfo) service.invoke("readData-R2001", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 客户经理查询客户的详细信息
	 * @param request
	 * @param customer
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2002")
	public ResultInfo customersInfo(HttpServletRequest request, @RequestBody Customer customer) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		Map<String,Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if(!"200".equals(result.get("code").toString())){
			readResult.setResObject(customer);
			JSONObject jsonUser = JSONObject.fromObject(customer);
			readResult = (ResultInfo) service.invoke("readData-R2002", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	

}
