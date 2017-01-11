/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.biz.mapper.CategoryMapper;
import com.rhtop.buss.biz.mapper.ContactsInfoMapper;
import com.rhtop.buss.biz.mapper.CustomerMapper;
import com.rhtop.buss.biz.mapper.MemberMapper;
import com.rhtop.buss.biz.service.CustomerService;

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
	
	@Override
	public int insertCustomer(Customer customer) {
		return customerMapper.insertSelective(customer);
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