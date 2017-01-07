package com.rhtop.buss.biz.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rhtop.buss.biz.service.BusinessDiaryService;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.common.entity.BusinessDiary;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.ReadResult;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.RelCustomerCategory;
import com.rhtop.buss.common.utils.FileUtil;
import com.rhtop.buss.common.utils.Jwt;
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
public class WriteController {
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
	
	/**
	 * 客户经理第一次录入客户信息、联系人信息、品类信息的接口
	 * @param userId 客户经理UUID
	 * @param customer 客户信息对象
	 * @param contacts 联系人对象list
	 * @param categorys 品类对象list
	 * @return htmlMsg的Json 包含响应状态码code和响应信息message
	 * @author MakeItHappen
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0001")
	public ReadResult<String> addCustomerAndCategory(HttpServletRequest request, @Valid @RequestParam(value="userId") String userId, @Valid @RequestBody Customer customer){
		ReadResult<String> readResult = new ReadResult<String>();
		String token = request.getHeader("token");
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
					contact.setCreateUser(userId);
					contact.setCreateTime(now);
					contact.setContactsInfoId(UUID.randomUUID().toString().replace("-", ""));
					contact.setCustomerId(customerId);
					contactsSer.insertContactsInfo(contact);
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
			//添加一条操作记录
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("/writeData/In0001");
			bd.setOprContent(userId+customer);
			busDiaSer.insertBusinessDiary(bd);
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
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0002")
	public ReadResult<String> uploadPic(HttpServletRequest request, @Valid @RequestParam("picFile") MultipartFile picFile,
			@Valid @RequestParam("mgrId") String mgrId){
		ReadResult<String> readResult = new ReadResult<String>();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		//检查校验是否通过
		if ("200".equals(result.get("code").toString())) {
			String catePic = FileUtil.uploadOneFile(picFile);
			readResult.setResObject(catePic);
			//添加一条操作记录
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(mgrId);
			bd.setOprType("/sriteData/In0002");
			bd.setOprContent(catePic);
			busDiaSer.insertBusinessDiary(bd);
		}
		return readResult;
	}

	
	
	/**
	 * 客户经理的信息采集菜单中采集信息的接口
	 * @param catePris 一个RelCategoryPrice对象集合。
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0003")
	public ReadResult<String> fixWholesaleAndAcptPrice(HttpServletRequest request, @Valid @RequestParam(value="userId") String userId, @Valid @RequestBody RelCategoryPrice catePri){
		ReadResult<String> readResult = new ReadResult<String>();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		//检查校验是否通过
		if ("200".equals(result.get("code").toString())) {
			try {
				catePri.setMgrId(userId);
				catPriSer.createOrUpdateWholesaleAndAcptPriceByCategoryId(catePri);
			} catch (Exception e) {
				e.printStackTrace();
				readResult.setCode("500");
				readResult.setMessage("更新失败！");
			}
			//新增一条操作记录
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("/sriteData/In0003");
			bd.setOprContent(catePri.toString());
			busDiaSer.insertBusinessDiary(bd);
		}
		return readResult;
	}

	
	// TODO: 这个接口需要添加对批量审核的支持
	/**
	 * 分部经理完善现货价、半期货价、期货价的接口
	 * @param userId 用户ID，也就是调用该接口的分部经理的ID
	 * @param catePri 品类价格对象，包含品类ID，现货、半期货、期货价的最大最小值。
	 * @return 状态码和状态消息
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0004")
	public ReadResult<String> fixMidPrice(HttpServletRequest request, @Valid @RequestParam(value="userId") String userId, @Valid @RequestBody RelCategoryPrice catePri){
		ReadResult<String> readResult = new ReadResult<String>();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		//检查校验是否通过
		if ("200".equals(result.get("code").toString())) {
			try {
				catePri.setRegMgrId(userId);
				catPriSer.createOrUpdateMidPriceByCategoryId(catePri);
			} catch (Exception e) {
				e.printStackTrace();
				readResult.setCode("500");
				readResult.setMessage("更新失败！");
			}
			//新增一条操作记录
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("/sriteData/In0004");
			bd.setOprContent(catePri.toString());
			busDiaSer.insertBusinessDiary(bd);
		}
		return readResult;
	}
	
	/**
	 * 分部经理确认客户录入的接口
	 * @param userId 操作者UUID
	 * @param cus 需要被确认的客户对象
	 * @return 更新状态
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0005")
	public ReadResult<String> commitNewCustomerLevelOne(HttpServletRequest request, @Valid @RequestParam(value="userId") String userId, @Valid @RequestBody List<Customer> cuss){
		ReadResult<String> readResult = new ReadResult<String>();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		//检查校验是否通过
		if ("200".equals(result.get("code").toString())) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			for(Customer cus : cuss){
				cus.setUpdateTime(now);
				cus.setUpdateUser(userId);
				try {
					int status = cusSer.checkCustomer(1, userId, cus);
					if(status==0){
						readResult.setResObject("确认完成！");
					}else if(status == 1){
						readResult.setResObject("后台异常！");
					}else if(status == 2){
						readResult.setResObject("审核流程错误！");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//新增一条操作记录
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("/sriteData/In0005");
			bd.setOprContent(cuss.toString());
			busDiaSer.insertBusinessDiary(bd);
		}
		return readResult;
	}
	//TODO: 总经理确认客户录入的接口
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0006")
	public ReadResult<String> commitNewCustomerLevelTwo(HttpServletRequest request, @Valid @RequestParam(value="userId") String userId, @Valid @RequestBody List<Customer> cuss){
		ReadResult<String> readResult = new ReadResult<String>();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		//检查校验是否通过
		if ("200".equals(result.get("code").toString())) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			for(Customer cus : cuss){
				cus.setUpdateTime(now);
				cus.setUpdateUser(userId);
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
					e.printStackTrace();
				}
			}
			//新增一条操作记录
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("/sriteData/In0006");
			bd.setOprContent(cuss.toString());
			busDiaSer.insertBusinessDiary(bd);
		}
		return readResult;
	}
	//TODO: 国际部完善品类报盘信息
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0007")
	public ReadResult<String> fixOfferPrice(HttpServletRequest request, @Valid @RequestParam(value="userId") String userId, @Valid @RequestBody RelCategoryPrice catPri){
		ReadResult<String> readResult = new ReadResult<String>();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		//检查校验是否通过
		if ("200".equals(result.get("code").toString())) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			catPri.setUpdateTime(now);
			catPri.setUpdateUser(userId);
			catPri.setUniMgrId(userId);
			catPri.setOfferUpdateTime(now);
			try {
				catPriSer.createOrUpdateOfferPriceAndTimeByCategoryId(catPri);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//记录日志
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("/sriteData/In0007");
			bd.setOprContent(catPri.toString());
			busDiaSer.insertBusinessDiary(bd);
		}
		return readResult;
	}
	//TODO: 国际部新增品类信息
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0008")
	public ReadResult<String> universeAddCategory(HttpServletRequest request, @Valid @RequestParam(value="userId") String userId,
			@Valid @RequestBody Category cat){
		ReadResult<String> readResult = new ReadResult<String>();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		//检查校验是否通过
		if ("200".equals(result.get("code").toString())) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
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
			//记录日志
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("/sriteData/In0008");
			bd.setOprContent(cat.toString());
			busDiaSer.insertBusinessDiary(bd);
		}
		return readResult;
	}
	//TODO:客户经理和分部经理编辑（更新）客户、品类、联系人信息的接口
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/In0009")
	public ReadResult<String> updateCustomerAndCategory(HttpServletRequest request, @Valid @RequestParam(value="userId") String userId, @Valid @RequestBody Customer customer){
		ReadResult<String> readResult = new ReadResult<String>();
		String token = request.getHeader("token");
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
			customer.setUpdateUser(userId);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
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
			//添加一条操作记录
			BusinessDiary bd = new BusinessDiary();
			bd.setBusinessDiaryId(UUID.randomUUID().toString().replace("-", ""));
			bd.setOprTime(now);
			bd.setOprUser(userId);
			bd.setOprType("/sriteData/In0009");
			bd.setOprContent(userId+customer);
			busDiaSer.insertBusinessDiary(bd);
		}
		return readResult;
	}
}
