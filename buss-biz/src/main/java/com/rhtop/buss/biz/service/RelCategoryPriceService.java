/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.ResultInfo;

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
	ResultInfo createOrUpdateWholesaleAndAcptPriceByCategoryId(ResultInfo readResult, List<RelCategoryPrice> relCategoryPrices, String categoryId);
	
	/**
	 * 创建或更新品类三个中间价
	 */
	ResultInfo createOrUpdateMidPriceByCategoryId(ResultInfo readresult, List<RelCategoryPrice> relCategoryPrices);
	
	/**
	 * 创建或更新报盘价和报盘时效
	 */
	ResultInfo createOrUpdateOfferPriceAndTimeByCategoryId(ResultInfo readResult, RelCategoryPrice relCategoryPrice);
	
	/**
	 * 根据条件查询列表
	 */
	List<RelCategoryPrice> listRelCategoryPrices(RelCategoryPrice relCategoryPrice);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<RelCategoryPrice> listPageRelCategoryPrice(RelCategoryPrice relCategoryPrice);
	/**
	 * 定时任务 删除价格等信息；保留主键，品类id,创建时间，创建人四个字段
	 * @param category
	 * @return
	 */
	int updateSelective(RelCategoryPrice relCategoryPrice);
}
