/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;

import com.rhtop.buss.common.entity.Module;

public interface ModuleMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(Module module);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String moduleId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(Module module);
    /**
	 * 根据主键查询对象
	 */
    Module selectByPrimaryKey(String moduleId);
    /**
     * 根据条件查询列表
     */
	List<Module> listModules(Module module);
    /**
     * 根据条件分页查询列表
     */
	List<Module> listPageModule(Module module);
	/**
	 * 根据用户ID获取功能列表
	 * @param userId
	 * @return
	 */
	List<Module> listModulesByUserId(String userId);
    
}