package com.rhtop.buss.biz.web;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhtop.buss.biz.service.BusinessDiaryService;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.CodeMapService;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.MemberService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.CodeMap;
import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.ResultInfo;

@RestController
@RequestMapping(value = "service/readData")
public class ReadController {
	@Autowired
	private CategoryService catSer;
	@Autowired
	private CustomerService cusSer;
	@Autowired
	private ContactsInfoService contactsSer;
	@Autowired
	private RelCategoryPriceService catPriSer;
	@Autowired
	private RelCustomerCategoryService cusCatSer;
	@Autowired
	private BusinessDiaryService busDiaSer;
	@Autowired
	private MemberService memberService;
	@Autowired
	private CodeMapService codeMapService;
	
	/**
	 * 接口id：R2001
	 * 客户经理 客户信息查询 
	 * 根据传入的用户的id，客户类型，渠道，地区，查与之相关的客户信息
	 * 
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2001")
	public ResultInfo listCustomers(@RequestParam("body") String body) {
		JSONObject jsonObject=JSONObject.fromObject(body);
		Customer customer = (Customer)JSONObject.toBean(jsonObject, Customer.class);
		ResultInfo readResult = new ResultInfo();
		List<Customer> customers = cusSer.listPageCustomer(customer);
		readResult.setCode("200");
		readResult.setRecords(customers);
		return readResult;
	}
	
	/**
	 * 接口id：R2002
	 * 客户经理 查看 客户的详细信息
	 * 根据客户的id
	 * 包括 联系人，以及品类信息
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/R2002")
	public ResultInfo customerInfo(@RequestParam("body") String body) {
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Customer customer = (Customer) JSONObject.toBean(jsonObject, Customer.class);
		// 查询客户信息
		customer.setCustomerId(customer.getCustomerId());
		Customer cus = cusSer.selectCustomerInfo(customer).get(0);
		// 查询联系人
		ContactsInfo contactsinfo = new ContactsInfo();
		contactsinfo.setCustomerId(customer.getCustomerId());
		List<ContactsInfo> conts = contactsSer.listContactsInfos(contactsinfo);
		// 查询品类
		List<Category> cates = catSer.listCategoryByCustomer(customer.getCustomerId());
		// 添加联系人和品类
		cus.setCategorys(cates);
		cus.setContacts(conts);
		readResult.setMessage("数据获取成功！");
		readResult.setResObject(cus);
		return readResult;
	}
	
	/**
	 * 接口id:R2003
	 * 客户经理  查询 品类信息
	 * 查询所有， 根据地区,品名，厂号查询
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/R2003")
	public ResultInfo listCategorys(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		return readResult;
	}
	
	/**
	 * 查询所有代码集和代码值
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/getAllCodeMap")
	public ResultInfo getAllCodeMap(){
		ResultInfo readResult = new ResultInfo();
		List<CodeMap> codeMapList = codeMapService.listAllCode();
		readResult.setCode("200");
		readResult.setRecords(codeMapList);
		return readResult;
	}
	/*@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2003")
	public ReadResult<List<Category>>  listCategorys(HttpServletRequest request,
			@RequestBody Category category) {
		ReadResult<List<Category>> readResult = new ReadResult<List<Category>>();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			category.setCusLoc(category.getCusLoc());
			List<Category> listCategorys = catSer.listCategorys(category);
			readResult.setMessage("数据获取成功！");
			readResult.setResObject(listCategorys);
		}
		return readResult;
	}*/
	
	/**
	 * 接口id：R2004
	 * 客户经理 查询品类的详细信息
	 */
	/*@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2004")
	public ReadResult<Category>  categoryInfo(HttpServletRequest request,
			@RequestBody Category category) {
		ReadResult<Category> readResult = new ReadResult<Category>();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			 category.setCusLoc(category.getCusLoc());
			 Category cate = catSer.selectByPrimaryKey(category.getCategoryId());
			 //TODO 需要返回的是图片在服务器上的绝对路径 
			 cate.setCatePic(""+cate.getCatePic());
			readResult.setMessage("数据获取成功！");
			readResult.setResObject(cate);
		}
		return readResult;
	}*/
	
	/**
	 * 接口id：R2005
	 * 客户经理  查询 信息采集 
	 * 客户经理已采集
	 * 所有未采集
	 */
	/*@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2005")
	public ReadResult<List<Category>>  listRelCategoryPrices(HttpServletRequest request,
			@RequestBody Member member) {
		ReadResult<List<Category>> readResult = new ReadResult<List<Category>>();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			List<Category> categoeylist =catSer.listCategoeyByPrice(member.getMemberId());
			readResult.setMessage("数据获取成功！");
			readResult.setResObject(categoeylist);
		}
		return readResult;
	}*/
	
	/**
	 * 接口id：R2006
	 * 品类的采集信息详情
	 * 品类id
	 */
/*	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2006")
	public ReadResult<Category> categoryPriceInfo(HttpServletRequest request,
			@RequestBody Category category ) {
		ReadResult<Category>  readResult = new ReadResult<Category> ();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			readResult.setMessage("数据获取成功！");
			Category cate = catSer.selectByPrimaryKey(category.getCategoryId());
			RelCategoryPrice relCategoryPrice = new RelCategoryPrice();
			relCategoryPrice.setCategoryId(category.getCategoryId());
			//获取品类价格信息
			List<RelCategoryPrice> rcps = catPriSer.listRelCategoryPrices(relCategoryPrice);
			cate.setRcps(rcps);
			readResult.setMessage("数据获取成功！");
			readResult.setResObject(cate);
			
		}
		return readResult;
	}
*/	 
	
	
	
	
}
