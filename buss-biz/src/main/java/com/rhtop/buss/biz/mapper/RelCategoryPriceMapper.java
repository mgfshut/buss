/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.RelCategoryPrice;

public interface RelCategoryPriceMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(RelCategoryPrice relCategoryPrice);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String relCategoryPriceId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(RelCategoryPrice relCategoryPrice);
    /**
     * 根据品类ID修改对象
     */
    int updateByCategoryId(RelCategoryPrice relCategoryPrice);
    /**
	 * 根据主键查询对象
	 */
    RelCategoryPrice selectByPrimaryKey(String relCategoryPriceId);
    /**
     * 根据品类ID查询关系记录
     */
    RelCategoryPrice  selectByCategoryId(String categoryId);
    /**
     * 根据品类ID和渠道查询关系记录中最后更新的一条（按理说应该只有一条，查最后一条是为了容错）
     */
    List<RelCategoryPrice> selectByCategoryIdAndChaId(String categoryId,String chaId);
    /**
     * 根据条件查询列表
     */
	List<RelCategoryPrice> listRelCategoryPrices(RelCategoryPrice relCategoryPrice);
    /**
     * 根据条件分页查询列表
     */
	List<RelCategoryPrice> listPageRelCategoryPrice(RelCategoryPrice relCategoryPrice);
	
	/**
	 * 定时任务 删除价格，保留主键，品类id,创建时间，创建人四个字段
	 * @param category
	 * @return
	 */
	int updateSelective(RelCategoryPrice relCategoryPrice);
	/**
	 * 分部经理按照创建人ID，品类ID，渠道ID
	 * @param relCategoryPrice
	 * @return
	 */
	RelCategoryPrice checkRegMgrCategory(RelCategoryPrice relCategoryPrice);
	
	/**
	 * 决策委员会品类价格信息
	 * @author lujin
	 * @date 2017-1-21
	 * @param relCategoryPrice
	 * @return
	 */
	List<RelCategoryPrice> listPagePriceGroupBycusCha(RelCategoryPrice relCategoryPrice);
	
    
}