/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.SlaTransactionInfo;

public interface SlaTransactionInfoMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(SlaTransactionInfo slaTransactionInfo);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String slaTransactionInfoId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(SlaTransactionInfo slaTransactionInfo);
    /**
	 * 根据主键查询对象
	 */
    SlaTransactionInfo selectByPrimaryKey(String slaTransactionInfoId);
    /**
     * 根据条件查询列表
     */
	List<SlaTransactionInfo> listSlaTransactionInfos(SlaTransactionInfo slaTransactionInfo);
    /**
     * 根据条件分页查询列表
     */
	List<SlaTransactionInfo> listPageSlaTransactionInfo(SlaTransactionInfo slaTransactionInfo);
    
}