package com.rhtop.buss.biz.mapper;

import java.util.List;

import com.rhtop.buss.common.entity.CusckLog;

public interface CusckLogMapper {
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(CusckLog cusckLog);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String cusckLogId);
    /**
	 * 根据主键查询对象
	 */
    CusckLog selectByPrimaryKey(String cusckLogId);
    /**
     * 根据条件查询列表
     */
	List<CusckLog> listCusckLogs(CusckLog cusckLog);
    /**
     * 根据条件分页查询列表
     */
	List<CusckLog> listPageCusckLog(CusckLog cusckLog);
	/**
	 * 根据客户id查询对该客户的操作记录
	 * @author lujin
	 * @date 2017-1-21
	 * @param cusckLog
	 * @return
	 */
	List<CusckLog> listPageCusckCustomer(CusckLog cusckLog);
}
