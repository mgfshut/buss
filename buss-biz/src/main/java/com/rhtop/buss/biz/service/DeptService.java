/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.Dept;

public interface DeptService{
    
	/**
	 * 新增
	 */
	int insertDept(Dept dept);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteDept(String deptId);
	
	/**
	 * 修改
	 */
	int updateDept(Dept dept);
	
	/**
	 * 根据Id查找数据
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

	String getDeptTree(String property);
}