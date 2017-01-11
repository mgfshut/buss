package com.rhtop.buss.biz.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhtop.buss.biz.service.BusinessDiaryService;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.biz.service.ContractInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.DealLogService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.biz.service.SlaTransactionInfoService;
import com.rhtop.buss.biz.service.TransactionInfoService;
import com.rhtop.buss.common.entity.BusinessDiary;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.DealLog;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.RelCustomerCategory;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.common.utils.FileUtil;
import com.rhtop.buss.common.web.BaseController;
/**
 * 对外接口的写入功能控制器，内部接口按照操作类型分为两类，
 * 信息采集相关接口的命名为前缀In+四位编号0001依次递增,
 * 交易和合同相关接口的命名微前缀Dl+四位编号0001依次递增。
 * @author MakeItHappen
 *
 */
@RestController
@RequestMapping(value="service/writeData")
//配置跨域支持
@CrossOrigin
public class WriteController extends BaseController{
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
	private TransactionInfoService txSer;
	@Autowired
	private ContractInfoService conSer;
	@Autowired
	private SlaTransactionInfoService salTxSer;
	@Autowired
	private DealLogService dlogSer;
	
	/**
	 * 客户经理第一次录入客户信息、联系人信息、品类信息的接口
	 * @param customer 客户信息对象 包含联系人对象list、品类对象list
	 * @return 
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0001")
	public ResultInfo addCustomerAndCategory(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		Customer customer = null;
		try{
			customer = mapper.readValue(body, Customer.class);
		}catch(Exception e){
			log.error("[WriteController.addCustomerAndCategory]数据解析异常", e);
		}
		 
		String userId = customer.getUpdateUser();
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		//检查传进来的客户对象是否为空，为空则返回错误信息码并结束操作。
		if(customer.getCusName().trim().equals("")||customer.getCusName()==null 
			||customer.getCusCha().trim().equals("")||customer.getCusCha()==null 
			||customer.getCusLoc().trim().equals("")||customer.getCusLoc()==null 
			||customer.getCusType().trim().equals("")||customer.getCusType()==null){
			readResult.setCode("500");
			readResult.setMessage("客户信息不能为空！");
			return readResult;
		}
		
		List<ContactsInfo> contacts = customer.getContacts();
		List<Category> categorys = customer.getCategorys();
		//客户对象不为空，完善客户对象
		customer.setCreateUser(userId);
		customer.setUpdateUser(userId);
		String customerId = UUID.randomUUID().toString().replace("-", "");
		customer.setCustomerId(customerId);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		customer.setCreateTime(now);
		customer.setUpdateTime(now);
		customer.setCusCreateTime(now);
		customer.setCkStatus("00");
		//向数据库中添加一条客户数据。
		try {
			//添加客户数据
			cusSer.insertCustomer(customer);
		} catch (Exception e) {
			e.printStackTrace();
			readResult.setMessage("操作失败，新增客户出错。");
			readResult.setCode("500");
			return readResult;
		}
		//新增联系人
		//检查是否有新增需求
		if(!contacts.isEmpty()){
			//有新增的联系人记录，加入数据库
			for(ContactsInfo contact : contacts){
				if(contact.getContactName().trim().equals("")||contact.getContactName()==null||contact.getContactPhone().trim().equals("")||contact.getContactPhone()==null){
					continue;
				}else{
					contact.setCreateUser(userId);
					contact.setUpdateUser(userId);
					contact.setCreateTime(now);
					contact.setCreateUser(userId);
					contact.setContactsInfoId(UUID.randomUUID().toString().replace("-", ""));
					contact.setCustomerId(customerId);
					contactsSer.insertContactsInfo(contact);
				}
			}
		}
		//新增品类
		//检查是否有新增需求
		if(!categorys.isEmpty()){
			for(Category cat : categorys){
				//检查要新增的品类是否已存在于数据库中
				if(catSer.checkCategoryExist(cat)==null){
					//新增品类
					cat.setCategoryId(UUID.randomUUID().toString().replace("-", ""));
					cat.setCreateTime(now);
					cat.setCreateUser(userId);
					catSer.insertCategory(cat);
					RelCustomerCategory relCustomerCategory = new RelCustomerCategory();
					relCustomerCategory.setCreateTime(now);
					relCustomerCategory.setUpdateTime(now);
					relCustomerCategory.setUpdateUser(userId);
					relCustomerCategory.setCreateUser(userId);
					relCustomerCategory.setCategoryId(cat.getCategoryId());
					relCustomerCategory.setCateScale(cat.getCateScale());
					relCustomerCategory.setCooInten(cat.getCooInten());
					relCustomerCategory.setCooIntenComm(cat.getCooIntenComm());
					relCustomerCategory.setRelCustomerCategoryId(UUID.randomUUID().toString().replace("-", ""));
					relCustomerCategory.setCusLoc(customer.getCusLoc());
					cusCatSer.insertRelCustomerCategory(relCustomerCategory);
				}else{
					continue;
				}
			}
		}
		try{
			//添加一条操作记录
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("01");//0开头是客户操作、1是品类操作、2是价格操作、3是交易操作、4是合同操作、5文件操作。||后面一位1为新增、2修改、3删除。
			bd.setOprName("客户新增");
			bd.setOprContent(body);
			busDiaSer.insertBusinessDiary(bd);
		}catch(Exception e){
			log.error("[WriteController.addCustomerAndCategory]操作日志记录异常", e);
		}
		
		
		return readResult;
	}
	
	/**
	 * 图片上传接口
	 * 在客户经理创建客户的过程中新增品类时涉及到上传图片，
	 * 要求ContentType为MultipartFile，
	 * 返回值为文件在服务器中的相对路径。
	 * 返回值需要被记录到一个名为catePic的字段中，在保存品类信息时提交上来。
	 * @param picFile
	 * @return 文件相对路径
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0002")
	public ResultInfo uploadPic(HttpServletRequest request, MultipartFile picFile, @RequestParam("memberId") String memberId){
		String userId = memberId;
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		String catePic = null;
		try {
			catePic = FileUtil.uploadOneFile(picFile);
			readResult.setResObject(catePic);
		} catch (Exception e) {
			log.error("[WriteController.uploadPic]图片上传异常", e);
		}
		//添加一条操作记录
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("51");
			bd.setOprName("图片上传");
			bd.setOprContent(catePic);
			busDiaSer.insertBusinessDiary(bd);
		} catch (Exception e) {
			log.error("[WriteController.uploadPic]操作日志记录异常", e);
		}
		return readResult;
	}

	
	
	/**
	 * 客户经理的信息采集菜单中采集信息的接口
	 * @param request 拿token做校验
	 * @param catePris 一个RelCategoryPrice对象集合。
	 * @return
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0003")
	public ResultInfo fixWholesaleAndAcptPrice(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		RelCategoryPrice catePri = null;
		try{
			catePri = mapper.readValue(body, RelCategoryPrice.class);
		}catch(Exception e){
			log.error("[WriteController.addCustomerAndCategory]数据解析异常", e);
		}
		
		String userId = catePri.getUpdateUser();
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		
		try {
			catePri.setMgrId(userId);
			catPriSer.createOrUpdateWholesaleAndAcptPriceByCategoryId(catePri);
		} catch (Exception e) {
			e.printStackTrace();
			readResult.setCode("500");
			readResult.setMessage("更新失败！");
			log.error("[WriteController.fixWholesaleAndAcptPrice]数据更新失败", e);
		}
		//新增一条操作记录
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("22");
			bd.setOprName("客户经理完善批发价与接盘价格。");
			bd.setOprContent(body);
			busDiaSer.insertBusinessDiary(bd);
		} catch (Exception e) {
			log.error("[WriteController.fixWholesaleAndAcptPrice]日志记录异常", e);
		}
		return readResult;
	}

	
	/**
	 * 分部经理完善现货价、半期货价、期货价的接口
	 * @param request 拿token做校验
	 * @param catePri 品类价格对象，包含品类ID，现货、半期货、期货价的最大最小值。
	 * @return 状态码和状态消息
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0004")
	public ResultInfo fixMidPrice(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		RelCategoryPrice catePri = null;
		try{
			catePri = mapper.readValue(body, RelCategoryPrice.class);
		}catch(Exception e){
			log.error("[WriteController.addCustomerAndCategory]数据解析异常", e);
		}
		String userId = catePri.getUpdateUser();
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		try {
			catePri.setRegMgrId(userId);
			catPriSer.createOrUpdateMidPriceByCategoryId(catePri);
		} catch (Exception e) {
			e.printStackTrace();
			readResult.setCode("500");
			readResult.setMessage("更新失败！");
			log.error("[WriteController.fixMidPrice]数据更新失败", e);
		}
		//新增一条操作记录
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("22");
			bd.setOprName("分部经理完善现货价、期货价、半期货价。");
			bd.setOprContent(body);
			busDiaSer.insertBusinessDiary(bd);
		} catch (Exception e) {
			log.error("[WriteController.fixMidPrice]日志记录异常", e);
		}
		return readResult;
	}
	
	/**
	 * 分部经理确认客户录入的接口（可批量确认）
	 * @param request 拿token做校验
	 * @param cuss 需要被确认的客户对象
	 * @return 更新状态
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0005")
	public ResultInfo commitNewCustomerLevelOne(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		List<Customer> cuss = null;
		try{
			//接List的写法
			cuss = mapper.readValue(body, new TypeReference<List<Customer>>() {});
		}catch(Exception e){
			log.error("[WriteController.commitNewCustomerLevelOne]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		String userId = null;
		for(Customer cus : cuss){
			cus.setUpdateTime(now);
			userId = cus.getUpdateUser();
			try {
				int status = cusSer.checkCustomer(1, userId, cus);
				if(status==0){
					readResult.setMessage("确认完成！");
				}else if(status == 1){
					readResult.setCode("500");
					readResult.setMessage("后台异常！");
				}else if(status == 2){
					readResult.setCode("500");
					readResult.setMessage("审核流程错误！");
				}
			} catch (Exception e) {
				log.error("[WriteController.commitNewCustomerLevelOne]数据更新失败", e);
			}
		}
		//新增一条操作记录
		try {
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("02");
			bd.setOprName("分部经理批量确认新增客户信息");
			bd.setOprContent(body);
			busDiaSer.insertBusinessDiary(bd);
		} catch (Exception e) {
			log.error("[WriteController.commitNewCustomerLevelOne]日志记录异常", e);
		}
		return readResult;
	}
	/**
	 * 总经理确认客户录入的接口（可批量确认）
	 * @param request 拿token做校验
	 * @param cuss 客户信息List
	 * @return
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0006")
	public ResultInfo commitNewCustomerLevelTwo(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		List<Customer> cuss = null;
		try{
			//接List的写法
			cuss = mapper.readValue(body, new TypeReference<List<Customer>>() {});
		}catch(Exception e){
			log.error("[WriteController.commitNewCustomerLevelTwo]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		String userId = null;
		try {
			for(Customer cus : cuss){
				cus.setUpdateTime(now);
				userId = cus.getUpdateUser();
				try {
					int status = cusSer.checkCustomer(2, userId, cus);
					if(status==0){
						readResult.setMessage("确认完成！");
					}else if(status == 1){
						readResult.setCode("500");
						readResult.setMessage("后台异常！");
					}else if(status == 2){
						readResult.setCode("500");
						readResult.setMessage("审核流程错误！");
					}
				} catch (Exception e) {
					log.error("[WriteController.commitNewCustomerLevelTwo]数据更新失败", e);
				}
			}
		} catch (Exception e) {
			log.error("[WriteController.commitNewCustomerLevelTwo]数据更新失败", e);
		}
			
		//新增一条操作记录
		try {
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("02");
			bd.setOprName("总经理批量确认新增客户信息");
			bd.setOprContent(body);
			busDiaSer.insertBusinessDiary(bd);
		} catch (Exception e) {
			log.error("[WriteController.commitNewCustomerLevelTwo]日志记录异常", e);
		}
		return readResult;
	}
	/**
	 * 国际部完善品类报盘信息的接口
	 * @param catPri 一个品类价格关系对象
	 * @return
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0007")
	public ResultInfo fixOfferPrice(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		RelCategoryPrice catePri = null;
		try{
			catePri = mapper.readValue(body, RelCategoryPrice.class);
		}catch(Exception e){
			log.error("[WriteController.fixOfferPrice]数据解析异常", e);
		}
		String userId = catePri.getUpdateUser();
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		try {
			catePri.setUpdateTime(now);
			catePri.setUpdateUser(userId);
			catePri.setUniMgrId(userId);
			catePri.setOfferUpdateTime(now);
			catPriSer.createOrUpdateOfferPriceAndTimeByCategoryId(catePri);
		} catch (Exception e) {
			log.error("[WriteController.fixOfferPrice]数据更新异常", e);
		}
		//记录日志
		try {
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("22");
			bd.setOprName("国际部完善品类报盘信息的接口");
			bd.setOprContent(body);
			busDiaSer.insertBusinessDiary(bd);
		} catch (Exception e) {
			log.error("[WriteController.fixOfferPrice]日志记录异常", e);
		}
		
		return readResult;
	}
	/**
	 * 国际部新增品类信息接口
	 * @param request 拿token做验证
	 * @param cat 一个品类信息对象
	 * @return
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0008")
	public ResultInfo universeAddCategory(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		Category cat = null;
		try{
			cat = mapper.readValue(body, Category.class);
		}catch(Exception e){
			log.error("[WriteController.universeAddCategory]数据解析异常", e);
		}
		String userId = cat.getUpdateUser();
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		try {
			//检查要新增的品类是否已存在于数据库中
			if(catSer.checkCategoryExist(cat)==null){
				//新增品类
				cat.setCategoryId(UUID.randomUUID().toString().replace("-", ""));
				cat.setCreateTime(now);
				cat.setCreateUser(userId);
				catSer.insertCategory(cat);
				//新增品类价格关系数据
				RelCategoryPrice catPri = new RelCategoryPrice();
				catPri.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
				catPri.setUniMgrId(userId);
				catPri.setUpdateTime(now);
				catPri.setUpdateUser(userId);
				catPri.setCateSup(cat.getCateSup());
				catPri.setCurrency(cat.getCurrency());
				catPri.setUnit(cat.getUnit());
				catPri.setOfferAging(cat.getOfferAging());
				catPri.setCatePri(cat.getCatePri());
				catPri.setCreateUser(userId);
				catPri.setCreateTime(now);
				catPriSer.createOrUpdateOfferPriceAndTimeByCategoryId(catPri);
			}else{
				//品类信息存在，更新品类信息
				cat.setUpdateTime(now);
				cat.setUpdateUser(userId);
				catSer.updateCategory(cat);
				//更新品类价格信息
				RelCategoryPrice catPri = new RelCategoryPrice();
				catPri.setUniMgrId(userId);
				catPri.setUpdateTime(now);
				catPri.setUpdateUser(userId);
				catPri.setCateSup(cat.getCateSup());
				catPri.setCurrency(cat.getCurrency());
				catPri.setUnit(cat.getUnit());
				catPri.setOfferAging(cat.getOfferAging());
				catPri.setCatePri(cat.getCatePri());
				catPriSer.createOrUpdateOfferPriceAndTimeByCategoryId(catPri);
			}
		} catch (Exception e) {
			log.error("[WriteController.universeAddCategory]数据更新异常", e);
		}
		//记录日志
		try {
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("11");
			bd.setOprName("国际部新增品类信息");
			bd.setOprContent(body);
			busDiaSer.insertBusinessDiary(bd);
		} catch (Exception e2) {
			log.error("[WriteController.universeAddCategory]日志记录异常", e2);
		}
		
		return readResult;
	}
	/**
	 * 客户经理和分部经理编辑（更新）客户、品类、联系人信息的接口
	 * @param request 拿token做验证
	 * @param customer 用户对象，包含一个用户和与之对应的联系人和品类
	 * @return
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0009")
	public ResultInfo updateCustomerAndCategory(@RequestBody String body){
		ObjectMapper mapper = new ObjectMapper();
		Customer customer = null;
		try{
			customer = mapper.readValue(body, Customer.class);
		}catch(Exception e){
			log.error("[WriteController.updateCustomerAndCategory]数据解析异常", e);
		}
		String userId = customer.getUpdateUser();
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		try {
			List<ContactsInfo> contacts = customer.getContacts();
			List<Category> categorys = customer.getCategorys();
			//客户对象不为空，完善客户对象
			customer.setUpdateUser(userId);
			customer.setUpdateTime(now);
			String customerId = customer.getCustomerId();
			//数据库更新一条客户数据。
			try {
				//添加客户数据
				cusSer.updateCustomer(customer);
			} catch (Exception e) {
				e.printStackTrace();
				readResult.setMessage("操作失败，更新客户出错。");
				readResult.setCode("500");
				return readResult;
			}
			//更新联系人
			if(!contacts.isEmpty()){
				for(ContactsInfo contact : contacts){
					//检查该联系人是否有记录ID，有则更新，无则创建。
					if(contact.getContactsInfoId().trim().equals("")||contact.getContactsInfoId()==null){
						contact.setCreateUser(userId);
						contact.setCreateTime(now);
						contact.setContactsInfoId(UUID.randomUUID().toString().replace("-", ""));
						contact.setCustomerId(customerId);
						contactsSer.insertContactsInfo(contact);
					}else{
						contact.setUpdateUser(userId);
						contact.setUpdateTime(now);
						contactsSer.updateContactsInfo(contact);
					}
				}
			}
			//更新品类
			if(!categorys.isEmpty()){
				for(Category cat : categorys){
					//检查要新增的品类是否已存在于数据库中
					if(catSer.checkCategoryExist(cat)==null){
						//新增品类
						cat.setCategoryId(UUID.randomUUID().toString().replace("-", ""));
						cat.setCreateTime(now);
						cat.setCreateUser(userId);
						catSer.insertCategory(cat);
						RelCustomerCategory relCustomerCategory = new RelCustomerCategory();
						relCustomerCategory.setCreateTime(now);
						relCustomerCategory.setCreateUser(userId);
						relCustomerCategory.setCategoryId(cat.getCategoryId());
						relCustomerCategory.setCateScale(cat.getCateScale());
						relCustomerCategory.setCooInten(cat.getCooInten());
						relCustomerCategory.setCooIntenComm(cat.getCooIntenComm());
						relCustomerCategory.setRelCustomerCategoryId(UUID.randomUUID().toString().replace("-", ""));
						relCustomerCategory.setCusLoc(customer.getCusLoc());
						cusCatSer.insertRelCustomerCategory(relCustomerCategory);
					}else{
						continue;
					}
				}
			}
		} catch (Exception e) {
			log.error("[WriteController.updateCustomerAndCategory]数据更新异常", e);
		}	
		//添加一条操作记录
		try {
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("02");
			bd.setOprName("客户经理和分部经理编辑（更新）客户、品类、联系人信息");
			bd.setOprContent(body);
			busDiaSer.insertBusinessDiary(bd);
		} catch (Exception e) {
			log.error("[WriteController.updateCustomerAndCategory]日志记录异常", e);
		}
		return readResult;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0001")
	public ResultInfo makeADeal(@RequestBody String body){
		ObjectMapper mapper = new ObjectMapper();
		TransactionInfo tx = null;
		try{
			tx = mapper.readValue(body, TransactionInfo.class);
		}catch(Exception e){
			log.error("[WriteController.makeADeal]数据解析异常", e);
		}
		String userId = tx.getUpdateUser();
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(date);
		String date1 = sdf2.format(date);
		tx.setUpdateTime(now);
		tx.setUpdateUser(userId);
		tx.setDealTime(date1);
		String txId = null;
		try {
			txId = txSer.createDeal(tx);
		} catch (Exception e) {
			log.error("[WriteController.makeADeal]数据新增异常", e);
		}
		try {
			DealLog dlog = new DealLog();
			dlog.setOprUser(userId);
			dlog.setOprTime(now);
			dlog.setTransactionInfoId(txId);
			dlog.setDealLogId(UUID.randomUUID().toString().replace("-",""));
			dlog.setOprType("31");
			dlog.setOprName("发起交易");
			dlog.setOprContent(body);
		} catch (Exception e) {
			log.error("[WriteController.makeADeal]日志记录异常", e);
		}
		return readResult;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0002")
	public ResultInfo makeNegotiate(@RequestBody String body){
		ObjectMapper mapper = new ObjectMapper();
		TransactionInfo tx = null;
		try{
			tx = mapper.readValue(body, TransactionInfo.class);
		}catch(Exception e){
			log.error("[WriteController.makeNegotiate]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		String userId = tx.getUpdateUser();
		tx.setUpdateTime(now);
		tx.setUpdateUser(userId);
		tx.setTxAmo(null);
		try {
			txSer.cusNegotiate(tx);
		} catch (Exception e) {
			log.error("[WriteController.makeNegotiate]数据更新异常", e);
		}
		try {
			DealLog dlog = new DealLog();
			dlog.setOprUser(userId);
			dlog.setOprTime(now);
			dlog.setTransactionInfoId(tx.getTransactionInfoId());
			dlog.setDealLogId(UUID.randomUUID().toString().replace("-",""));
			dlog.setOprType("32");
			dlog.setOprName("客户回盘");
			dlog.setOprContent(body);
		} catch (Exception e) {
			log.error("[WriteController.makeNegotiate]日志记录异常", e);
		}
		return readResult;
	}
	
	/**
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0003")
	public ResultInfo universeNegotiate(@RequestBody String body){
		ObjectMapper mapper = new ObjectMapper();
		TransactionInfo tx = null;
		try{
			tx = mapper.readValue(body, TransactionInfo.class);
		}catch(Exception e){
			log.error("[WriteController.universeNegotiate]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		String userId = tx.getUpdateUser();
		tx.setUpdateTime(now);
		tx.setTxStatus("21");
		try {
			txSer.universeNegotiate(tx);
		} catch (Exception e) {
			log.error("[WriteController.universeNegotiate]数据更新异常", e);
		}
		try {
			DealLog dlog = new DealLog();
			dlog.setOprUser(userId);
			dlog.setOprTime(now);
			dlog.setTransactionInfoId(tx.getTransactionInfoId());
			dlog.setDealLogId(UUID.randomUUID().toString().replace("-",""));
			dlog.setOprType("32");
			dlog.setOprName("国际部回盘");
			dlog.setOprContent(body);
		} catch (Exception e) {
			log.error("[WriteController.universeNegotiate]日志记录异常", e);
		}
		return readResult;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0004")
	public ResultInfo domainNegotiate(@RequestBody String body){
		ObjectMapper mapper = new ObjectMapper();
		TransactionInfo tx = null;
		try{
			tx = mapper.readValue(body, TransactionInfo.class);
		}catch(Exception e){
			log.error("[WriteController.domainNegotiate]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		String userId = tx.getUpdateUser();
		tx.setUpdateTime(now);
		tx.setTxStatus("22");
		try {
			txSer.domainNegotiate(tx);
		} catch (Exception e) {
			log.error("[WriteController.domainNegotiate]数据更新异常", e);
		}
		try {
			DealLog dlog = new DealLog();
			dlog.setOprUser(userId);
			dlog.setOprTime(now);
			dlog.setTransactionInfoId(tx.getTransactionInfoId());
			dlog.setDealLogId(UUID.randomUUID().toString().replace("-",""));
			dlog.setOprType("32");
			dlog.setOprName("决委会审核回盘");
			dlog.setOprContent(body);
		} catch (Exception e) {
			log.error("[WriteController.domainNegotiate]日志记录异常", e);
		}
		return readResult;
	}
}
