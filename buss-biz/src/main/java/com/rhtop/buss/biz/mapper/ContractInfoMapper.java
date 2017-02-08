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
	
	/**
	 * 查找最新的一条合同记录
	 * @return
	 */
	ContractInfo selectLatestContract();
	
	/**
	 * 根据合同状态分页查询列表 cont_status_ > 10
	 * 行政 
	 * @author lujin
	 * @date 2017-2-4
	 * @param contractInfo
	 * @return
	 */
	List<ContractInfo> listPageContractInfoByXZStatus(ContractInfo contractInfo);
	
	/**
	 * 根据合同状态分页查询列表 cont_status_ > 20
	 * 财务
	 * @author lujin
	 * @date 2017-2-4
	 * @param contractInfo
	 * @return
	 */
	List<ContractInfo> listPageContractInfoByCWStatus(ContractInfo contractInfo);
	
}