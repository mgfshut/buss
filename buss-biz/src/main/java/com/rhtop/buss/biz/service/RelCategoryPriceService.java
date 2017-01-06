/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.RelCategoryPrice;

public interface RelCategoryPriceService{
    
	/**
	 * 新增
	 */
	int insertRelCategoryPrice(RelCategoryPrice relCategoryPrice);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteRelCategoryPrice(String relCategoryPriceId);
	
	/**
	 * 根据关系记录ID查找并修改记录
	 */
	int updateRelCategoryPrice(RelCategoryPrice relCategoryPrice);
	
	/**
	 * 根据关系记录Id查找数据
	 */
	RelCategoryPrice selectByPrimaryKey(String relCategoryPriceId);

	/**
	 * 创建或更新品类批发价和接盘价
	 */
	int createOrUpdateByCategoryId(RelCategoryPrice relCategoryPrice);
	
	/**
	 * 根据条件查询列表
	 */
	List<RelCategoryPrice> listRelCategoryPrices(RelCategoryPrice relCategoryPrice);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<RelCategoryPrice> listPageRelCategoryPrice(RelCategoryPrice relCategoryPrice);
}