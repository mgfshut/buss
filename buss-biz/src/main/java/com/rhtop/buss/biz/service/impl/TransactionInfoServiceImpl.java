/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.rhtop.buss.common.entity.SlaTransactionInfo;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.biz.mapper.SlaTransactionInfoMapper;
import com.rhtop.buss.biz.mapper.TransactionInfoMapper;
import com.rhtop.buss.biz.service.TransactionInfoService;

@Service("transactionInfoService")
public class TransactionInfoServiceImpl implements TransactionInfoService {
	@Autowired
	private TransactionInfoMapper transactionInfoMapper;
	@Autowired
	private SlaTransactionInfoMapper slaTxMapper;
	
	@Override
	public int insertTransactionInfo(TransactionInfo transactionInfo) {
		return transactionInfoMapper.insertSelective(transactionInfo);
	}
	@Override
	public String createDeal(TransactionInfo tx){
		String transactionInfoId = null;
		String slaTransactionInfoId = null;
		if(tx.getTransactionInfoId()==null||tx.getTransactionInfoId().trim().equals("")){
			transactionInfoId = UUID.randomUUID().toString().replace("-", "");
			String now = tx.getUpdateTime();
			String userId = tx.getUpdateUser();
			tx.setTransactionInfoId(transactionInfoId);
			tx.setCreateUser(userId);
			tx.setCreateTime(now);
			tx.setPcasTime(now);
			if(tx.getPcasPri()==null||tx.getPcasPri().trim().equals("")){
				//如果没有填写价格，默认他是发起交易。
				tx.setTxStatus("10");
			}else{
				//填写了价格，认为它是询价中。
				tx.setTxStatus("20");
			}
			transactionInfoMapper.insertSelective(tx);
			SlaTransactionInfo slaTx = new SlaTransactionInfo();
			slaTransactionInfoId = UUID.randomUUID().toString().replace("-", "");
			slaTx.setSlaTransactionInfoId(slaTransactionInfoId);
			slaTx.setCreateTime(now);
			slaTx.setUpdateTime(now);
			slaTx.setCreateUser(userId);
			slaTx.setUpdateUser(userId);
			slaTx.setTransactionInfoId(transactionInfoId);
			slaTx.setPcasPri(tx.getPcasPri());
			slaTx.setPcasTime(now);
			slaTx.setTxAmo(tx.getTxAmo());
			slaTxMapper.insertSelective(slaTx);
		}else{
			throw new RuntimeException("交易记录已存在！");
		}
		return transactionInfoId;
	}
	
	@Override
	public String cusNegotiate(TransactionInfo tx){
		tx.setTxStatus("20");
		String transactionInfoId = tx.getTransactionInfoId();
		transactionInfoMapper.updateByPrimaryKeySelective(tx);
		SlaTransactionInfo slaTx = new SlaTransactionInfo();
		String slaTransactionInfoId = UUID.randomUUID().toString().replace("-", "");
		slaTx.setSlaTransactionInfoId(slaTransactionInfoId);
		String now = tx.getUpdateTime();
		String userId = tx.getUpdateUser();
		slaTx.setCreateTime(now);
		slaTx.setUpdateTime(now);
		slaTx.setCreateUser(userId);
		slaTx.setUpdateUser(userId);
		slaTx.setTransactionInfoId(transactionInfoId);
		slaTx.setPcasPri(tx.getPcasPri());
		slaTx.setPcasTime(now);
		slaTx.setTxAmo(tx.getTxAmo());
		slaTxMapper.insertSelective(slaTx);
		return transactionInfoId;
	}
	
	@Override
	public String universeNegotiate(TransactionInfo tx){
		String transactionInfoId = tx.getTransactionInfoId();
		transactionInfoMapper.updateByPrimaryKeySelective(tx);
		//TODO:这里没做完啊！
		return transactionInfoId;
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