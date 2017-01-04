/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.Module;

public interface ModuleService{
    
	/**
	 * 新增
	 */
	int insertModule(Module module);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteModule(String moduleId);
	
	/**
	 * 修改
	 */
	int updateModule(Module module);
	
	/**
	 * 根据Id查找数据
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
	/**
	 * 根据授权名称获取功能对象
	 * @param authName
	 * @return
	 */
	Module selectByAuthName(String authName);
}