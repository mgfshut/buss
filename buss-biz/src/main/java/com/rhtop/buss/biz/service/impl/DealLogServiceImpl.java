/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhtop.buss.biz.mapper.CategoryMapper;
import com.rhtop.buss.biz.mapper.CustomerMapper;
import com.rhtop.buss.biz.mapper.DealLogMapper;
import com.rhtop.buss.biz.mapper.SlaTransactionInfoMapper;
import com.rhtop.buss.biz.mapper.TransactionInfoMapper;
import com.rhtop.buss.biz.service.DealLogService;
import com.rhtop.buss.common.entity.DealLog;
import com.rhtop.buss.common.entity.TransactionInfo;

@Service("dealLogService")
public class DealLogServiceImpl implements DealLogService {
	@Autowired
	private DealLogMapper dealLogMapper;
	@Autowired
	private TransactionInfoMapper  transactionInfoMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private SlaTransactionInfoMapper  slaTransactionInfoMapper;
	
	@Override
	public int insertDealLog(DealLog dealLog) {
		return dealLogMapper.insertSelective(dealLog);
	}

	@Override
	public int deleteDealLog(String dealLogId) {
		return dealLogMapper.deleteByPrimaryKey(dealLogId);
	}

	@Override
	public int updateDealLog(DealLog dealLog) {
		return dealLogMapper.updateByPrimaryKeySelective(dealLog);
	}
	
	@Override
	public DealLog selectByPrimaryKey(String dealLogId){
		return dealLogMapper.selectByPrimaryKey(dealLogId);
	}

	@Override
	public List<DealLog> listDealLogs(DealLog dealLog) {
		List<DealLog> dealLogList = dealLogMapper.listDealLogs(dealLog);
		return dealLogList;
	}
	
	@Override
	public List<DealLog> listPageDealLog(DealLog dealLog) {
		List<DealLog> dealLogs = dealLogMapper.listPageDealLog(dealLog);
		return dealLogs;
	}

	@Override
	public List<TransactionInfo> DealLogList(TransactionInfo transactionInfo) {
		//得到交易(page分页减少数据的压力)
		List<TransactionInfo> trans = transactionInfoMapper.listPageTransactionInfo(transactionInfo);
		for(TransactionInfo tran:trans){
			//得到品类
			tran.setCate(categoryMapper.selectByPrimaryKey(tran.getCategoryId()));
			//得到客户
			tran.setCust(customerMapper.selectByPrimaryKey(tran.getCustomerId()));
			//得到交易日志
			DealLog dealLog = new DealLog();
			dealLog.setTransactionInfoId(tran.getTransactionInfoId());
			tran.setDealLogs(dealLogMapper.selectDealLog(dealLog));
		}
		return trans;
	}

}