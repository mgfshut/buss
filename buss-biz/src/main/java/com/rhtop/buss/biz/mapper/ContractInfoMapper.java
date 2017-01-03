/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.ContractInfo;

public interface ContractInfoMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(ContractInfo contractInfo);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String contractInfoId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(ContractInfo contractInfo);
    /**
	 * 根据主键查询对象
	 */
    ContractInfo selectByPrimaryKey(String contractInfoId);
    /**
     * 根据条件查询列表
     */
	List<ContractInfo> listContractInfos(ContractInfo contractInfo);
    /**
     * 根据条件分页查询列表
     */
	List<ContractInfo> listPageContractInfo(ContractInfo contractInfo);
    
}