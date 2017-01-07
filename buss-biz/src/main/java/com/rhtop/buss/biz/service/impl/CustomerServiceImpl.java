/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.biz.mapper.CustomerMapper;
import com.rhtop.buss.biz.service.CustomerService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerMapper customerMapper;
	
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
		List<Customer> customers = customerMapper.listPageCustomer(customer);
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
	public List<Customer> selectCustomerInfo(Customer customer) {
		List<Customer> customers = customerMapper.selectCustomerByCreateUser(customer);
		return customers;
	}

}