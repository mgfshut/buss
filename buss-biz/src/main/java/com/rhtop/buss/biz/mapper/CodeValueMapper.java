/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;

import com.rhtop.buss.common.entity.CodeValue;

public interface CodeValueMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(CodeValue codeValue);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String codeValueId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(CodeValue codeValue);
    /**
	 * 根据主键查询对象
	 */
    CodeValue selectByPrimaryKey(String codeValueId);
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
	 * @date 2017年1月11日 上午10:50:43 
	 * @param codeValue
	 * @return
	 */
	int updateTheCodeValue(String code, String codeValue,
			String codeValueDescribe,String updateTime);
    
}