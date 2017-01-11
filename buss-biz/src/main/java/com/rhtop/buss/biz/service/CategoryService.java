/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.Category;

public interface CategoryService{
    
	/**
	 * 新增
	 */
	int insertCategory(Category category);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteCategory(String categoryId);
	
	/**
	 * 修改
	 */
	int updateCategory(Category category);
	
	/**
	 * 根据Id查找数据
	 */
	Category selectByPrimaryKey(String categoryId);
	
	/**
	 * 根据品类名、产地、厂号、规格、包装数量查看品类是否存在
	 */
	Category checkCategoryExist(Category category);
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
	 * 根据客户经理的id 查询已采集的和未采集的的品类信息
	 * 规则：memberId 为 空，表示查询（批发价和接盘价）未报价的品类信息
	 *     memberId 不为空，表示查询客户经理已报价的品类信息
	 * @param memberId
	 * @return
	 */
	List<Category> listPageCategoeyByPrice(String memberId);
}