/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.CodeMap;

public interface CodeMapMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(CodeMap codeMap);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String codeMapId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(CodeMap codeMap);
    /**
	 * 根据主键查询对象
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
    
}