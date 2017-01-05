/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;

import com.rhtop.buss.common.entity.Role;

public interface RoleMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(Role role);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String roleId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(Role role);
    /**
	 * 根据主键查询对象
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
	/**
	 * 根据用户ID获取角色信息列表
	 * @param memberId
	 * @return
	 */
	List<Role> listRolesByMemberId(String memberId);
    
}