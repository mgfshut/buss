/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.text.ParseException;
import java.util.List;

import com.rhtop.buss.common.entity.ContractInfo;

public interface ContractInfoService{
    
	/**
	 * 新增
	 */
	int insertContractInfo(ContractInfo contractInfo);
    
	/**
	 * 创建合同、修改交易记录的状态
	 * @throws ParseException 
	 */
	String createContract(ContractInfo con) throws Exception;
	
	/**
	 * 总经理审核合同（修改状态 从10修改为20）
	 */
	String checkContract(ContractInfo con) throws Exception;
	
	/**
	 * 行政人员审核合同（修改状态 从20修改为30）
	 */
	String contractStamp(ContractInfo con) throws Exception;
	
	/**
	 * 合同下载：返回合同URL
	 */
	List<String> downloadContract(ContractInfo con);
	
	/**
	 * 财务审核合同（修改状态 从30修改为40）
	 */
	String treasurerCheckContract(ContractInfo con) throws Exception;
	
	/**
	 * 根据ID删除数据
	 */
	int deleteContractInfo(String contractInfoId);
	
	/**
	 * 修改
	 */
	int updateContractInfo(ContractInfo contractInfo);
	
	/**
	 * 根据Id查找数据
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
	 * 根据合同ID获取合同打印相关信息
	 * @author mgf
	 * @date 2017年1月15日 下午1:31:43 
	 * @param contractInfoId
	 * @return
	 */
	ContractInfo printByContractInfoId(String contractInfoId);
	
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
	
	/**
	 * 总经理驳回客户经理提交的合同
	 * （交易表 修改状态 从30修改为31，合同修改状态 从10修改为11 ）
	 * @author lujin
	 * @date 2017-2-8
	 */
	String dismissContract(ContractInfo con) throws Exception;
	
	/**
	 * 行政驳回总经理已审核的合同
	 * （合同修改状态 从20修改为21 ）
	 * @author lujin
	 * @date 2017-2-8
	 */
	String dismissContractByXZ(ContractInfo con) throws Exception;
	
	
}