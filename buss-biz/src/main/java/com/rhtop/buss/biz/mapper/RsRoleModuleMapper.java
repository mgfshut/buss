/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;

import com.rhtop.buss.common.entity.RsRoleModule;

public interface RsRoleModuleMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(RsRoleModule rsRoleModule);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String rsRoleModuleId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(RsRoleModule rsRoleModule);
    /**
	 * 根据主键查询对象
	 */
    RsRoleModule selectByPrimaryKey(String rsRoleModuleId);
    /**
     * 根据条件查询列表
     */
	List<RsRoleModule> listRsRoleModules(RsRoleModule rsRoleModule);
    /**
     * 根据条件分页查询列表
     */
	List<RsRoleModule> listPageRsRoleModule(RsRoleModule rsRoleModule);
	/**
	 * 根据角色ID删除所有相关的角色功能关系
	 * @param roleId
	 * @return
	 */
	int deleteRsRoleModuleByRoleId(String roleId);
    
}