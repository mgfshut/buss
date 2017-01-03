/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.biz.mapper.CodeValueMapper;
import com.rhtop.buss.biz.service.CodeValueService;

@Service("codeValueService")
public class CodeValueServiceImpl implements CodeValueService {
	@Autowired
	private CodeValueMapper codeValueMapper;
	
	@Override
	public int insertCodeValue(CodeValue codeValue) {
		return codeValueMapper.insertSelective(codeValue);
	}

	@Override
	public int deleteCodeValue(String codeValueId) {
		return codeValueMapper.deleteByPrimaryKey(codeValueId);
	}

	@Override
	public int updateCodeValue(CodeValue codeValue) {
		return codeValueMapper.updateByPrimaryKeySelective(codeValue);
	}
	
	@Override
	public CodeValue selectByPrimaryKey(String codeValueId){
		return codeValueMapper.selectByPrimaryKey(codeValueId);
	}

	@Override
	public List<CodeValue> listCodeValues(CodeValue codeValue) {
		List<CodeValue> codeValueList = codeValueMapper.listCodeValues(codeValue);
		return codeValueList;
	}
	
	@Override
	public List<CodeValue> listPageCodeValue(CodeValue codeValue) {
		List<CodeValue> codeValues = codeValueMapper.listPageCodeValue(codeValue);
		return codeValues;
	}

	@Override
	public List<CodeValue> listCodeValuesByCode(String code) {
		List<CodeValue> codeValues = codeValueMapper.listCodeValuesByCode(code);
		return codeValues;
	}

}