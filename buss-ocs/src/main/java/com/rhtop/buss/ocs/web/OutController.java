package com.rhtop.buss.ocs.web;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
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
import com.google.gson.Gson;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.common.entity.Upgrade;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.security.UserLoginToken;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.utils.Jwt;
import com.rhtop.buss.common.utils.PropertyUtil;
import com.rhtop.buss.common.web.BaseController;

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
@CrossOrigin(maxAge=3600)
public class OutController extends BaseController {
	@Autowired(required=false)
	@Qualifier("restService")
	private RestService service;
	
	protected Logger  log = Logger.getLogger("interface");
	
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
			readResult.setMessage("用户名不存在或密码错误！");
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
//			Gson gson = new Gson();
//			String jsonCustomer = gson.toJson(customer);
			log.info("writeData-In0001:"+jsonCustomer.toString());
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
			log.info("readData-getAllCodeMap:");
			readResult = (ResultInfo) service.invoke("readData-getAllCodeMap", "POST", new String(), ResultInfo.class);
		}
		return readResult;
	}
	/**
	 * 增加代码值
	 * @author mgf
	 * @date 2017年1月14日 上午10:34:57 
	 * @param request
	 * @param codeValue
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/writeData/addCodeValue")
	public ResultInfo addCodeValue(HttpServletRequest request,@RequestBody CodeValue codeValue) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String,Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if("200".equals(result.get("code").toString())){
			codeValue.setUpdateUser(memberId);
			JSONObject jsonCodeValue = JSONObject.fromObject(codeValue);
			log.info("writeData-addCodeValue:"+jsonCodeValue.toString());
			readResult = (ResultInfo) service.invoke("writeData-addCodeValue", "POST", jsonCodeValue.toString(), ResultInfo.class);
		}
		return readResult;
	}
	/**
	 * 删除代码值
	 * @author mgf
	 * @date 2017年1月14日 上午10:34:57 
	 * @param request
	 * @param codeValue
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/writeData/deleteCodeValue")
	public ResultInfo deleteCodeValue(HttpServletRequest request,@RequestBody CodeValue codeValue) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String,Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if("200".equals(result.get("code").toString())){
			codeValue.setUpdateUser(memberId);
			JSONObject jsonCodeValue = JSONObject.fromObject(codeValue);
			log.info("writeData-deleteCodeValue:"+jsonCodeValue.toString());
			readResult = (ResultInfo) service.invoke("writeData-deleteCodeValue", "POST", jsonCodeValue.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * app合同打印
	 * @author mgf
	 * @date 2017年1月15日 上午10:01:24 
	 * @param request
	 * @param codeValue
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/contractInfoPrint")
	public ResultInfo contractInfoPrint(HttpServletRequest request,@RequestBody ContractInfo contractInfo) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String,Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if("200".equals(result.get("code").toString())){
			contractInfo.setUpdateUser(memberId);
			JSONObject jsonCodeValue = JSONObject.fromObject(contractInfo);
			log.info("contractInfo-app-print:"+jsonCodeValue.toString());
			readResult = (ResultInfo) service.invoke("contractInfo-app-print", "POST", jsonCodeValue.toString(), ResultInfo.class);
		}
		return readResult;
	}
	/**
	 * 修改密码
	 * @author mgf
	 * @date 2017年1月17日 上午9:55:25 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/writeData/modifyPassword")
	public ResultInfo modifyPassword(HttpServletRequest request,@RequestBody User user) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String,Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if("200".equals(result.get("code").toString())){
			user.setUpdateUser(memberId);
			JSONObject jsonCodeValue = JSONObject.fromObject(user);
			log.info("user-modifyPassword:"+jsonCodeValue.toString());
			readResult = (ResultInfo) service.invoke("user-modifyPassword", "POST", jsonCodeValue.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
//	/**
//	 * app版本更新接口
//	 * @author mgf
//	 * @date 2017年1月17日 上午10:13:04 
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/writeData/versionUpdate")
//	public ResultInfo versionUpdate(HttpServletRequest request) {
//		ResultInfo readResult = new ResultInfo();
//		String token = request.getHeader("token");
//		String memberId = request.getHeader("memberId");
//		Map<String,Object> result = Jwt.validToken(memberId,token);
//		readResult.setCode(result.get("code").toString());
//		readResult.setMessage(result.get("message").toString());
//		if("200".equals(result.get("code").toString())){
//			//TODO
//		}
//		return readResult;
//	}
	
	
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
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		URI uri = null;
		try{
			uri = new URI(coreUrl + "/service/writeData/In0002");
		}catch(Exception e){
			e.printStackTrace();
		}
		List<File> fileList = new ArrayList<File>();
		for (int i=0; i<files.length; i++){
			MultipartFile file = files[i];
			File localFile = new File(FileUtils.getTempDirectoryPath() + File.separator + RandomStringUtils.randomAlphanumeric(8) + file.getOriginalFilename()) ;
			try{
				Files.write(file.getBytes(), localFile);
				mvm.add("files", new FileSystemResource(localFile));
				fileList.add(localFile);
			}catch(Exception e){
				
			}
		}
		
		resultInfo = restTemplate.postForObject(uri, mvm, ResultInfo.class);
		for (int i=0; i<fileList.size(); i++){
			File localFile = fileList.get(i);
			if (localFile.exists()) {
				localFile.delete();
			}
		}
		return resultInfo;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0003")
	public ResultInfo fixWholesaleAndAcptPrice(HttpServletRequest request, @RequestBody Category catePris){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			
			catePris.setUpdateUser(memberId);
//			JSONObject jsonObject = JSONObject.fromObject(catePris);
			Gson gson = new Gson();
			String jsonObject = gson.toJson(catePris);
			log.info("writeData-In0003:"+jsonObject.toString());
			readResult = (ResultInfo) service.invoke("writeData-In0003", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0004")
	public ResultInfo fixMidPrice(HttpServletRequest request, @RequestBody Category cat){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			cat.setUpdateUser(memberId);
//			JSONObject jsonObject = JSONObject.fromObject(cat);
			Gson gson = new Gson();
			String jsonObject = gson.toJson(cat);
			log.info("writeData-In0004:"+jsonObject.toString());
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
		if ("200".equals(result.get("code").toString())) {
			List<String> ids = (List<String>)map.get("ids");
			List<Customer> cuss = new ArrayList<Customer>();
			for(String cusId : ids){
				Customer cus = new Customer();
				cus.setCustomerId(cusId);
				cus.setUpdateUser(memberId);
				cuss.add(cus);
			}
			JSONArray jsonObject = JSONArray.fromObject(cuss);
//			Gson gson = new Gson();
//			String jsonObject = gson.toJson(cuss);
			log.info("writeData-In0005:"+jsonObject.toString());
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
		if ("200".equals(result.get("code").toString())) {
			List<String> ids = (List<String>)map.get("ids");
			List<Customer> cuss = new ArrayList<Customer>();
			for(String cusId : ids){
				Customer cus = new Customer();
				cus.setCustomerId(cusId);
				cus.setUpdateUser(memberId);
				cuss.add(cus);
			}
			JSONArray jsonObject = JSONArray.fromObject(cuss);
//			Gson gson = new Gson();
//			String jsonObject = gson.toJson(cuss);
			log.info("writeData-In0006:"+jsonObject.toString());
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
//			JSONObject jsonObject = JSONObject.fromObject(catePri);
			Gson gson = new Gson();
			String jsonObject = gson.toJson(catePri);
			log.info("writeData-In0007:"+jsonObject.toString());
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
//			JSONObject jsonObject = JSONObject.fromObject(cat);
			Gson gson = new Gson();
			String jsonObject = gson.toJson(cat);
			log.info("writeData-In0008:"+jsonObject.toString());
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
//			Gson gson = new Gson();
//			String jsonObject = gson.toJson(customer);
			log.info("writeData-In0009:"+jsonObject.toString());
			readResult = (ResultInfo) service.invoke("writeData-In0009", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	/**
	 * 删除联系人的接口
	 * @param request
	 * @param con 包含contactsInfoId,联系人记录ID
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0010")
	public ResultInfo deleteContact(HttpServletRequest request, @RequestBody ContactsInfo con){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			con.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(con);
			log.info("writeData-In0010:"+jsonObject.toString());
			readResult = (ResultInfo) service.invoke("writeData-In0010", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 联系人修改接口
	 * @param request
	 * @param con 包含contactsInfoId(联系人记录ID),contactPhone(联系人手机),contactTel(联系人电话),
	 * contactName(联系人姓名),contactAddr(联系人地址),contactMail(联系人邮箱),comm(备注信息),其中ID、姓名、手机为必填字段。
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/In0011")
	public ResultInfo modifyContact(HttpServletRequest request, @RequestBody ContactsInfo con){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			con.setUpdateUser(memberId);
			Gson gson = new Gson();
			String json = gson.toJson(con);
			log.info("writeData-In0011:"+json);
			readResult = (ResultInfo) service.invoke("writeData-In0011", "POST", json, ResultInfo.class);
		}
		return readResult;
	}
	
	//发起交易的接口
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
			//JSONObject jsonObject = JSONObject.fromObject(tx);
			Gson gson = new Gson();
			String json = gson.toJson(tx);
			log.info("writeData-Dl0001:"+json);
			readResult = (ResultInfo) service.invoke("writeData-Dl0001", "POST", json, ResultInfo.class);
		}
		return readResult;
	}
	
	//回盘接口
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
//			JSONObject jsonObject = JSONObject.fromObject(tx);
			Gson gson = new Gson();
			String jsonObject = gson.toJson(tx);
			log.info("writeData-Dl0002:"+jsonObject.toString());
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
//			JSONObject jsonObject = JSONObject.fromObject(tx);
			Gson gson = new Gson();
			String jsonObject = gson.toJson(tx);
			log.info("writeData-Dl0003:"+jsonObject.toString());
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
//			JSONObject jsonObject = JSONObject.fromObject(tx);
			Gson gson = new Gson();
			String jsonObject = gson.toJson(tx);
			log.info("writeData-Dl0004:"+jsonObject.toString());
			readResult = (ResultInfo) service.invoke("writeData-Dl0004", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	//用户定盘接口
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
//			JSONObject jsonObject = JSONObject.fromObject(contract);
			Gson gson = new Gson();
			String jsonObject = gson.toJson(contract);
			log.info("writeData-Dl0005:"+jsonObject.toString());
			readResult = (ResultInfo) service.invoke("writeData-Dl0005", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 总经理进行合同审定接口
	 * 入参：合同ID
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/Dl0006")
	public ResultInfo checkContract(HttpServletRequest request, @RequestBody ContractInfo contract){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			contract.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(contract);
//			Gson gson = new Gson();
//			String jsonObject = gson.toJson(contract);
			log.info("writeData-Dl0006:"+jsonObject.toString());
			readResult = (ResultInfo) service.invoke("writeData-Dl0006", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 行政人员上传完文件后提交审核的接口
	 * 入参：合同文件路径contUlName，contractInfoId合同ID。
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/Dl0007")
	public ResultInfo contractStamp(HttpServletRequest request, @RequestBody ContractInfo contract){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			contract.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(contract);
//			Gson gson = new Gson();
//			String jsonObject = gson.toJson(contract);
			log.info("writeData-Dl0007:"+jsonObject.toString());
			readResult = (ResultInfo) service.invoke("writeData-Dl0007", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 合同下载接口
	 * 入参：contractInfoId合同ID
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/Dl0008")
	public ResultInfo downloadContract(HttpServletRequest request, @RequestBody ContractInfo contract){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			JSONObject jsonObject = JSONObject.fromObject(contract);
//			Gson gson = new Gson();
//			String jsonObject = gson.toJson(contract);
			log.info("writeData-Dl0008:"+jsonObject.toString());
			readResult = (ResultInfo) service.invoke("writeData-Dl0008", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 财务审核接口
	 * 入参：contractInfoId合同ID
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/Dl0009")
	public ResultInfo treasurerCheckContract(HttpServletRequest request, @RequestBody ContractInfo contract){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			contract.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(contract);
//			Gson gson = new Gson();
//			String jsonObject = gson.toJson(contract);
			log.info("writeData-Dl0009:"+jsonObject.toString());
			readResult = (ResultInfo) service.invoke("writeData-Dl0009", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 填写快递单号接口
	 * 入参：contractInfoId合同ID，expressId快递单号
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/Dl0010")
	public ResultInfo setExpressId(HttpServletRequest request, @RequestBody ContractInfo contract){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			contract.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(contract);
//			Gson gson = new Gson();
//			String jsonObject = gson.toJson(contract);
			log.info("writeData-Dl0010:"+jsonObject.toString());
			readResult = (ResultInfo) service.invoke("writeData-Dl0010", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 经理取消交易的接口
	 * 入参：transactionInfoId合同ID，clRea取消原因。
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/writeData/Dl0011")
	public ResultInfo cancleTransaction(HttpServletRequest request, @RequestBody TransactionInfo tx){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			tx.setUpdateUser(memberId);
			JSONObject jsonObject = JSONObject.fromObject(tx);
//			Gson gson = new Gson();
//			String jsonObject = gson.toJson(tx);
			log.info("writeData-Dl0011:"+jsonObject.toString());
			readResult = (ResultInfo) service.invoke("writeData-Dl0011", "POST", jsonObject.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id:R2001
	 * 客户经理,分部经理 ，总经理查询所属的客户信息列表，分页，条件查询（名称）
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
			log.info("readData-R2001:"+jsonUser.toString());
			readResult = (ResultInfo) service.invoke("readData-R2001", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id:R2002
	 * 客户经理,分部经理 ，总经理查询客户的详细信息
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
			log.info("readData-R2002:"+jsonUser.toString());
			readResult = (ResultInfo) service.invoke("readData-R2002", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	} 
	
	/**
	 * 接口id:R2003
	 * 查看品类列表，分页查询 ,条件查询（品名）
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
		if("200".equals(result.get("code").toString())){
//			category.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(category);
			log.info("readData-R2003:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2003", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}

	/**
	 * 接口id:R2004
	 * 查看品类详情
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
		if ("200".equals(result.get("code").toString())) {
			JSONObject jsonUser = JSONObject.fromObject(category);
			log.info("readData-R2004:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2004", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}

	
	/**
	 * 接口id:R2005
	 * 客户经理、部门经理查看信息采集列表
	 * 部门经理，已报盘/未报盘
	 * @param request
	 * @param member
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/readData/R2005")
	public ResultInfo listRelCategoryPrices(HttpServletRequest request, @RequestBody Category category) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			category.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(category);
			log.info("readData-R2005:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2005", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	 /** 接口id:R2006
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
		if ("200".equals(result.get("code").toString())) {
			category.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(category);
			log.info("readData-R2006:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2006", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id:R2007
	 * 客户经理，分部经理，角色委员会 查看交易信息列表
	 * @param request
	 * @param member
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/readData/R2007")
	public ResultInfo listTransactions(HttpServletRequest request,@RequestBody TransactionInfo transactioninfo){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			transactioninfo.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(transactioninfo);
			log.info("readData-R2007:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2007", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	/**
	 * 接口id:R2008
	 * 客户经理，分部经理，决策委员会查看交易详情
	 * @param request
	 * @param member
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/readData/R2008")
	public ResultInfo  transactionInfo(HttpServletRequest request,@RequestBody TransactionInfo transactionInfo){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			transactionInfo.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(transactionInfo);
			log.info("readData-R2008:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2008", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	/**
	 * 接口id:R2009
	 * 总经理查看合同信息
	 * @param request
	 * @param contractInfo
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/readData/R2009")
	public ResultInfo  listContract(HttpServletRequest request,@RequestBody ContractInfo contractinfo){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
//			contractinfo.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(contractinfo);
			log.info("readData-R2009:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2009", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id:R2011
	 * 发起交易(客户信息的选择)
	 * @author lujin
	 * @date 2017-1-13
	 * @param request
	 * @param transactionInfo
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/readData/R2011")
	public ResultInfo  startTransactionByCu(HttpServletRequest request,@RequestBody Customer customer){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			customer.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(customer);
			log.info("readData-R2011:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2011", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	/**
	 * 接口id:R2012
	 * 发起交易(品类信息的选择)
	 * @author lujin
	 * @date 2017-1-13
	 * @param request
	 * @param transactionInfo
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/readData/R2012")
	public ResultInfo  startTransactionByCa(HttpServletRequest request,@RequestBody Customer customer){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			JSONObject jsonUser = JSONObject.fromObject(customer);
			log.info("readData-R2012:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2012", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id：R2013
	 * 总经理查看合同详情
	 * @param request
	 * @param contractInfo
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2013")
	public ResultInfo ContractInfo(HttpServletRequest request, @RequestBody ContractInfo contractInfo) {
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId, token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			JSONObject jsonUser = JSONObject.fromObject(contractInfo);
			contractInfo.setCreateUser(memberId);
			log.info("readData-R2013:"+jsonUser.toString());
			readResult = (ResultInfo) service.invoke("readData-R2013", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id：R2014
	 * 品类的采集信息详情(未采集)
	 * 品类id
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2014")
	public ResultInfo categoryPriceInfoNoPricategory(HttpServletRequest request,@RequestBody Category category){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			category.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(category);
			log.info("readData-R2014:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2014", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	/**
	 * 接口id：R2015
	 * 国际部回盘列表(未回盘)
	 * 品类id
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2015")
	public ResultInfo notCtofPrice(HttpServletRequest request,@RequestBody TransactionInfo transactionInfo){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			transactionInfo.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(transactionInfo);
			log.info("readData-R2015:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2015", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	/**
	 * 接口id：R2016
	 * 国际部回盘列表(已回盘)
	 * 品类id
	 */
	/*@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2016")
	public ResultInfo ctofPrice(HttpServletRequest request,@RequestBody TransactionInfo transactionInfo){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			transactionInfo.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(transactionInfo);
			readResult =(ResultInfo)service.invoke("readData-R2016", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}*/
	/**
	 *  接口id：R2017
	 * @author lujin
	 * @date 2017-1-22
	 * 国际采购人员 回盘与报盘 详情
	 * 返回该交易的客户与品类的信息
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2017")
	public ResultInfo CustAndCateInfo(HttpServletRequest request,@RequestBody TransactionInfo transactionInfo){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			JSONObject jsonUser = JSONObject.fromObject(transactionInfo);
			log.info("readData-R2017:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2017", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id：R2018
	 * @author lujin
	 * @date 2017-1-23
	 * 发起交易(选择品类信息)
	 * 通过品类id，创建者，得到创建者的客户信息
	 * @param request
	 * @param category
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2018")
	public ResultInfo CustInfoByCateId(HttpServletRequest request,@RequestBody Category category){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			category.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(category);
			log.info("readData-R2018:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2018", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id：R2019
	 * @author lujin
	 * @date 2017-1-23
	 * 国际采购部，查看品类的报盘详情
	 * 通过品类id,得到品类的报价详情
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/readData/R2019")
	public ResultInfo offerPriceInfoByCateId(HttpServletRequest request,@RequestBody Category category){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			JSONObject jsonUser = JSONObject.fromObject(category);
			log.info("readData-R2019:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2019", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id：R2020
	 * 决策委员会日志报表（客户信息）
	 * @param customer 分页查 page对象 
	 * @author lujin
	 * @date 2017-1-21
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2020")
	public ResultInfo cusckLogCustomer(HttpServletRequest request,@RequestBody Customer customer){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			//customer.setCreateUser(memberId);
			JSONObject jsonUser = JSONObject.fromObject(customer);
			log.info("readData-R2020:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2020", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	/**
	 * 接口id：R2021
	 * 决策委员会日志报表（品类信息）
	 * @param customer 分页查 page对象 
	 * @author lujin
	 * @date 2017-1-21
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/readData/R2021")
	public ResultInfo cusckLogCategory(HttpServletRequest request,@RequestBody RelCategoryPrice relCategoryPrice){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			JSONObject jsonUser = JSONObject.fromObject(relCategoryPrice);
			log.info("readData-R2021:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2021", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	
	/**
	 * 接口id:R2022
	 * 决策委员会日志报表（实时交易）
	 * @author lujin
	 * @date 2017-1-21
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/readData/R2022")
	public ResultInfo cusckLogTranscation(HttpServletRequest request,@RequestBody TransactionInfo transactionInfo){
		ResultInfo readResult = new ResultInfo();
		String token = request.getHeader("token");
		String memberId = request.getHeader("memberId");
		Map<String, Object> result = Jwt.validToken(memberId,token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			JSONObject jsonUser = JSONObject.fromObject(transactionInfo);
			log.info("readData-R2022:"+jsonUser.toString());
			readResult =(ResultInfo)service.invoke("readData-R2022", "POST", jsonUser.toString(), ResultInfo.class);
		}
		return readResult;
	}
	/**
	 * 接口id：upgrade
	 * 版本更新
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/upgrade")
	public ResultInfo ctofPrice(HttpServletRequest request, @RequestBody Upgrade upgrade){
		ResultInfo readResult = new ResultInfo();
		
		JSONObject jsonUser = JSONObject.fromObject(upgrade);
		log.info("readData-upgrade:"+jsonUser.toString());
		readResult =(ResultInfo)service.invoke("readData-upgrade", "POST", jsonUser.toString(), ResultInfo.class);
		
		return readResult;
	}
}
