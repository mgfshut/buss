/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.CodeValue;

public interface CodeValueService{
    
	/**
	 * 新增
	 */
	int insertCodeValue(CodeValue codeValue);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteCodeValue(String codeValueId);
	
	/**
	 * 修改
	 */
	int updateCodeValue(CodeValue codeValue);
	
	/**
	 * 根据Id查找数据
	 */
	CodeValue selectByPrimaryKey(String codeValueId);
	
	/**
	 * 根据字段类别和字段说明查询指定字典数据
	 * @param codeMap
	 * @param codeValueId
	 * @param codeName
	 * @return
	 */
	CodeValue queryCodeValueAndCodeName(String codeMap, String codeName);
	
	/**
	 * 根据条件查询列表
	 */
	List<CodeValue> listCodeValues(CodeValue codeValue);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<CodeValue> listPageCodeValue(CodeValue codeValue);
	/**
	 * 根据代码集编码获取代码值列表
	 * @param code
	 * @return
	 */
	List<CodeValue> listCodeValuesByCode(String code);
	/**
	 * 根据代码集编码、代码值、代码值描述修改
	 * @author mgf
	 * @date 2017年1月11日 上午10:45:05 
	 * @param code 代码集编码
	 * @param codeValue 代码值
	 * @param codeValueDescribe 代码值描述
	 * @return
	 */
	int updateTheCodeValue(String code, String codeValue,String codeValueDescribe,String updateTime);
	/**
	 * 增加代码值
	 * @author mgf
	 * @date 2017年1月14日 上午10:43:15 
	 * @param codeValue
	 * @return
	 */
	int addCodeValue(CodeValue codeValue);
	
	public String maxCode(String code);
}