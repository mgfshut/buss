/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.rhtop.buss.common.entity.BusinessDiary;
import com.rhtop.buss.biz.mapper.BusinessDiaryMapper;
import com.rhtop.buss.biz.service.BusinessDiaryService;

@Service("businessDiaryService")
public class BusinessDiaryServiceImpl implements BusinessDiaryService {
	@Autowired
	private BusinessDiaryMapper businessDiaryMapper;
	
	@Override
	public int insertBusinessDiary(BusinessDiary businessDiary) {
		return businessDiaryMapper.insertSelective(businessDiary);
	}

	@Override
	public int deleteBusinessDiary(String businessDiaryId) {
		return businessDiaryMapper.deleteByPrimaryKey(businessDiaryId);
	}

	@Override
	public int updateBusinessDiary(BusinessDiary businessDiary) {
		return businessDiaryMapper.updateByPrimaryKeySelective(businessDiary);
	}
	
	@Override
	public BusinessDiary selectByPrimaryKey(String businessDiaryId){
		return businessDiaryMapper.selectByPrimaryKey(businessDiaryId);
	}

	@Override
	public List<BusinessDiary> listBusinessDiarys(BusinessDiary businessDiary) {
		List<BusinessDiary> businessDiaryList = businessDiaryMapper.listBusinessDiarys(businessDiary);
		return businessDiaryList;
	}
	
	@Override
	public List<BusinessDiary> listPageBusinessDiary(BusinessDiary businessDiary) {
		List<BusinessDiary> businessDiarys = businessDiaryMapper.listPageBusinessDiary(businessDiary);
		return businessDiarys;
	}

}