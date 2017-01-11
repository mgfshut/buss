package com.rhtop.buss.ocs.web;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.TransactionInfo;
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
	
	private @Value("${biz.server.url}") String coreUrl;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/login")
	public ResultInfo login(HttpServletRequest request, @RequestBody User user){
		ResultInfo readResult = new ResultInfo();
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
			payload.put("uid", kuser.getUserId());//用户ID+设备类型
			Calendar ca = Calendar.getInstance();
			payload.put("iat", ca.getTime().getTime());//生成时间
			ca.add(Calendar.DATE, 30);// num为增加的天数，可以改变的
			payload.put("ext",ca.getTime().getTime());//过期时间30天
			String token=Jwt.createToken(payload);
			member.setToken(token);
			readResult.setCode("200");
			readResult.setMessage("登录成功");
			readResult.setResObject(member);
			PropertyUtil propertyUtil = new PropertyUtil("properties/token.properties");
			//从配置文件中读取上传文件的存放根路径
			String readToken = propertyUtil.readValue(kuser.getUserId());
			if(!token.equals(readToken)){
				propertyUtil.setValue(kuser.getUserId(), token);
			}
		}catch(Exception e){
			e.printStackTrace();
			readResult.setCode("999");
			readResult.setMessage("登录失败");
		}
		return readResult;
	}
	/**
	 * 客户经理新增用户、品类、，联系人的接口
	 * @param request
	 * @param customer
	 * @author MakeItHappen
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0001")
	public ResultInfo addCustomerAndCategory(HttpServletRequest request,@RequestBody Customer customer){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			customer.setUpdateUser(memberId);
			JSONObject jsonCustomer = JSONObject.fromObject(customer);
			readResult = (ResultInfo) service.invoke("writeData-In0001", "POST", jsonCustomer.toString() , ResultInfo.class);
		}
		return readResult;
	}

	/**
	 * 查询所有代码集编码
	 * @param request
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/getAllCodeMap")
	public ResultInfo customersInfo(HttpServletRequest request) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String,Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if("200".equals(result.get("code").toString())){
			readResult = (ResultInfo) service.invoke("readData-getAllCodeMap", "POST", new String(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 单图片上传接口
	 * 图片上传暂不做用户信息验证
	 * @param request
	 * @param picFile
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/uploads")
	public ResultInfo uploadPic(HttpServletRequest request, MultipartFile[] files){
		ResultInfo resultInfo = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		URI uri = null;
		try{
			uri = new URI(coreUrl + "/service/writeData/In0002");
		}catch(Exception e){
			
		}
		mvm.add("memberId", memberId);
		for (int i=0; i<files.length; i++){
			MultipartFile file = files[i];
			File localFile = new File(FileUtils.getTempDirectoryPath() + File.separator + RandomStringUtils.randomAlphanumeric(8) + file.getOriginalFilename()) ;
			try{
				Files.write(file.getBytes(), localFile);
				mvm.add("file", new FileSystemResource(localFile));
			}catch(Exception e){
				
			}
			
			if (localFile.exists()) {
				localFile.delete();
			}
		}
		
		resultInfo = restTemplate.postForObject(uri, mvm, ResultInfo.class);
		
		return resultInfo;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0003")
	public ResultInfo fixWholesaleAndAcptPrice(HttpServletRequest request, @RequestBody RelCategoryPrice catePri){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			catePri.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(catePri);
			readResult = (ResultInfo) service.invoke("writeData-In0003", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0004")
	public ResultInfo fixMidPrice(HttpServletRequest request, @RequestBody RelCategoryPrice catePri){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			catePri.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(catePri);
			readResult = (ResultInfo) service.invoke("writeData-In0004", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0005")
	public ResultInfo commitNewCustomerLevelOne(HttpServletRequest request,@RequestBody Map map){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if (!"200".equals(result.get("code").toString())) {
			List<String> ids = (List<String>)map.get("ids");
			List<Customer> cuss = new ArrayList<Customer>();
			for(String cusId : ids){
				Customer cus = new Customer();
				cus.setCustomerId(cusId);
				cus.setUpdateUser(memberId);
				cuss.add(cus);
			}
			JSONArray jsonObject = JSONArray.fromObject(cuss);
			readResult = (ResultInfo) service.invoke("writeData-In0005", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0006")
	public ResultInfo commitNewCustomerLevelTwo(HttpServletRequest request,@RequestBody Map map){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if (!"200".equals(result.get("code").toString())) {
			List<String> ids = (List<String>)map.get("ids");
			List<Customer> cuss = new ArrayList<Customer>();
			for(String cusId : ids){
				Customer cus = new Customer();
				cus.setCustomerId(cusId);
				cus.setUpdateUser(memberId);
				cuss.add(cus);
			}
			JSONArray jsonObject = JSONArray.fromObject(cuss);
			readResult = (ResultInfo) service.invoke("writeData-In0006", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0007")
	public ResultInfo fixOfferPrice(HttpServletRequest request, @RequestBody RelCategoryPrice catePri){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			catePri.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(catePri);
			readResult = (ResultInfo) service.invoke("writeData-In0007", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0008")
	public ResultInfo universeAddCategory(HttpServletRequest request, @RequestBody Category cat){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			cat.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(cat);
			readResult = (ResultInfo) service.invoke("writeData-In0008", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0009")
	public ResultInfo updateCustomerAndCategory(HttpServletRequest request, @RequestBody Customer customer){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			customer.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(customer);
			readResult = (ResultInfo) service.invoke("writeData-In0009", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	//TODO: 发起交易的接口
	//入参：customerId，categoryId，txAmo交易数量，pcasPri客户价。
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/Dl0001")
	public ResultInfo makeADeal(HttpServletRequest request, @RequestBody TransactionInfo tx){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			tx.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(tx);
			readResult = (ResultInfo) service.invoke("writeData-Dl0001", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	//TODO:回盘接口
	//入参：transactionInfoId交易记录ID，pcasPri客户价。
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/Dl0002")
	public ResultInfo makeANegotiate(HttpServletRequest request, @RequestBody TransactionInfo tx){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			tx.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(tx);
			readResult = (ResultInfo) service.invoke("writeData-Dl0002", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	//国际部回盘接口
	//入参：transactionInfoId交易记录ID，ctofPri回盘价，ctofAging回盘时效
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/Dl0003")
	public ResultInfo universeNegotiate(HttpServletRequest request, @RequestBody TransactionInfo tx){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			tx.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(tx);
			readResult = (ResultInfo) service.invoke("writeData-Dl0003", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	//决委会回盘审核接口
	//入参：transactionInfoId交易记录ID，domCtofPri决委会回盘价,(ctofAging回盘时效如果传了就会修改)
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/Dl0004")
	public ResultInfo domainNegotiate(HttpServletRequest request, @RequestBody TransactionInfo tx){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			tx.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(tx);
			readResult = (ResultInfo) service.invoke("writeData-Dl0004", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	//客户经理定盘接口
	//入参：transactionInfoId交易记录ID，contractInfo合同对象。
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/Dl0005")
	public ResultInfo createContract(HttpServletRequest request, @RequestBody ContractInfo contract){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			contract.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(contract);
			readResult = (ResultInfo) service.invoke("writeData-Dl0005", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}

	
	/**
	 * 接口id:R2001
	 * 客户经理查询所属的客户信息列表，分页，条件查询（地区，类型，渠道）
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
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			customer.setCreateUser(memberId);//将用户的id作为创建者给保存起来
			JSONObject jsonUser = JSONObject.fromObject(customer);
			readResult = (ResultInfo) service.invoke("readData-R2001", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id:R2002
	 * 客户经理,分部经理 查询客户的详细信息
	 * @param request
	 * @param customer
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2002")
	public ResultInfo customersInfo(HttpServletRequest request, @RequestBody Customer customer) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String,Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if("200".equals(result.get("code").toString())){
			customer.setCreateUser(memberId);//将用户的id作为创建者给保存起来
			JSONObject jsonUser = JSONObject.fromObject(customer);
			readResult = (ResultInfo) service.invoke("readData-R2002", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	} 
	
	/**
	 * 接口id:R2003
	 * 客户经理查看品类列表，分页查询
	 * @param request
	 * @param category
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2003")
	public ResultInfo listCategorys(HttpServletRequest request,@RequestBody Category category){
		ResultInfo readResult = new ResultInfo();
		String memberId = request.getHeader("memberId");
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if(!"200".equals(result.get("code").toString())){
//			category.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(category);
			readResult =(ResultInfo)service.invoke("readData-R2003", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}

	/**
	 * 接口id:R2004
	 * 客户经理查看品类详情
	 * @param request
	 * @param category
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/readData/R2004")
	public ResultInfo categorys(HttpServletRequest request, @RequestBody Category category) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String,Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if (!"200".equals(result.get("code").toString())) {
			JSONObject jsonUser = JSONObject.fromObject(category);
			readResult =(ResultInfo)service.invoke("readData-R2004", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}

	
	/**
	 * 接口id:R2005
	 * 客户经理查看信息采集列表
	 * @param request
	 * @param member
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/readData/R2005")
	public ResultInfo listRelCategoryPrices(HttpServletRequest request, @RequestBody Member member) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if (!"200".equals(result.get("code").toString())) {
			readResult.setResObject(member);
			JSONObject jsonUser = JSONObject.fromObject(member);
			readResult =(ResultInfo)service.invoke("readData-R2005", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	 /* 接口id:R2006
	 * 客户经理查看信息采集详情
	 * @param request
	 * @param member
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2006")
	public ResultInfo categoryPriceInfo(HttpServletRequest request, @RequestBody Category category) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if (!"200".equals(result.get("code").toString())) {
			readResult.setResObject(category);
			JSONObject jsonUser = JSONObject.fromObject(category);
			readResult =(ResultInfo)service.invoke("readData-R2006", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id:R2007
	 * 客户经理查看交易信息列表
	 * @param request
	 * @param member
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/readData/R2007")
	public ResultInfo listtransactions(HttpServletRequest request,@RequestBody Member member){
		ResultInfo readResultInfo = new ResultInfo();
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if (!"200".equals(result.get("code").toString())) {
			readResult.setResObject(memberId);
			JSONObject jsonUser = JSONObject.fromObject(memberId);
			readResult =(ResultInfo)service.invoke("readData-R2007", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResultInfo;
	}
	/**
	 * 接口id:R2008
	 * 客户经理查看交易信息列表
	 * @param request
	 * @param member
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/readData/R2008")
	public ResultInfo  transactionInfo(HttpServletRequest request,@RequestBody TransactionInfo transactionInfo){
		ResultInfo readResultInfo = new ResultInfo();
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if (!"200".equals(result.get("code").toString())) {
			readResult.setResObject(transactionInfo);
			JSONObject jsonUser = JSONObject.fromObject(transactionInfo);
			readResult =(ResultInfo)service.invoke("readData-R2008", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResultInfo;
	}
	/**
	 * 接口id:R2009
	 * 总经理查看合同信息
	 * @param request
	 * @param transactionInfo
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/readData/R2009")
	public ResultInfo  listContract(HttpServletRequest request,@RequestBody Member member){
		ResultInfo readResultInfo = new ResultInfo();
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if (!"200".equals(result.get("code").toString())) {
			readResult.setResObject(member);
			JSONObject jsonUser = JSONObject.fromObject(member);
			readResult =(ResultInfo)service.invoke("readData-R2009", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResultInfo;
	}
	
}
