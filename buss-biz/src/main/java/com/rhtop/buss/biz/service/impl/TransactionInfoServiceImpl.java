/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.biz.mapper.TransactionInfoMapper;
import com.rhtop.buss.biz.service.TransactionInfoService;

@Service("transactionInfoService")
public class TransactionInfoServiceImpl implements TransactionInfoService {
	@Autowired
	private TransactionInfoMapper transactionInfoMapper;
	
	@Override
	public int insertTransactionInfo(TransactionInfo transactionInfo) {
		return transactionInfoMapper.insertSelective(transactionInfo);
	}

	@Override
	public int deleteTransactionInfo(String transactionInfoId) {
		return transactionInfoMapper.deleteByPrimaryKey(transactionInfoId);
	}

	@Override
	public int updateTransactionInfo(TransactionInfo transactionInfo) {
		return transactionInfoMapper.updateByPrimaryKeySelective(transactionInfo);
	}
	
	@Override
	public TransactionInfo selectByPrimaryKey(String transactionInfoId){
		return transactionInfoMapper.selectByPrimaryKey(transactionInfoId);
	}

	@Override
	public List<TransactionInfo> listTransactionInfos(TransactionInfo transactionInfo) {
		List<TransactionInfo> transactionInfoList = transactionInfoMapper.listTransactionInfos(transactionInfo);
		return transactionInfoList;
	}
	
	@Override
	public List<TransactionInfo> listPageTransactionInfo(TransactionInfo transactionInfo) {
		List<TransactionInfo> transactionInfos = transactionInfoMapper.listPageTransactionInfo(transactionInfo);
		return transactionInfos;
	}

}