/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.RelCustomerCategory;

public interface RelCustomerCategoryService{
    
	/**
	 * 新增
	 */
	int insertRelCustomerCategory(RelCustomerCategory relCustomerCategory);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteRelCustomerCategory(String relCustomerCategoryId);
	
	/**
	 * 修改
	 */
	int updateRelCustomerCategory(RelCustomerCategory relCustomerCategory);
	
	/**
	 * 根据Id查找数据
	 */
	RelCustomerCategory selectByPrimaryKey(String relCustomerCategoryId);
	
	/**
	 * 根据条件查询列表
	 */
	List<RelCustomerCategory> listRelCustomerCategorys(RelCustomerCategory relCustomerCategory);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<RelCustomerCategory> listPageRelCustomerCategory(RelCustomerCategory relCustomerCategory);
}