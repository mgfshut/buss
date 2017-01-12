package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.CusckLog;

public interface CusckLogService {
	/**
	 * 新增
	 */
	int insertBusinessDiary(CusckLog cusckLog);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteBusinessDiary(String cusckLogId);
	
	/**
	 * 根据Id查找数据
	 */
	CusckLog selectByPrimaryKey(String cusckLogId);
	
	/**
	 * 根据条件查询列表
	 */
	List<CusckLog> listBusinessDiarys(CusckLog cusckLog);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<CusckLog> listPageBusinessDiary(CusckLog cusckLog);
}
