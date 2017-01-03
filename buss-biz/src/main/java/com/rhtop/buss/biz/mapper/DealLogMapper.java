/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.DealLog;

public interface DealLogMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(DealLog dealLog);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String dealLogId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(DealLog dealLog);
    /**
	 * 根据主键查询对象
	 */
    DealLog selectByPrimaryKey(String dealLogId);
    /**
     * 根据条件查询列表
     */
	List<DealLog> listDealLogs(DealLog dealLog);
    /**
     * 根据条件分页查询列表
     */
	List<DealLog> listPageDealLog(DealLog dealLog);
    
}