/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.Dept;

public interface DeptMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(Dept dept);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String deptId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(Dept dept);
    /**
	 * 根据主键查询对象
	 */
    Dept selectByPrimaryKey(String deptId);
    /**
     * 根据条件查询列表
     */
	List<Dept> listDepts(Dept dept);
    /**
     * 根据条件分页查询列表
     */
	List<Dept> listPageDept(Dept dept);
    
}