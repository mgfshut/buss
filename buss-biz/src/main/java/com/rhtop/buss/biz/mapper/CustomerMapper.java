/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;

import com.rhtop.buss.common.entity.Category;
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
	 * 查看自己的创建的客户信息，根据主键查询对象
	 */
    Customer selectByPrimaryKey(String customerId);
    /**
     * 根据主键查询对象，部门经理查看自己的客户经理的客户信息
     * @param creat
     * @return
     */
    Customer selectByCreater(String createUser);
    /**
     * 根据条件查询列表
     */
	List<Customer> listCustomers(Customer customer);
    /**
     * 根据条件分页查询列表
     */
	List<Customer> listPageCustomer(Customer customer);
	
	/**
	 * 根据条件分页查询列表
	 * 
	 * 客户经理 :客户信息查询列表
	 * 根据传入的用户id，查询客户经理创建的客户。
	 * 
	 * @param customer
	 * @return
	 */
	List<Customer> listPageCustomerByMgr(Customer customer);
	/**
	 * 根据条件分页查询列表
	 * 
	 * 部门经理：客户信息查询列表
	 * 根据传入的用户id，判断用户的职称，继而查询该用户是否存在客户经理，查询自己创建的客户以及客户经理创建的客户。
	 * 
	 * @param customer
	 * @return
	 */
	List<Customer> listPageCustomerByRegMgr(Customer customer);
	/**
	 * 根据条件分页查询列表
	 * 
	 * 总经理  ：客户信息查询列表
	 * 
	 * @param customer
	 * @return
	 */
	List<Customer> listPageCustomerByGenMgr(Customer customer);
	
	Customer selectInfoByPrimaryKey(String customerId);
	
	/**
	 * 通过品类id和创建者id，查找该创建者的客户信息
	 * 
	 * @author lujin
	 * @date 2017-1-23
	 * @param category
	 * @return
	 */
	List<Customer> selectCustByCateId(String createUser, String customerId);
	
	/**
	 * 通过创建者id得到该创建的客户信息
	 * @author lujin
	 * @Date 2017-1-24
	 * @param customer
	 * @return
	 */
	List<Customer> listPageCustByCreateUser(Customer customer);
	
}