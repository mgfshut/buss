/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.RelCustomerCategory;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.biz.mapper.CategoryMapper;
import com.rhtop.buss.biz.mapper.ContactsInfoMapper;
import com.rhtop.buss.biz.mapper.CustomerMapper;
import com.rhtop.buss.biz.mapper.MemberMapper;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private ContactsInfoMapper contactsInfoMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ContactsInfoService contactsSer;
	@Autowired
	private RelCustomerCategoryService cusCatSer;
	@Autowired
	private CategoryService catSer;
	
	@Override
	public int insertCustomer(Customer customer) {
		return customerMapper.insertSelective(customer);
	}
	
	@Override
	public ResultInfo addCustomer(ResultInfo readResult, Customer customer) {
		String userId = customer.getUpdateUser();
		String now = customer.getUpdateTime();
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
		customer.setCkStatus("00");
		//向数据库中添加一条客户数据。
		try {
			//添加客户数据
			insertCustomer(customer);
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
					relCustomerCategory.setCustomerId(customer.getCustomerId());
					relCustomerCategory.setCusChaId(customer.getCusCha());
					//渠道名称
					cusCatSer.insertRelCustomerCategory(relCustomerCategory);
				}else{
					//如果品类已经存在，检查关系表中客户经理、客户、品类是否已存在。防止客户经理重复提交。
					RelCustomerCategory relCustomerCategory = new RelCustomerCategory();
					relCustomerCategory.setCreateUser(cat.getCreateUser());
					relCustomerCategory.setCategoryId(cat.getCategoryId());
					relCustomerCategory.setCustomerId(customerId);
					RelCustomerCategory relCustomerCategoryRes  = cusCatSer.selectByPrimaryParam(relCustomerCategory);
					if(relCustomerCategoryRes != null){
						continue;
					}else{
					//写关联关系
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
						relCustomerCategory.setCustomerId(customer.getCustomerId());
						relCustomerCategory.setCusChaId(customer.getCusCha());
						cusCatSer.insertRelCustomerCategory(relCustomerCategory);
					}
					
				}
			}
		}
		return readResult;
	}

	@Override
	public int deleteCustomer(String customerId) {
		return customerMapper.deleteByPrimaryKey(customerId);
	}

	@Override
	public int updateCustomer(Customer customer) {
		return customerMapper.updateByPrimaryKeySelective(customer);
	}
	
	@Override
	public Customer selectByPrimaryKey(String customerId){
		return customerMapper.selectByPrimaryKey(customerId);
	}

	@Override
	public List<Customer> listCustomers(Customer customer) {
		List<Customer> customerList = customerMapper.listCustomers(customer);
		return customerList;
	}
	
	@Override
	public List<Customer> listPageCustomer(Customer customer) {
		//对用户的职务进行判断
		String memberJob = memberMapper.selectByPrimaryKey(customer.getCreateUser()).getMemberJob();
		List<Customer> customers = null;
		if("01".equals(memberJob)){//客户经理
			  customers = customerMapper.listPageCustomerByMgr(customer);;
		}else if("02".equals(memberJob)){//分部经理
			  customers = customerMapper.listPageCustomerByRegMgr(customer);
		}else if("05".equals(memberJob)){//总经理
			  customers = customerMapper.listPageCustomerByGenMgr(customer);
		}
		return customers;
	}

	@Override
	public int checkCustomer(int checkLevel, String userId, Customer cus) {
		try {
			//获取当前的该用户审核状态
			String ckStatus = customerMapper.selectByPrimaryKey(cus.getCustomerId()).getCkStatus();
			//根据审核层级进行相对应的操作
			if(checkLevel==1){
				//如果当前审核层级是分部经理审核，检查ckStatus是否为00
				if(ckStatus=="00"||ckStatus.equals("00")||Integer.parseInt(ckStatus)==00){
					cus.setCkStatus("01");
					customerMapper.updateByPrimaryKeySelective(cus);
					return 0;
				}else{
					return 2;
				}
			}else if (checkLevel==2){
				//如果当前审核层级是总经理审核，检查ckStatus字段是否为01
				if(ckStatus=="01"||ckStatus.equals("01")||Integer.parseInt(ckStatus)==01){
					cus.setCkStatus("02");
					customerMapper.updateByPrimaryKeySelective(cus);
					return 0;
				}else{
					return 2;
				}
			}
			return 2;
		} catch (Exception e) {
			e.printStackTrace();
			//上面的代码块出现任何异常，返回1表示后台异常。
			return 1;
		}
	}

	@Override
	public Customer selectCustomerInfo(Customer customer) {
		Customer cus = new Customer();
		String creater = memberMapper.selectByPrimaryKey( customer.getCreateUser()).getMemberJob();
		// 判断是否是客户经理或者总经理
		if ("05".equals(creater) || "01".equals(creater)) {
			// 查询客户信息
			cus = customerMapper.selectByPrimaryKey(customer.getCustomerId());
			customer.setCustomerId(customer.getCustomerId());
			// 查询联系人
			ContactsInfo contactsinfo = new ContactsInfo();
			contactsinfo.setCustomerId(customer.getCustomerId());
			List<ContactsInfo> conts = contactsInfoMapper.listContactsInfos(contactsinfo);
			// 查询品类
			List<Category> cates = categoryMapper.listCategoryByCustomer(customer.getCustomerId());
			// 添加联系人和品类
			cus.setCategorys(cates);
			cus.setContacts(conts);
		} else if("02".equals(creater)){
			String createUser = customerMapper.selectByPrimaryKey( customer.getCustomerId()).getCreateUser();
			// 判断创建是否是用户自己
			if (createUser.equals(customer.getCreateUser())) {// 是，
				// 查询客户信息
				cus = customerMapper.selectByPrimaryKey(customer
						.getCustomerId());
				customer.setCustomerId(customer.getCustomerId());
				// 查询联系人
				ContactsInfo contactsinfo = new ContactsInfo();
				contactsinfo.setCustomerId(customer.getCustomerId());
				List<ContactsInfo> conts = contactsInfoMapper
						.listContactsInfos(contactsinfo);
				// 查询品类
				List<Category> cates = categoryMapper
						.listCategoryByCustomer(customer.getCustomerId());
				// 添加联系人和品类
				cus.setCategorys(cates);
				cus.setContacts(conts);
			} else {// 否，部门经理下面的客户经理的客户详情
				cus = customerMapper.selectByCreater(customer.getCreateUser());
			}
		}
		return cus;
	}
}