/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.RsRoleModule;
import com.rhtop.buss.biz.mapper.RsRoleModuleMapper;
import com.rhtop.buss.biz.service.RsRoleModuleService;

@Service("rsRoleModuleService")
public class RsRoleModuleServiceImpl implements RsRoleModuleService {
	@Autowired
	private RsRoleModuleMapper rsRoleModuleMapper;
	
	@Override
	public int insertRsRoleModule(RsRoleModule rsRoleModule) {
		return rsRoleModuleMapper.insertSelective(rsRoleModule);
	}

	@Override
	public int deleteRsRoleModule(String rsRoleModuleId) {
		return rsRoleModuleMapper.deleteByPrimaryKey(rsRoleModuleId);
	}

	@Override
	public int updateRsRoleModule(RsRoleModule rsRoleModule) {
		return rsRoleModuleMapper.updateByPrimaryKeySelective(rsRoleModule);
	}
	
	@Override
	public RsRoleModule selectByPrimaryKey(String rsRoleModuleId){
		return rsRoleModuleMapper.selectByPrimaryKey(rsRoleModuleId);
	}

	@Override
	public List<RsRoleModule> listRsRoleModules(RsRoleModule rsRoleModule) {
		List<RsRoleModule> rsRoleModuleList = rsRoleModuleMapper.listRsRoleModules(rsRoleModule);
		return rsRoleModuleList;
	}

	@Override
	public int deleteRsRoleModuleByRoleId(String roleId) {
		return rsRoleModuleMapper.deleteRsRoleModuleByRoleId(roleId);
	}

}