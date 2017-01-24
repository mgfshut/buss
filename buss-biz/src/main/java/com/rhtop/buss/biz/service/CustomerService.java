/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.ResultInfo;

public interface CustomerService{
    
	/**
	 * 新增
	 */
	int insertCustomer(Customer customer);
	
	/**
	 * 新增客户信息、品类信息、联系人信息
	 */
	ResultInfo addCustomer (ResultInfo readResult, Customer customer) throws Exception;
    
	/**
	 * 根据ID删除数据
	 */
	int deleteCustomer(String customerId);
	
	/**
	 * 修改
	 */
	int updateCustomer(Customer customer);
	
	/**
	 * 经理修改客户信息
	 */
	ResultInfo updateCustomerInfo(ResultInfo readResult, Customer customer)  throws Exception;
	
	/**
	 * 根据Id查找数据
	 */
	Customer selectByPrimaryKey(String customerId);
	
	/**
	 * 修改客户审核状态
	 * checkLevel为审核层级，1代表这是分部经理审核，2代表总经理审核。
	 * 返回值为本次审核状态，0为审核完成，1为后台异常，2为审核流程错误。
	 */
	int checkCustomer(int checkLevel, String userId, Customer cus);
	/**
	 * 根据条件查询列表
	 */
	List<Customer> listCustomers(Customer customer);
	
    /**
	 * 根据条件分页查询列表
	 * 
	 * 客户信息查询列表
	 * 客户经理 ：客户信息查询
	 * 部门经理： 客户信息查询
	 * 总经理    ： 客户信心查询
	 * 根据传入的用户id，判断用户的职称，继而查询该用户是否存在客户经理。
	 * 
	 * @param customer
	 * @return
	 */
	List<Customer> listPageCustomer(Customer customer);
	
	/**
	 * 客户信息详情
	 * @param custome
	 * @return
	 */
	Customer selectCustomerInfo(Customer customer);
	
	/**
	 * 通过品类id和创建者id，查找该创建者的客户信息
	 * 
	 * @author lujin
	 * @date 2017-1-32
	 * @param category
	 * @return
	 */
	List<Customer> selectCustByCateId(Category category);
	
	/**
	 * 通过创建者的id得到自己所创建的客户信息
	 * @author lujin
	 * @Date 2017-1-24
	 * @param category
	 * @return
	 */
	List<Customer> listPageCustByCreateUser(Customer customer);
}