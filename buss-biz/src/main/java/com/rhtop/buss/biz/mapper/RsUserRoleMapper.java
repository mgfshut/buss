/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.RsUserRole;

public interface RsUserRoleMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(RsUserRole rsUserRole);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String rsUserRoleId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(RsUserRole rsUserRole);
    /**
	 * 根据主键查询对象
	 */
    RsUserRole selectByPrimaryKey(String rsUserRoleId);
    /**
     * 根据条件查询列表
     */
	List<RsUserRole> listRsUserRoles(RsUserRole rsUserRole);
    /**
     * 根据条件分页查询列表
     */
	List<RsUserRole> listPageRsUserRole(RsUserRole rsUserRole);
    
}