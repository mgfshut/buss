/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.Category;
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
	 * 根据客户经理、客户、品类查找数据
	 */
	RelCustomerCategory selectByPrimaryParam(RelCustomerCategory relCustomerCategory);
	
	/**
	 * 根据条件查询列表
	 */
	List<RelCustomerCategory> listRelCustomerCategorys(RelCustomerCategory relCustomerCategory);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<RelCustomerCategory> listPageRelCustomerCategory(RelCustomerCategory relCustomerCategory);
	/**
	 * 品类导出数据查询
	 * @author mgf
	 * @date 2017年1月14日 下午3:34:00 
	 * @param category
	 * @return
	 */
	List<RelCustomerCategory> categoryExportList(Category category);
	
	/**
	 * 通过创建者得到渠道
	 * @author lujin
	 * @date 2017-1-17
	 * @param category
	 * @return
	 */
	List<RelCustomerCategory> selectCuscha (Category category);
	
}