/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhtop.buss.common.entity.Module;
import com.rhtop.buss.biz.mapper.ModuleMapper;
import com.rhtop.buss.biz.service.ModuleService;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	private ModuleMapper moduleMapper;
	
	@Override
	public int insertModule(Module module) {
		return moduleMapper.insertSelective(module);
	}

	@Override
	public int deleteModule(String moduleId) {
		return moduleMapper.deleteByPrimaryKey(moduleId);
	}

	@Override
	public int updateModule(Module module) {
		return moduleMapper.updateByPrimaryKeySelective(module);
	}
	
	@Override
	public Module selectByPrimaryKey(String moduleId){
		return moduleMapper.selectByPrimaryKey(moduleId);
	}

	@Override
	public List<Module> listModules(Module module) {
		List<Module> moduleList = moduleMapper.listModules(module);
		return moduleList;
	}
	
	@Override
	public List<Module> listPageModule(Module module) {
		List<Module> modules = moduleMapper.listPageModule(module);
		return modules;
	}

	@Override
	public List<Module> listModulesByUserId(String userId) {
		List<Module> modules = moduleMapper.listModulesByUserId(userId);
		return modules;
	}

	@Override
	public Module selectByAuthName(String authName) {
		Module module = moduleMapper.selectByAuthName(authName);
		return module;
	}

}