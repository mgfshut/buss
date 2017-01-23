package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.CusckLog;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.RelCategoryPrice;

public interface CusckLogService {
	/**
	 * 新增
	 */
	int insertCusckLog(CusckLog cusckLog);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteCusckLog(String cusckLogId);
	
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
	
	/**
	 * 日志报表（客户信息）
	 * @param cusckLog
	 * @author lujin
	 * @date 2017-1-21
	 */
	List<Customer> cusCkLogCustomer(Customer customer);
	
	/**
	 * 日志报表（品类信息）
	 * @param cusckLog
	 * @author lujin
	 * @date 2017-1-21
	 */
	List<RelCategoryPrice> cusCkLogRelCategoryPrice(RelCategoryPrice relCategoryPrice);
}
