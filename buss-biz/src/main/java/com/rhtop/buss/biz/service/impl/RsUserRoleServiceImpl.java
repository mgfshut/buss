/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.RsUserRole;
import com.rhtop.buss.biz.mapper.RsUserRoleMapper;
import com.rhtop.buss.biz.service.RsUserRoleService;

@Service("rsUserRoleService")
public class RsUserRoleServiceImpl implements RsUserRoleService {
	@Autowired
	private RsUserRoleMapper rsUserRoleMapper;
	
	@Override
	public int insertRsUserRole(RsUserRole rsUserRole) {
		return rsUserRoleMapper.insertSelective(rsUserRole);
	}

	@Override
	public int deleteRsUserRole(String rsUserRoleId) {
		return rsUserRoleMapper.deleteByPrimaryKey(rsUserRoleId);
	}

	@Override
	public int updateRsUserRole(RsUserRole rsUserRole) {
		return rsUserRoleMapper.updateByPrimaryKeySelective(rsUserRole);
	}
	
	@Override
	public RsUserRole selectByPrimaryKey(String rsUserRoleId){
		return rsUserRoleMapper.selectByPrimaryKey(rsUserRoleId);
	}

	@Override
	public List<RsUserRole> listRsUserRoles(RsUserRole rsUserRole) {
		List<RsUserRole> rsUserRoleList = rsUserRoleMapper.listRsUserRoles(rsUserRole);
		return rsUserRoleList;
	}

	@Override
	public int deleteRsUserRoleByMemberId(String memberId) {
		return rsUserRoleMapper.deleteRsUserRoleByMemberId(memberId);
	}

}