package com.rhtop.buss.biz.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhtop.buss.biz.service.BusinessDiaryService;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.CodeValueService;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.biz.service.ContractInfoService;
import com.rhtop.buss.biz.service.CusckLogService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.DealLogService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.biz.service.SlaTransactionInfoService;
import com.rhtop.buss.biz.service.TransactionInfoService;
import com.rhtop.buss.common.entity.BusinessDiary;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.CusckLog;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.DealLog;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.common.utils.DateUtils;
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
	@Autowired
	private CusckLogService clSer;
	@Autowired
	private CodeValueService codeValueService;
	
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
		ResultInfo readResult = new ResultInfo();
		String userId = customer.getUpdateUser();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		customer.setCreateTime(now);
		customer.setUpdateTime(now);
		customer.setCusCreateTime(now);
		readResult.setCode("200");
		try {
			cusSer.addCustomer(readResult, customer); 
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
			log.error("[WriteController.addCustomerAndCategory]数据更新异常", e);
		}
		try{
			//添加一条操作记录
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("01");//0开头是客户操作、1是品类操作、2是价格操作、3是交易操作、4是合同操作、5文件操作。||后面一位1为新增、2修改、3删除、4查看。
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
	 * @param files
	 * @return 文件相对路径
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0002")
	public ResultInfo uploadPic(HttpServletRequest request, MultipartFile files, @RequestParam("memberId") String memberId){
		String userId = memberId;
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		String catePic = null;
		try {
			catePic = FileUtil.uploadPic(files);
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
		Category catePris = null;
		try{
			catePris = mapper.readValue(body, Category.class);
		}catch(Exception e){
			log.error("[WriteController.addCustomerAndCategory]数据解析异常", e);
		}
		List<RelCategoryPrice> rcps = catePris.getRcps();
		String userId = catePris.getUpdateUser();
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		
		try {
			readResult = catPriSer.createOrUpdateWholesaleAndAcptPriceByCategoryId(readResult,rcps, catePris.getCategoryId(), userId);
		} catch (Exception e) {
			e.printStackTrace();
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
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
	 * 分部经理完善(解盘价、批发价、)现货价、半期货价、期货价的接口
	 * @param request 拿token做校验
	 * @param catePri 品类价格对象，包含品类ID，现货、半期货、期货价的最大最小值。
	 * @return 状态码和状态消息
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0004")
	public ResultInfo fixMidPrice(@RequestParam("body") String body){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		ObjectMapper mapper = new ObjectMapper();
		Category catePri = null;
		try{
			catePri = mapper.readValue(body, Category.class);
		}catch(Exception e){
			log.error("[WriteController.addCustomerAndCategory]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		List<RelCategoryPrice> rcps = catePri.getRcps();
		String userId = catePri.getUpdateUser();
		for(RelCategoryPrice rcp : rcps){
			rcp.setUpdateUser(userId);
			rcp.setUpdateTime(now);
			rcp.setCategoryId(catePri.getCategoryId());
		}
		try {
			readResult = catPriSer.createOrUpdateMidPriceByCategoryId(readResult, rcps);
		} catch (Exception e) {
			e.printStackTrace();
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
			log.error("[WriteController.fixMidPrice]数据更新失败", e);
			return readResult;
		}
		//新增一条操作记录
		try {
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("22");
			bd.setOprName("分部经理完善(解盘价、批发价、)现货价、期货价、半期货价。");
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
				readResult.setCode("500");
				readResult.setMessage(e.getMessage());
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
			CusckLog cl = new CusckLog();
			cl.setOprTime(now);
			cl.setOprUser(userId);
			for(Customer cus : cuss){
				cl.setCusckLogId(UUID.randomUUID().toString().replace("-", ""));
				cl.setCustomerId(cus.getCustomerId());
				clSer.insertCusckLog(cl);
			}
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
					readResult.setCode("500");
					readResult.setMessage(e.getMessage());
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
			CusckLog cl = new CusckLog();
			cl.setOprTime(now);
			cl.setOprUser(userId);
			for(Customer cus : cuss){
				cl.setCusckLogId(UUID.randomUUID().toString().replace("-", ""));
				cl.setCustomerId(cus.getCustomerId());
				clSer.insertCusckLog(cl);
			}
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
		String now = DateUtils.getNowTime();
		try {
			catePri.setUpdateTime(now);
			catePri.setUpdateUser(userId);
			catePri.setUniMgrId(userId);
			catePri.setOfferUpdateTime(now);
			catPriSer.createOrUpdateOfferPriceAndTimeByCategoryId(readResult,catePri);
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
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
		String now = DateUtils.getNowTime();
		cat.setUpdateTime(now);
		try {
			readResult = catPriSer.universeAddCategoryAndPrice(readResult, cat);
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
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
	public ResultInfo updateCustomerAndCategory(@RequestParam("body") String body){
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
		customer.setUpdateTime(now);
		//客户对象不为空，完善客户对象
		customer.setUpdateUser(userId);
		customer.setUpdateTime(now);
		try {
			cusSer.updateCustomerInfo(readResult,customer);
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
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
	public ResultInfo makeADeal(@RequestParam("body") String body){
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
//		tx.setCtofPri(null);
		String txId = null;
		try {
			txId = txSer.createDeal(tx);
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
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
	public ResultInfo makeNegotiate(@RequestParam("body") String body){
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
			txSer.cusNegotiate(readResult,tx);
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
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
	public ResultInfo universeNegotiate(@RequestParam("body") String body){
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
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
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
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
			log.error("[WriteController.universeNegotiate]日志记录异常", e);
		}
		return readResult;
	}
	
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0004")
	public ResultInfo domainNegotiate(@RequestParam("body") String body){
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
		String endTime = sdf.format(new Date(date.getTime()+Integer.parseInt(tx.getCtofAging())*60*60*1000));
		tx.setEndTime(endTime);
		try {
			txSer.domainNegotiate(tx);
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
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
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0005")
	public ResultInfo createContract(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		ContractInfo con = null;
		try{
			con = mapper.readValue(body, ContractInfo.class);
		}catch(Exception e){
			log.error("[WriteController.createContract]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		String now = DateUtils.getNowTime();
		String userId = con.getUpdateUser();
		con.setUpdateTime(now);
		try {
			conSer.createContract(con);
		} catch (Exception e) {
			log.error("[WriteController.createContract]数据更新异常", e);
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
		}
		try {
			DealLog dlog = new DealLog();
			dlog.setOprUser(userId);
			dlog.setOprTime(now);
			dlog.setTransactionInfoId(con.getTransactionInfoId());
			dlog.setDealLogId(UUID.randomUUID().toString().replace("-",""));
			dlog.setOprType("41");
			dlog.setOprName("合同创建");
			dlog.setOprContent(body);
		} catch (Exception e) {
			log.error("[WriteController.createContract]日志记录异常", e);
		}
		return readResult;
	}
	
	/**
	 * 合同盖章后上传的接口
	 * @param request
	 * @param contracts
	 * @param memberId
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/uploadContracts")
	public ResultInfo uploadContract(HttpServletRequest request, MultipartFile[] contracts, @RequestParam("memberId") String memberId){
		String userId = memberId;
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		String contUlName = null;
		try {
			String contractName = null;
			for(int i = 0; i < contracts.length ; i++){
				contractName = FileUtil.uploadContract(contracts[i]);
				if(i==contracts.length){
					contUlName += contractName;
				}else{
					contUlName += contractName+"|";
				}
			}
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
			log.error("[WriteController.uploadPic]图片上传异常", e);
		}
		readResult.setResObject(contUlName);
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
			bd.setOprName("合同上传");
			bd.setOprContent(contUlName);
			busDiaSer.insertBusinessDiary(bd);
		} catch (Exception e) {
			log.error("[WriteController.uploadPic]操作日志记录异常", e);
		}
		return readResult;
	}
	
	/**
	 * 总经理合同审定接口
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0006")
	public ResultInfo checkContract(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		ContractInfo con = null;
		try{
			con = mapper.readValue(body, ContractInfo.class);
		}catch(Exception e){
			log.error("[WriteController.checkContract]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		String userId = con.getUpdateUser();
		con.setUpdateTime(now);
		try {
			conSer.checkContract(con);
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
			log.error("[WriteController.checkContract]数据更新异常", e);
		}
		try {
			DealLog dlog = new DealLog();
			dlog.setOprUser(userId);
			dlog.setOprTime(now);
			dlog.setTransactionInfoId(con.getTransactionInfoId());
			dlog.setDealLogId(UUID.randomUUID().toString().replace("-",""));
			dlog.setOprType("42");
			dlog.setOprName("总经理合同审定");
			dlog.setOprContent(body);
		} catch (Exception e) {
			log.error("[WriteController.checkContract]日志记录异常", e);
		}
		return readResult;
	}
	/**
	 * 合同盖章上传后行政审核接口
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0007")
	public ResultInfo contractStamp(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		ContractInfo con = null;
		try{
			con = mapper.readValue(body, ContractInfo.class);
		}catch(Exception e){
			log.error("[WriteController.contractStamp]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		String userId = con.getUpdateUser();
		con.setUpdateTime(now);
		try {
			conSer.contractStamp(con);
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
			log.error("[WriteController.contractStamp]数据更新异常", e);
		}
		try {
			DealLog dlog = new DealLog();
			dlog.setOprUser(userId);
			dlog.setOprTime(now);
			dlog.setTransactionInfoId(con.getTransactionInfoId());
			dlog.setDealLogId(UUID.randomUUID().toString().replace("-",""));
			dlog.setOprType("42");
			dlog.setOprName("行政合同审定、盖章");
			dlog.setOprContent(body);
		} catch (Exception e) {
			log.error("[WriteController.contractStamp]日志记录异常", e);
		}
		return readResult;
	}
	/**
	 * 合同下载接口
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0008")
	public ResultInfo downloadContract(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		ContractInfo con = null;
		try{
			con = mapper.readValue(body, ContractInfo.class);
		}catch(Exception e){
			log.error("[WriteController.downloadContract]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		String userId = con.getUpdateUser();
		con.setUpdateTime(now);
		try {
			List<String> urlList = conSer.downloadContract(con);
			readResult.setResObject(urlList);
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
			log.error("[WriteController.downloadContract]数据更新异常", e);
		}
		try {
			DealLog dlog = new DealLog();
			dlog.setOprUser(userId);
			dlog.setOprTime(now);
			dlog.setTransactionInfoId(con.getTransactionInfoId());
			dlog.setDealLogId(UUID.randomUUID().toString().replace("-",""));
			dlog.setOprType("54");
			dlog.setOprName("合同下载");
			dlog.setOprContent(body);
		} catch (Exception e) {
			log.error("[WriteController.downloadContract]日志记录异常", e);
		}
		return readResult;
	}
	/**
	 * 财务审核接口
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0009")
	public ResultInfo treasurerCheckContract(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		ContractInfo con = null;
		try{
			con = mapper.readValue(body, ContractInfo.class);
		}catch(Exception e){
			log.error("[WriteController.treasurerCheckContract]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		String userId = con.getUpdateUser();
		con.setUpdateTime(now);
		try {
			conSer.treasurerCheckContract(con);
		} catch (Exception e) {
			log.error("[WriteController.treasurerCheckContract]数据更新异常", e);
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
		}
		try {
			DealLog dlog = new DealLog();
			dlog.setOprUser(userId);
			dlog.setOprTime(now);
			dlog.setTransactionInfoId(con.getTransactionInfoId());
			dlog.setDealLogId(UUID.randomUUID().toString().replace("-",""));
			dlog.setOprType("42");
			dlog.setOprName("财务审核合同");
			dlog.setOprContent(body);
		} catch (Exception e) {
			log.error("[WriteController.treasurerCheckContract]日志记录异常", e);
		}
		return readResult;
	}
	
	/**
	 * 填写快递单号接口
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0010")
	public ResultInfo setExpressId(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		ContractInfo con = null;
		try{
			con = mapper.readValue(body, ContractInfo.class);
		}catch(Exception e){
			log.error("[WriteController.setExpressId]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		String userId = con.getUpdateUser();
		try {
			con.setUpdateUser(null);
			conSer.updateContractInfo(con);
		} catch (Exception e) {
			log.error("[WriteController.setExpressId]数据更新异常", e);
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
		}
		try {
			DealLog dlog = new DealLog();
			dlog.setOprUser(userId);
			dlog.setOprTime(now);
			dlog.setTransactionInfoId(con.getTransactionInfoId());
			dlog.setDealLogId(UUID.randomUUID().toString().replace("-",""));
			dlog.setOprType("42");
			dlog.setOprName("填写快递单号");
			dlog.setOprContent(body);
		} catch (Exception e) {
			log.error("[WriteController.setExpressId]日志记录异常", e);
		}
		return readResult;
	}
	
	/**
	 * 经理取消合同的接口
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/Dl0011")
	public ResultInfo cancleTransaction(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		TransactionInfo tx = null;
		try{
			tx = mapper.readValue(body, TransactionInfo.class);
		}catch(Exception e){
			log.error("[WriteController.cancleTransaction]数据解析异常", e);
		}
		ResultInfo readResult = new ResultInfo();
		readResult.setCode("200");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		String userId = tx.getUpdateUser();
		tx.setUpdateTime(now);
		tx.setTxStatus("70");
		try {
			txSer.updateTransactionInfo(tx);
		} catch (Exception e) {
			log.error("[WriteController.cancleTransaction]数据更新异常", e);
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
		}
		try {
			DealLog dlog = new DealLog();
			dlog.setOprUser(userId);
			dlog.setOprTime(now);
			dlog.setTransactionInfoId(tx.getTransactionInfoId());
			dlog.setDealLogId(UUID.randomUUID().toString().replace("-",""));
			dlog.setOprType("42");
			dlog.setOprName("取消合同");
			dlog.setOprContent(body);
		} catch (Exception e) {
			log.error("[WriteController.cancleTransaction]日志记录异常", e);
		}
		return readResult;
	}
	
	/**
	 * 增加代码值
	 * @author mgf
	 * @date 2017年1月14日 上午10:42:49 
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/addCodeValue")
	public ResultInfo addCodeValue(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		CodeValue codeValue =  null;
		try{
			codeValue = mapper.readValue(body, CodeValue.class);
		}catch(Exception e){
			log.error("[WriteController.addCodeValue]数据解析异常", e);
		}
		
		ResultInfo resultInfo = new ResultInfo();
		int i = codeValueService.addCodeValue(codeValue);
		if(i>0){
			resultInfo.setCode("200");
		}else{
			resultInfo.setCode("500");
			resultInfo.setMessage("插入数据失败");
		}
		return resultInfo;
	}
	
	/**
	 * 删除代码值
	 * @author mgf
	 * @date 2017年1月14日 上午10:42:49 
	 * @param body
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/deleteCodeValue")
	public ResultInfo deleteCodeValue(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		CodeValue codeValue =  null;
		try{
			codeValue = mapper.readValue(body, CodeValue.class);
		}catch(Exception e){
			log.error("[WriteController.addCodeValue]数据解析异常", e);
		}
		
		ResultInfo resultInfo = new ResultInfo();
		int i = codeValueService.deleteCodeValue(codeValue.getCodeValueId());
		if(i>0){
			resultInfo.setCode("200");
		}else{
			resultInfo.setCode("500");
			resultInfo.setMessage("删除数据失败");
		}
		return resultInfo;
	}
	
}
