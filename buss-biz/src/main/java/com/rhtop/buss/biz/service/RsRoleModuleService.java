/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.RsRoleModule;

public interface RsRoleModuleService{
    
	/**
	 * 新增
	 */
	int insertRsRoleModule(RsRoleModule rsRoleModule);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteRsRoleModule(String rsRoleModuleId);
	
	/**
	 * 修改
	 */
	int updateRsRoleModule(RsRoleModule rsRoleModule);
	
	/**
	 * 根据Id查找数据
	 */
	RsRoleModule selectByPrimaryKey(String rsRoleModuleId);
	
	/**
	 * 根据条件查询列表
	 */
	List<RsRoleModule> listRsRoleModules(RsRoleModule rsRoleModule);
}