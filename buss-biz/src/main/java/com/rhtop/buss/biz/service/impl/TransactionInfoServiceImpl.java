/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.SlaTransactionInfo;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.biz.mapper.TransactionInfoMapper;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.ContractInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.SlaTransactionInfoService;
import com.rhtop.buss.biz.service.TransactionInfoService;

@Service("transactionInfoService")
public class TransactionInfoServiceImpl implements TransactionInfoService {
	
	@Autowired
	private CategoryService catSer;
	@Autowired
	private CustomerService cusSer;
	@Autowired
	private SlaTransactionInfoService slaSer; 
	@Autowired
	private ContractInfoService conSer;
	
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
		TransactionInfo tra= transactionInfoMapper.selectByPrimaryKey(transactionInfoId);
		//查询该交易的品类详情
		Category cate = catSer.selectByPrimaryKey(tra.getCategoryId());
		//查询该交易的客户详情
		Customer cust = cusSer.selectByPrimaryKey(tra.getCustomerId());
		//回盘信息
		SlaTransactionInfo slatransaction = new SlaTransactionInfo();
		slatransaction.setTransactionInfoId(transactionInfoId);
		List<SlaTransactionInfo> sla = slaSer.listSlaTransactionInfos(slatransaction);
		//合同信息	
		ContractInfo contractinfo = new ContractInfo();
		contractinfo.setTransactionInfoId(transactionInfoId);
		ContractInfo con = conSer.listContractInfos(contractinfo).get(0);
		
		tra.setSla(sla);
		tra.setCate(cate);
		tra.setCust(cust);
		tra.setContract(con);
		return tra;
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