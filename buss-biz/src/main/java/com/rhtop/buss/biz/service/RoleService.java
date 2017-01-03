/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.Role;

public interface RoleService{
    
	/**
	 * 新增
	 */
	int insertRole(Role role);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteRole(String roleId);
	
	/**
	 * 修改
	 */
	int updateRole(Role role);
	
	/**
	 * 根据Id查找数据
	 */
	Role selectByPrimaryKey(String roleId);
	
	/**
	 * 根据条件查询列表
	 */
	List<Role> listRoles(Role role);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<Role> listPageRole(Role role);
	/**
	 * 根据角色ID获取权限列表
	 * @param roleId
	 * @return
	 */
	List<String> getPermissionListByRoleId(String roleId);
}