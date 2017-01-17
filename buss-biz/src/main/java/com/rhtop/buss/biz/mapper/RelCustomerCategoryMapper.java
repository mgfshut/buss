/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;

import com.rhtop.buss.common.entity.Category;
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
     * 根据客户、品类、客户经理（创建人）查询对象
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
	 * @date 2017年1月14日 下午3:34:35 
	 * @param category
	 * @return
	 */
	List<RelCustomerCategory> categoryExportList(Category category);
    /**
     * 客户经理查询 品类的渠道
     * @author lujin
     * @date 2017-1-17
     */
	List<RelCustomerCategory> selectCuschaByMgr(RelCustomerCategory relCustomerCategory);
	/**
     * 分部经理查询 品类的渠道
     * @author lujin
     * @date 2017-1-17
     */
	List<RelCustomerCategory> selectCuschaByRegMgr(RelCustomerCategory relCustomerCategory);
	
}