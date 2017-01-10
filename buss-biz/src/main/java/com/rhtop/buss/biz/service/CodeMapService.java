/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.CodeMap;

public interface CodeMapService{
    
	/**
	 * 新增
	 */
	int insertCodeMap(CodeMap codeMap);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteCodeMap(String codeMapId);
	
	/**
	 * 修改
	 */
	int updateCodeMap(CodeMap codeMap);
	
	/**
	 * 根据Id查找数据
	 */
	CodeMap selectByPrimaryKey(String codeMapId);
	
	/**
	 * 根据条件查询列表
	 */
	List<CodeMap> listCodeMaps(CodeMap codeMap);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<CodeMap> listPageCodeMap(CodeMap codeMap);
	/**
	 * 查询所有代码集和代码值
	 * @return
	 */
	List<CodeMap> listAllCode();
}