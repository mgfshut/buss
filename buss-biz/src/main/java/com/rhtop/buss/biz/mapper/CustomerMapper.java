/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.Customer;

public interface CustomerMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(Customer customer);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String customerId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(Customer customer);
    /**
	 * 根据主键查询对象
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