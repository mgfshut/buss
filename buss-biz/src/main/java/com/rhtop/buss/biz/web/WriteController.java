package com.rhtop.buss.biz.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.rhtop.buss.biz.service.BusinessDiaryService;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.common.entity.BusinessDiary;
import com.rhtop.buss.common.entity.CategoryVo;
import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.RelCustomerCategory;
import com.rhtop.buss.common.web.HtmlMessage;
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
@CrossOrigin(allowedHeaders = "*")
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
	@RequestMapping(value="/In0001")
	public String addCustomerAndCategory(@Valid @RequestParam(value="userId") String userId, @Valid @RequestBody Customer customer, @Valid @RequestBody List<ContactsInfo> contacts, @Valid @RequestBody List<CategoryVo> categorys){
		//新建Gson对象
		Gson gson = new Gson();
		HtmlMessage htmlMsg = null;
		//检查传进来的客户对象是否为空，为空则返回错误信息码并结束操作。
		if(customer==null){
			htmlMsg = new HtmlMessage("操作失败，用户信息为必填属性。");
			htmlMsg.setStatusCode("500");
			String json = gson.toJson(htmlMsg);
			return json;
		}
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
			htmlMsg = new HtmlMessage("操作失败，新增客户出错。");
			htmlMsg.setStatusCode("500");
			String json = gson.toJson(htmlMsg);
			return json;
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
			for(CategoryVo cat : categorys){
				//检查要新增的品类是否已存在于数据库中
				if(catSer.checkCategoryExist(cat)==null){
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
		bd.setOprType("/sriteData/In0001");
		bd.setOprContent(userId+customer+contacts+categorys);
		busDiaSer.insertBusinessDiary(bd);
		
		//构造返回消息的实体
		htmlMsg = new HtmlMessage();
		//返回消息转换成Json字符串
        String json = gson.toJson(htmlMsg);
        return json;
	}
}
