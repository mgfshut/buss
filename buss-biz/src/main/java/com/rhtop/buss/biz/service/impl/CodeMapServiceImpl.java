/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.rhtop.buss.common.entity.CodeMap;
import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.biz.mapper.CodeMapMapper;
import com.rhtop.buss.biz.mapper.CodeValueMapper;
import com.rhtop.buss.biz.service.CodeMapService;

@Service("codeMapService")
public class CodeMapServiceImpl implements CodeMapService {
	@Autowired
	private CodeMapMapper codeMapMapper;
	@Autowired
	private CodeValueMapper codeValueMapper;
	
	@Override
	@Transactional
	public int insertCodeMap(CodeMap codeMap) {
		return codeMapMapper.insertSelective(codeMap);
	}

	@Override
	public int deleteCodeMap(String codeMapId) {
		return codeMapMapper.deleteByPrimaryKey(codeMapId);
	}

	@Override
	public int updateCodeMap(CodeMap codeMap) {
		return codeMapMapper.updateByPrimaryKeySelective(codeMap);
	}
	
	@Override
	public CodeMap selectByPrimaryKey(String codeMapId){
		return codeMapMapper.selectByPrimaryKey(codeMapId);
	}

	@Override
	public List<CodeMap> listCodeMaps(CodeMap codeMap) {
		List<CodeMap> codeMapList = codeMapMapper.listCodeMaps(codeMap);
		return codeMapList;
	}
	
	@Override
	public List<CodeMap> listPageCodeMap(CodeMap codeMap) {
		List<CodeMap> codeMaps = codeMapMapper.listPageCodeMap(codeMap);
		return codeMaps;
	}

	@Override
	public List<CodeMap> listAllCode() {
		List<CodeMap> codeMapList = codeMapMapper.listCodeMaps(new CodeMap());
		for(CodeMap codeMap:codeMapList){
			List<CodeValue> codeValueList = codeValueMapper.listCodeValuesByCode(codeMap.getCode());
			codeMap.setCodeValueList(codeValueList);
		}
		return codeMapList;
	}

}