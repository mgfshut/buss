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

}