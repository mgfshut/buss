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
	 * 根据条件查询列表
	 */
	List<Category> listCategorys(Category category);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<Category> listPageCategory(Category category);
}