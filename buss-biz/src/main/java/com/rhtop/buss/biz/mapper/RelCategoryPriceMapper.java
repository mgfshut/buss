/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
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
    RelCategoryPrice selectByCategoryId(String categoryId);
    /**
     * 根据条件查询列表
     */
	List<RelCategoryPrice> listRelCategoryPrices(RelCategoryPrice relCategoryPrice);
    /**
     * 根据条件分页查询列表
     */
	List<RelCategoryPrice> listPageRelCategoryPrice(RelCategoryPrice relCategoryPrice);
    
}