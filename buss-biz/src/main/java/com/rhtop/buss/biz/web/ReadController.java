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
import com.rhtop.buss.biz.service.ContractInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.MemberService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.biz.service.TransactionInfoService;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.CodeMap;
import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.TransactionInfo;

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
	@Autowired
	private TransactionInfoService traSer;  
	@Autowired
	private ContractInfoService contractSer;
	
	
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
	/**
	 * 接口id：R2001
	 * 【客户信息】
	 * 客户经理 ：客户信息查询
	 * 部门经理： 客户信息查询
	 * 总经理    ： 客户信心查询
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
		readResult.setPage(customer.getPage());
		return readResult;
	}
	
	/**
	 * 接口id：R2002
	 * 客户经理，分部经理 查看 客户的详细信息
	 * 根据客户的id 
	 * 包括 联系人列表，以及品类列表信息
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/R2002")
	public ResultInfo customerInfo(@RequestParam("body") String body) {
		JSONObject jsonObject = JSONObject.fromObject(body);
		Customer customer = (Customer) JSONObject.toBean(jsonObject, Customer.class);
		ResultInfo readResult = new ResultInfo();
		customer.setCustomerId(customer.getCustomerId());
		Customer cus = cusSer.selectCustomerInfo(customer);
		readResult.setMessage("数据获取成功！");
		readResult.setResObject(cus);
		return readResult;
	}
	
	/**
	 * 接口id:R2003
	 * 查询 品类信息
	 * 查询所有， 分页查询 根据 品名查询
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/R2003")
	public ResultInfo listCategorys(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Category category = (Category) JSONObject.toBean(jsonObject, Category.class);
		category.setCusLoc(category.getCusLoc());
		List<Category> listCategorys = catSer.listPageCategory(category);
		readResult.setMessage("数据获取成功！");
		readResult.setRecords(listCategorys);
		readResult.setPage(category.getPage());
		return readResult;
	}
	
	/**
	 * 接口id：R2004
	 * 查询品类的详细信息
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/R2004")
	public ResultInfo categoryInfo(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Category category = (Category) JSONObject.toBean(jsonObject, Category.class);
		Category cate = catSer.selectByPrimaryKey(category.getCategoryId());
		readResult.setMessage("数据获取成功！");
		readResult.setResObject(cate);
		return readResult;
	}
	
	/**
	 * 接口id：R2005
	 * 客户经理，部门经理  查询 信息采集 
	 * 国际采购部 已报盘/未报盘
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2005")
	public ResultInfo listRelcategoryPrice(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Member member = (Member) JSONObject.toBean(jsonObject, Member.class);
		List<Category> categoeylist =catSer.listPageCategoeyByPrice(member.getMemberId());
		readResult.setMessage("数据获取成功！");
		readResult.setRecords(categoeylist);
		readResult.setPage(member.getPage());
		return readResult;
	}
	
	/**
	 * 接口id：R2010
	 * 国际采购部 已报盘/未报盘
	 */
	/*
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2010")
	public ResultInfo listRelcategoryPriceByUniMgr(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Member member = (Member) JSONObject.toBean(jsonObject, Member.class);
		List<Category> categoeylist =catSer.listPageCategoeyByPrice(member.getMemberId());
		readResult.setMessage("数据获取成功！");
		readResult.setRecords(categoeylist);
		readResult.setPage(member.getPage());
		return readResult;
	}*/
	
	/**
	 * 接口id：R2006
	 * 品类的采集信息详情
	 * 品类id
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2006")
	public ResultInfo categoryPriceInfo(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Category category = (Category) JSONObject.toBean(jsonObject, Category.class);
		//品类信息
		Category cate = catSer.selectByPrimaryKey(category.getCategoryId());
		//获取品类价格信息
		RelCategoryPrice relCategoryPrice = new RelCategoryPrice();
		relCategoryPrice.setCategoryId(category.getCategoryId());
		List<RelCategoryPrice> rcps = catPriSer.listRelCategoryPrices(relCategoryPrice);
		cate.setRcps(rcps);
		readResult.setMessage("数据获取成功！");
		readResult.setResObject(cate);
		return readResult;
	}
	
	
	/**
	 * 接口id：R2007
	 * 客户经理查看交易列表
	 * @author lujin
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2007")
	public ResultInfo listtransactions(@RequestParam ("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		TransactionInfo transactionInfo = (TransactionInfo) JSONObject.toBean(jsonObject,TransactionInfo.class);
		List<TransactionInfo> tras = traSer.listPageTransactionInfoBycreateUser(transactionInfo);
		readResult.setMessage("数据获取成功！");
		readResult.setRecords(tras);
		return readResult;
	}
	
	/**
	 * 接口id：R2008
	 * 客户经理查看交易详情
	 * @author lujin
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2008")
	public ResultInfo transaction(@RequestParam ("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		TransactionInfo transactionInfo = (TransactionInfo) JSONObject.toBean(jsonObject,TransactionInfo.class);
		TransactionInfo tras = traSer.selectByPrimaryKey(transactionInfo.getTransactionInfoId());
		readResult.setMessage("数据获取成功！");
		readResult.setResObject(tras);
		return readResult;
	}
	
	/**
	 * 接口id：R2009
	 * 总经理查看合同列表
	 * @param body
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2009")
	public ResultInfo listContract(@RequestParam ("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		ContractInfo contractInfo = (ContractInfo) JSONObject.toBean(jsonObject,ContractInfo.class);
		List<ContractInfo> conts = contractSer.listPageContractInfo(contractInfo);
		readResult.setMessage("数据获取成功！");
		readResult.setResObject(conts);
		return readResult;
	}
}
