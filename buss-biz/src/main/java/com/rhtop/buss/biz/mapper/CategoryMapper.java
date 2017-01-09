/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;

import com.rhtop.buss.common.entity.Category;

public interface CategoryMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(Category category);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String categoryId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(Category category);
    /**
	 * 根据主键查询对象
	 */
    Category selectByPrimaryKey(String categoryId);
    /**
     * 根据品类名称、规格comm、厂号、产地查询品类
     */
    Category selectByPrimaryParam(Category category);
    /**
     * 根据条件查询列表
     */
	List<Category> listCategorys(Category category);
    /**
     * 根据条件分页查询列表
     */
	List<Category> listPageCategory(Category category);
    
	/**
	 * 根据客户id查询所属的类品
	 * @param customerId
	 * @return
	 */
	List<Category> listCategoryByCustomer(String customerId);
	
	/**
	 * 根据客户经理的id查询客户经理已采集的价格
	 * @param customerId
	 * @return
	 */
	List<Category> listPrice(String memberId);
	
	/**
	 * 查询所有未采集(接盘价和批发价)的品类
	 * @param customerId
	 * @return
	 */
	List<Category> listNotPrice(String memberId);
}
