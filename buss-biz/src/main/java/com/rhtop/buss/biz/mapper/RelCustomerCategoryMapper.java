/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.RelCustomerCategory;

public interface RelCustomerCategoryMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(RelCustomerCategory relCustomerCategory);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String relCustomerCategoryId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(RelCustomerCategory relCustomerCategory);
    /**
	 * 根据主键查询对象
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