/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.Category;
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
	 * 根据品类ID查找数据
	 */
	RelCategoryPrice selectByCategoryId(String categoryId);

	/**
	 * 创建或更新品类批发价和接盘价
	 */
	ResultInfo createOrUpdateWholesaleAndAcptPriceByCategoryId(ResultInfo readResult, List<RelCategoryPrice> relCategoryPrices, String categoryId, String userId) throws Exception;
	
	/**
	 * 创建或更新品类三个中间价
	 */
	ResultInfo createOrUpdateMidPriceByCategoryId(ResultInfo readresult, List<RelCategoryPrice> relCategoryPrices)throws Exception;
	
	/**
	 * 创建或更新报盘价和报盘时效
	 * @throws Exception 
	 */
	ResultInfo createOrUpdateOfferPriceAndTimeByCategoryId(ResultInfo readResult, RelCategoryPrice relCategoryPrice) throws Exception;
	
	/**
	 * 国际部新建品类、填写供应商、两个单位、报盘价和报盘时效
	 */
	ResultInfo universeAddCategoryAndPrice(ResultInfo readResult, Category category) throws Exception;
	
	
	/**
	 * 根据条件查询列表
	 */
	List<RelCategoryPrice> listRelCategoryPrices(RelCategoryPrice relCategoryPrice, String memberId);
	
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
