/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.Customer;

public interface CustomerService{
    
	/**
	 * 新增
	 */
	int insertCustomer(Customer customer);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteCustomer(String customerId);
	
	/**
	 * 修改
	 */
	int updateCustomer(Customer customer);
	
	/**
	 * 根据Id查找数据
	 */
	Customer selectByPrimaryKey(String customerId);
	
	/**
	 * 根据条件查询列表
	 */
	List<Customer> listCustomers(Customer customer);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<Customer> listPageCustomer(Customer customer);
}