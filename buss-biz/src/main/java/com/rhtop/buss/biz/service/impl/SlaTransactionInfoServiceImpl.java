/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.rhtop.buss.common.entity.SlaTransactionInfo;
import com.rhtop.buss.biz.mapper.SlaTransactionInfoMapper;
import com.rhtop.buss.biz.service.SlaTransactionInfoService;

@Service("slaTransactionInfoService")
public class SlaTransactionInfoServiceImpl implements SlaTransactionInfoService {
	@Autowired
	private SlaTransactionInfoMapper slaTransactionInfoMapper;
	
	@Override
	public int insertSlaTransactionInfo(SlaTransactionInfo slaTransactionInfo) {
		return slaTransactionInfoMapper.insertSelective(slaTransactionInfo);
	}

	@Override
	public int deleteSlaTransactionInfo(String slaTransactionInfoId) {
		return slaTransactionInfoMapper.deleteByPrimaryKey(slaTransactionInfoId);
	}

	@Override
	public int updateSlaTransactionInfo(SlaTransactionInfo slaTransactionInfo) {
		return slaTransactionInfoMapper.updateByPrimaryKeySelective(slaTransactionInfo);
	}
	
	@Override
	public SlaTransactionInfo selectByPrimaryKey(String slaTransactionInfoId){
		return slaTransactionInfoMapper.selectByPrimaryKey(slaTransactionInfoId);
	}

	@Override
	public List<SlaTransactionInfo> listSlaTransactionInfos(SlaTransactionInfo slaTransactionInfo) {
		List<SlaTransactionInfo> slaTransactionInfoList = slaTransactionInfoMapper.listSlaTransactionInfos(slaTransactionInfo);
		return slaTransactionInfoList;
	}
	
	@Override
	public List<SlaTransactionInfo> listPageSlaTransactionInfo(SlaTransactionInfo slaTransactionInfo) {
		List<SlaTransactionInfo> slaTransactionInfos = slaTransactionInfoMapper.listPageSlaTransactionInfo(slaTransactionInfo);
		return slaTransactionInfos;
	}

}