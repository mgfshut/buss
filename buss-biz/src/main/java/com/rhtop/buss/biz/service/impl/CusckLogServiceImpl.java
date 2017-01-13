package com.rhtop.buss.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhtop.buss.biz.mapper.CusckLogMapper;
import com.rhtop.buss.biz.service.CusckLogService;
import com.rhtop.buss.common.entity.CusckLog;

@Service("cusckLogService")
public class CusckLogServiceImpl implements CusckLogService {
	@Autowired
	private CusckLogMapper cusckLogMapper;
	
	@Override
	public int insertCusckLog(CusckLog cusckLog) {
		return cusckLogMapper.insertSelective(cusckLog);
	}

	@Override
	public int deleteCusckLog(String cusckLogId) {
		return cusckLogMapper.deleteByPrimaryKey(cusckLogId);
	}

	@Override
	public CusckLog selectByPrimaryKey(String cusckLogId) {
		return cusckLogMapper.selectByPrimaryKey(cusckLogId);
	}

	@Override
	public List<CusckLog> listBusinessDiarys(CusckLog cusckLog) {
		List<CusckLog> cusckLogList = cusckLogMapper.listCusckLogs(cusckLog);
		return cusckLogList;
	}

	@Override
	public List<CusckLog> listPageBusinessDiary(CusckLog cusckLog) {
		List<CusckLog> cusckLogs = cusckLogMapper.listPageCusckLog(cusckLog);
		return cusckLogs;
	}

}
