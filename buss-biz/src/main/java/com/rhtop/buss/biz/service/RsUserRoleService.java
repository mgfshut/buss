/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.RsUserRole;

public interface RsUserRoleService{
    
	/**
	 * 新增
	 */
	int insertRsUserRole(RsUserRole rsUserRole);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteRsUserRole(String rsUserRoleId);
	
	/**
	 * 修改
	 */
	int updateRsUserRole(RsUserRole rsUserRole);
	
	/**
	 * 根据Id查找数据
	 */
	RsUserRole selectByPrimaryKey(String rsUserRoleId);
	
	/**
	 * 根据条件查询列表
	 */
	List<RsUserRole> listRsUserRoles(RsUserRole rsUserRole);
	/**
	 * 根据用户ID删除所有相关的用户角色关系
	 * @param memberId
	 * @return
	 */
	int deleteRsUserRoleByMemberId(String memberId);
}