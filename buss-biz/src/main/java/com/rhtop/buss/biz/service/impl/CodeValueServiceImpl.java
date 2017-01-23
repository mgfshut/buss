/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.rhtop.buss.common.entity.CodeMap;
import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.biz.mapper.CodeValueMapper;
import com.rhtop.buss.biz.service.CodeMapService;
import com.rhtop.buss.biz.service.CodeValueService;

@Service("codeValueService")
public class CodeValueServiceImpl implements CodeValueService {
	@Autowired
	private CodeValueMapper codeValueMapper;
	@Autowired
	private CodeMapService codeMapService;
	
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

	@Override
	public int updateTheCodeValue(String code, String codeValue,
			String codeValueDescribe,String updateTime) {
		return codeValueMapper.updateTheCodeValue(code,codeValue,codeValueDescribe,updateTime);
	}

	@Override
	public int addCodeValue(CodeValue codeValue) {
		CodeMap codeMap = new CodeMap();
		codeMap.setCode(codeValue.getCode());
		List<CodeMap> codeMapList = codeMapService.listCodeMaps(codeMap);
		if(codeMapList.size()>0){
			codeMap = codeMapList.get(0);
		}
		codeValue.setCodeMapId(codeMap.getCodeMapId());
		codeValue.setCreateUser(codeValue.getUpdateUser());
		codeValue.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		codeValue.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		codeValue.setCodeValueId(UUID.randomUUID().toString().replace("-", ""));
		return codeValueMapper.insertSelective(codeValue);
	}

	@Override
	public CodeValue queryCodeValueAndCodeName(String codeMap, String codeName) {
		return codeValueMapper.queryCodeValueAndCodeName(codeMap, codeName);
	}
	
	@Override
	public String maxCode(String code) {
		return codeValueMapper.maxCode(code);
	}

	@Override
	public List<CodeValue> listCodeValuesByCodes(List<String> codes) {
		return codeValueMapper.listCodeValuesByCodes(codes);
	}
}