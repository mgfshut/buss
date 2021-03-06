/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.Customer;

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
	
	
	List<Category> listPageCategoryByIntf(Category category);
	
	/**
	 * 根据客户id查询所属的类品
	 * @param customerId
	 * @return
	 */
	List<Category> listCategoryByCustomer(String customerId);

	/**
	 * 查询已采集的和未采集的的品类信息
	 * @param category
	 * @return
	 */
	List<Category> listPageCategoeyByPrice(Category category);
	
	/**
	 * 品类与价格的详情
	 * @param categoryId
	 * @return
	 */
	Category selectInfoByPrimaryKey(String categoryId);
	/**
	 * 批量插入品类
	 * @author mgf
	 * @date 2017年1月14日 下午5:39:51 
	 * @param categorys
	 */
	int insertExcelCategory(List<Category> categorys) throws Exception;
	/**
	 * 根据客户id查询所属的报价不为空的类品
	 * @param customerId
	 * @return
	 */
	List<Category> listPageByCustomerAndPrice(String customerId);
	
	/**
	 * 通过品类的id
	 * 品类的报价详情(还包括供应商，货币单位，计量单位，采购价格，报盘时效，报盘价)
	 * @author lujin
	 * @date 2017-1-23
	 * @param category
	 * @return
	 */
	Category custOfferPiceInfo(Category category);
	
	/**
	 * 得到品类与品类的价格详情
	 * @author lujin
	 * @date 2017-2-9
	 * @param category
	 * @return
	 */
	Category listPageReCatePrice(Category category);
}
