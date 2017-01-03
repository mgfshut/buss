/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.BusinessDiary;

public interface BusinessDiaryService{
    
	/**
	 * 新增
	 */
	int insertBusinessDiary(BusinessDiary businessDiary);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteBusinessDiary(String businessDiaryId);
	
	/**
	 * 修改
	 */
	int updateBusinessDiary(BusinessDiary businessDiary);
	
	/**
	 * 根据Id查找数据
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