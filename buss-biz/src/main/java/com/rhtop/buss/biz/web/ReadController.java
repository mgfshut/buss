package com.rhtop.buss.biz.web;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
import com.rhtop.buss.biz.service.CusckLogService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.DealLogService;
import com.rhtop.buss.biz.service.MemberService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.biz.service.TransactionInfoService;
import com.rhtop.buss.biz.service.UpgradeService;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.CodeMap;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.RelCustomerCategory;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.common.entity.Upgrade;
import com.rhtop.buss.common.utils.Constant;
import com.rhtop.buss.common.web.BaseController;

@RestController
@RequestMapping(value = "service/readData")
public class ReadController  extends BaseController {
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
	@Autowired
	private UpgradeService upgradeService;
	@Autowired
	private CusckLogService cusckSer;
	@Autowired
	private DealLogService delSer;
	
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
		readResult.setMessage("数据获取成功！");
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
		readResult.setCode("200");
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
		List<Category> listCategorys = catSer.listPageCategoryByIntf(category);
		readResult.setCode("200");
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
		Category cate = catSer.selectInfoByPrimaryKey(category.getCategoryId());
		readResult.setCode("200");
		readResult.setMessage("数据获取成功！");
		readResult.setResObject(cate);
		return readResult;
	}
	
	/**
	 * 接口id：R2005
	 * 客户经理，部门经理  ,总经理 查询 信息采集 
	 * 国际采购部 已报盘/未报盘
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2005")
	public ResultInfo listRelcategoryPrice(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Category category = (Category) JSONObject.toBean(jsonObject, Category.class);
		List<Category> categoeylist =catSer.listPageCategoeyByPrice(category);
		readResult.setCode("200");
		readResult.setRecords(categoeylist);
		readResult.setMessage("数据获取成功！");
		readResult.setPage(category.getPage());
		return readResult;
	}
	
	/**
	 * 接口id：R2006
	 * 品类的采集信息详情(已采集)
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
		List<RelCategoryPrice> rcps = catPriSer.listRelCategoryPrices(relCategoryPrice, category.getCreateUser());
		cate.setRcps(rcps);
		readResult.setCode("200");
		readResult.setMessage("数据获取成功！");
		readResult.setResObject(cate);
		return readResult;
	}
	
	/**
	 * 接口id：R2014
	 * 品类的采集信息详情(未采集)
	 * 品类id
	 * @date 2017-1-18
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2014")
	public ResultInfo categoryPriceInfoNoPri(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Category category = (Category) JSONObject.toBean(jsonObject, Category.class);
		//品类信息
		Category cate = catSer.selectByPrimaryKey(category.getCategoryId());
		if (cate != null){
			//获取品类渠道信息（客户与品类的关系表）
			List<RelCustomerCategory> recacu = cusCatSer.selectCuscha(category);
			try{
				cate.setRcacu(recacu);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			readResult.setCode("200");
			readResult.setMessage("数据获取成功！");
		}else{
			readResult.setCode("400");
			readResult.setMessage("品类信息不存在！");
		}
		
		readResult.setResObject(cate);
		return readResult;
	}
	
	
	
	/**
	 * 接口id：R2007
	 * 客户经理,部门经理 ,决策委员会（交易状态大于等于21） 查看交易列表
	 * @author lujin
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2007")
	public ResultInfo listTransactions(@RequestParam ("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		TransactionInfo transactioninfo = (TransactionInfo) JSONObject.toBean(jsonObject,TransactionInfo.class);
		transactioninfo.setCreateUser(transactioninfo.getCreateUser());
		List<TransactionInfo> tras = traSer.listPageTransactionInfoBycreateUser(transactioninfo);
		readResult.setCode("200");
		readResult.setPage(transactioninfo.getPage());
		readResult.setMessage("数据获取成功！");
		readResult.setRecords(tras);
		return readResult;
	}

	/**
	 * 接口id：R2011
	 * 发起交易 (对客户信息的查询)
	 * 客户经理，分部经理 查询自己创建的客户信息
	 * @author lujin
	 * @date 2017-1-13
	 * @param body
	 * @return readResult
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2011")
	public ResultInfo startTransactionByCu(@RequestParam ("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Customer customer = (Customer) JSONObject.toBean(jsonObject,Customer.class);
		//查询客户信息(通过审核了的)
//		customer.setCkStatus("02");
		//查询客户信息
		List<Customer> customerlist = cusSer.listPageCustByCreateUser(customer);
		readResult.setCode("200");
		readResult.setMessage("数据获取成功！");
		readResult.setPage(customer.getPage());
		readResult.setRecords(customerlist);
		readResult.setPage(customer.getPage());
		return readResult;
	}
	
	/**
	 * 接口id：R2012
	 * 发起交易(对品类信息的查询)
	 * 客户经理，分部经理 查询所选客户所属的品类以及品类的报盘价不能为空
	 * @author lujin
	 * @date 2017-1-13
	 * @param body
	 * @return readResult
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2012")
	public ResultInfo startTransactionByCa(@RequestParam ("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Customer customer = (Customer) JSONObject.toBean(jsonObject,Customer.class);
		//查询客户所属的品类信息
		List<Category> categorylist = catSer.listPageByCustomerAndPrice(customer.getCustomerId());
		readResult.setMessage("数据获取成功！");
		readResult.setCode("200");
		readResult.setPage(customer.getPage());
		readResult.setRecords(categorylist);
		return readResult;
	}
	
	
	/**
	 * 接口id：R2008
	 * 客户经理，分部经理，决策委员会查看交易详情
	 * @author lujin
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2008")
	public ResultInfo transactionInfo(@RequestParam ("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		TransactionInfo transactionInfo = (TransactionInfo) JSONObject.toBean(jsonObject,TransactionInfo.class);
		TransactionInfo tras = traSer.selectTransactionInfo(transactionInfo);
		readResult.setMessage("数据获取成功！");
		readResult.setCode("200");
		readResult.setResObject(tras);
		readResult.setPage(transactionInfo.getPage());
		return readResult;
	}
	/**
	 * 接口id：R2015
	 * 品类id
	 * 国际采购人员 回盘列表(未回盘)
	 * 国际采购人员 回盘列表(已回盘)
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2015")
	public ResultInfo notCtofPrice(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		TransactionInfo transactionInfo = (TransactionInfo) JSONObject.toBean(jsonObject, TransactionInfo.class);
		transactionInfo.setqType("20");
		List<TransactionInfo> trans = traSer.listPageInfo(transactionInfo);
		if (trans != null && trans.size() > 0 ){
			readResult.setCode("200");
			readResult.setPage(transactionInfo.getPage());
			readResult.setRecords(trans);
			readResult.setMessage("数据获取成功！");
		}else{
			readResult.setCode("200");
			readResult.setMessage("不存在未回盘的信息！");
		}
		return readResult;
	    }
	
	/**
	 * 接口id：R2016
	 * 品类id
	 * 国际采购人员 回盘列表(已回盘)
	 *//*
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2016")
	public ResultInfo ctofPrice(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		TransactionInfo transactionInfo = (TransactionInfo) JSONObject.toBean(jsonObject, TransactionInfo.class);
		//已回盘列表:交易状态为21
		transactionInfo.setTxStatus("21");
		List<TransactionInfo> trans = traSer.listPageInfo(transactionInfo);
		if (trans.size()!=0){
			readResult.setCode("200");
			readResult.setRecords(trans);
			readResult.setPage(transactionInfo.getPage());
			readResult.setMessage("数据获取成功！");
		}else{
			readResult.setCode("200");
			readResult.setMessage("不存在已回盘的信息！");
		}
		return readResult;
	    }*/
	
	/**
	 * 接口id：R2017
	 * @author lujin
	 * @date 2017-1-22
	 * 国际采购人员 回盘  详情
	 * 返回该交易的客户与品类的信息以及交易记录详情
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2017")
	public ResultInfo CustAndCateInfo(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		TransactionInfo transactionInfo = (TransactionInfo) JSONObject.toBean(jsonObject, TransactionInfo.class);
		TransactionInfo tran = traSer.CustAndCateAndPriceInfo(transactionInfo);
		readResult.setCode("200");
		readResult.setMessage("数据获取成功！");
		readResult.setResObject(tran);
		return readResult;
	    }
	
	/**
	 * 接口id：R2018
	 * @author lujin
	 * @date 2017-1-23
	 * 发起交易(选择品类信息)
	 * 通过品类id，创建者，得到创建者的客户信息
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2018")
	public ResultInfo CustInfoByCateId(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Category category = (Category) JSONObject.toBean(jsonObject, Category.class);
		List<Customer> custList = cusSer.selectCustByCateId(category);
		readResult.setCode("200");
		readResult.setMessage("数据获取成功！");
		readResult.setPage(category.getPage());
		readResult.setResObject(custList);
		return readResult;
	    }
	
	/**
	 * 接口id：R2019
	 * @author lujin
	 * @date 2017-1-23
	 * 国际采购部，查看品类的 报盘 详情
	 * 通过品类id,得到品类的报价详情
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2019")
	public ResultInfo offerPriceInfoByCateId(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Category category = (Category) JSONObject.toBean(jsonObject, Category.class);
		Category cate = catSer.custOfferPiceInfo(category);
		readResult.setCode("200");
		readResult.setMessage("数据获取成功！");
		readResult.setResObject(cate);
		return readResult;
	    }
	
	/**
	 * 接口id:R2020
	 * 决策委员会日志报表（客户采集）
	 * @author lujin
	 * @date 2017-1-21
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2020")
	public ResultInfo cusckLogCustomer(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Customer customer = (Customer) JSONObject.toBean(jsonObject,Customer.class);
		List<Customer> custs = cusckSer.cusCkLogCustomer(customer);
		if(custs == null || custs.size() == 0){
			readResult.setMessage("无信息!");
		}else{
			readResult.setMessage("数据获取成功！");
		}
		readResult.setCode("200");
		readResult.setPage(customer.getPage());
		readResult.setRecords(custs);
		return readResult;
	}
	/**
	 * 接口id:R2021
	 * 决策委员会日志报表（品类报盘）
	 * @author lujin
	 * @date 2017-1-21
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2021")
	public ResultInfo cusckLogCategory(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		RelCategoryPrice relCategoryPrice = (RelCategoryPrice) JSONObject.toBean(jsonObject,RelCategoryPrice.class);
		List<RelCategoryPrice> rcps =  cusckSer.cusCkLogRelCategoryPrice(relCategoryPrice);
		if(rcps == null || rcps.size() == 0){
			readResult.setMessage("无信息!");
		}else{
			readResult.setMessage("数据获取成功！");
		}
		readResult.setCode("200");
		readResult.setRecords(rcps);
		readResult.setPage(relCategoryPrice.getPage());
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
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2022")
	public ResultInfo cusckLogTranscation(@RequestParam("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		TransactionInfo transactionInfo = (TransactionInfo) JSONObject.toBean(jsonObject,TransactionInfo.class);
		List<TransactionInfo> trans =  delSer.DealLogList(transactionInfo);
		if(trans == null || trans.size() == 0){
			readResult.setMessage("无信息!");
		}else{
			readResult.setMessage("数据获取成功！");
		}
		readResult.setCode("200");
		readResult.setRecords(trans);
		readResult.setPage(transactionInfo.getPage());
		return readResult;
	}
	
	/**
	 * 接口id：R2009
	 * 总经理查看合同列表(无状态区分，所有状态)
	 * @param body
	 * @author lujin
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2009")
	public ResultInfo listContract(@RequestParam ("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		ContractInfo contractInfo = (ContractInfo) JSONObject.toBean(jsonObject,ContractInfo.class);
		try {
			List<ContractInfo> conts = contractSer.listPageContractInfo(contractInfo);
			for (int i=0; i<conts.size(); i++){
				String cusName = cusSer.selectByPrimaryKey(conts.get(i).getCustomerId()).getCusName();
				String cateName = catSer.selectByPrimaryKey(conts.get(i).getCategoryId()).getCateName();
				conts.get(i).setBuyName(cusName);
				conts.get(i).setCateName(cateName);
			}
			readResult.setRecords(conts);	
			readResult.setCode("200");
			readResult.setMessage("数据获取成功！");
			readResult.setPage(contractInfo.getPage());
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
			return readResult;
		}
		
		return readResult;
	}
	/**
	 * 接口id:R2013
	 * 客户经理 分部经理查看合同详情
	 * @author lujin
	 * @date 2017-1-13
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/R2013")
	public ResultInfo ContractInfo(@RequestParam ("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		ContractInfo contractInfo = (ContractInfo) JSONObject.toBean(jsonObject,ContractInfo.class);
		ContractInfo conts = null;
		try {
		    conts = contractSer.listContractInfos(contractInfo).get(0);
			readResult.setCode("200");
			readResult.setMessage("数据获取成功！");
			readResult.setResObject(conts);
		} catch (Exception e) {
			readResult.setCode("200");
			readResult.setMessage("数据获取失败！");
			readResult.setResObject(conts);
		}
		return readResult;
	}
	/**
	 * 接口id:upgrade
	 * 查看程序最新版本号
	 * @author 李彬彬
	 * @date 2017年1月20日
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/upgrade")
	public ResultInfo upgrade(@RequestParam ("body") String body){
		ResultInfo readResult = new ResultInfo();
		JSONObject jsonObject = JSONObject.fromObject(body);
		Upgrade upgrade = (Upgrade) JSONObject.toBean(jsonObject, Upgrade.class);
		Upgrade appUpgrade = null;
		try{
			appUpgrade = upgradeService.selectDownload(upgrade);
			if (appUpgrade != null && StringUtils.isNotEmpty(appUpgrade.getDownloadFiles())){
				appUpgrade.setDownloadFiles(Constant.HTTPURL + appUpgrade.getDownloadFiles());
			}
		}catch(Exception e){
			log.error("版本更新查询失败["+body+"]", e);
		}
		readResult.setCode("200");
		readResult.setMessage("数据获取成功！");
		readResult.setResObject(appUpgrade);
		return readResult;
	}
}
