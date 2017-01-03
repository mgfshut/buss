/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.BusinessDiary;

public interface BusinessDiaryMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(BusinessDiary businessDiary);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String businessDiaryId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(BusinessDiary businessDiary);
    /**
	 * 根据主键查询对象
	 */
    BusinessDiary selectByPrimaryKey(String businessDiaryId);
    /**
     * 根据条件查询列表
     */
	List<BusinessDiary> listBusinessDiarys(BusinessDiary businessDiary);
    /**
     * 根据条件分页查询列表
     */
	List<BusinessDiary> listPageBusinessDiary(BusinessDiary businessDiary);
    
}