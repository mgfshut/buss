/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.Role;
import com.rhtop.buss.biz.mapper.RoleMapper;
import com.rhtop.buss.biz.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public int insertRole(Role role) {
		return roleMapper.insertSelective(role);
	}

	@Override
	public int deleteRole(String roleId) {
		return roleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public int updateRole(Role role) {
		return roleMapper.updateByPrimaryKeySelective(role);
	}
	
	@Override
	public Role selectByPrimaryKey(String roleId){
		return roleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public List<Role> listRoles(Role role) {
		List<Role> roleList = roleMapper.listRoles(role);
		return roleList;
	}
	
	@Override
	public List<Role> listPageRole(Role role) {
		List<Role> roles = roleMapper.listPageRole(role);
		return roles;
	}

	@Override
	public List<String> getPermissionListByRoleId(String roleId) {
		List<String> list = roleMapper.getPermissionListByRoleId(roleId);
		return list;
	}

	@Override
	public List<Role> listRolesByMemberId(String memberId) {
		List<Role> roleList = roleMapper.listRolesByMemberId(memberId);
		return roleList;
	}
}