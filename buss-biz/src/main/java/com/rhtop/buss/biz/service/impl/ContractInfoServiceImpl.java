/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.SlaTransactionInfo;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.biz.mapper.ContractInfoMapper;
import com.rhtop.buss.biz.mapper.SlaTransactionInfoMapper;
import com.rhtop.buss.biz.mapper.TransactionInfoMapper;
import com.rhtop.buss.biz.service.ContractInfoService;

@Service("contractInfoService")
public class ContractInfoServiceImpl implements ContractInfoService {
	@Autowired
	private ContractInfoMapper contractInfoMapper;
	
	@Autowired
	private TransactionInfoMapper txMapper; 
	
	@Autowired
	private SlaTransactionInfoMapper slaTxMapper;
	
	@Override
	public int insertContractInfo(ContractInfo contractInfo) {
		return contractInfoMapper.insertSelective(contractInfo);
	}

	@Override
	public int deleteContractInfo(String contractInfoId) {
		return contractInfoMapper.deleteByPrimaryKey(contractInfoId);
	}

	@Override
	public int updateContractInfo(ContractInfo contractInfo) {
		return contractInfoMapper.updateByPrimaryKeySelective(contractInfo);
	}
	
	@Override
	public ContractInfo selectByPrimaryKey(String contractInfoId){
		return contractInfoMapper.selectByPrimaryKey(contractInfoId);
	}

	@Override
	public List<ContractInfo> listContractInfos(ContractInfo contractInfo) {
		List<ContractInfo> contractInfoList = contractInfoMapper.listContractInfos(contractInfo);
		return contractInfoList;
	}
	
	@Override
	public List<ContractInfo> listPageContractInfo(ContractInfo contractInfo) {
		List<ContractInfo> contractInfos = contractInfoMapper.listPageContractInfo(contractInfo);
		return contractInfos;
	}

	@Override
	public String createContract(ContractInfo con) {
		TransactionInfo tx = new TransactionInfo();
		tx.setTransactionInfoId(con.getTransactionInfoId());
		tx.setUpdateTime(con.getUpdateTime());
		tx.setUpdateUser(con.getUpdateUser());
		tx.setTxStatus("30");
		txMapper.updateByPrimaryKeySelective(tx);
		SlaTransactionInfo slaTx = slaTxMapper.selectLatestByTransactionInfoId(tx.getTransactionInfoId());
		slaTx.setUpdateUser(tx.getUpdateUser());
		slaTx.setUpdateTime(tx.getUpdateTime());
		slaTx.setCusAplSta("01");
		slaTxMapper.updateByPrimaryKeySelective(slaTx);
		String conId = UUID.randomUUID().toString().replace("-", "");
		con.setContractInfoId(conId);
		con.setEndTime(tx.getEndTime());
		con.setContStatus("10");
		contractInfoMapper.insertSelective(con);
		return conId;
	}
	

}