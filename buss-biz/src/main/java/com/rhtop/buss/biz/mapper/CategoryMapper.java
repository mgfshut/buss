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
     * 根据品类名称、规格、厂号、产地、包装数量查询品类
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
	
	List<Category> listPageCategoryByIntf(Category category);
    
	/**
	 * 根据客户id查询所属的类品
	 * @param customerId
	 * @return
	 */
	List<Category> listCategoryByCustomer(String customerId);
	
	/**
	 * 查询客户经理采集报价的价格
	 * 根据客户经理的id
	 * @param memberId
	 * @return
	 */
	List<Category> listPriceByMgr(Category memberId);
	
	/**
	 * 分部经理查询报价信息
	 * 根据分部经理的id
	 * @param memberId
	 * @return
	 */
	List<Category> listPriceByRegMgr(Category memberId);
	/**
	 * 国际采购人员查询未报盘情况
	 * @param memberId
	 * @return
	 */
	List<Category> listNotPriceByUniMgr(String memberId);
	
	/**
	 * 国际采购人员查询已报盘情况
	 * @param memberId
	 * @return
	 */
	List<Category> listPriceByUniMgr(String memberId);
	
	/**
	 * 品类详情 
	 * 根据品类的id查询
	 * @param customerId
	 * @return
	 */
	Category selectInfoByPrimaryKey(String categoryId);
	
	/**
	 * 根据客户id查询所属的报价不为空的类品
	 * @param customerId
	 * @return
	 */
	List<Category> listPageByCustomerAndPrice(Category category);
	
	/**
	 * 更新价格
	 * @author lujin
	 */
	int updateCategory(Category category);
	
	/**
	 * 国际部查询品类的报盘情况
	 * @return
	 */
	List<Category> listPagePriceByUniMgr(Category category);
	
	/**
	 * 通过品类的id
	 * 品类的报价详情(还包括供应商，货币单位，计量单位，采购价格，报盘时效，报盘价)
	 * @author lujin
	 * @date 2017-1-23
	 * @param category
	 * @return
	 */
	Category custOfferPiceInfo(Category category);
}
