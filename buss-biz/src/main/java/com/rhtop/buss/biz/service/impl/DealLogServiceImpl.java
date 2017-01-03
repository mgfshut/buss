/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.rhtop.buss.common.entity.DealLog;
import com.rhtop.buss.biz.mapper.DealLogMapper;
import com.rhtop.buss.biz.service.DealLogService;

@Service("dealLogService")
public class DealLogServiceImpl implements DealLogService {
	@Autowired
	private DealLogMapper dealLogMapper;
	
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

}