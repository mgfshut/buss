/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.Department;

public interface DepartmentService{
    
	/**
	 * 新增
	 */
	int insertDepartment(Department department);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteDepartment(String departmentId);
	
	/**
	 * 修改
	 */
	int updateDepartment(Department department);
	
	/**
	 * 根据Id查找数据
	 */
	Department selectByPrimaryKey(String departmentId);
	
	/**
	 * 根据条件查询列表
	 */
	List<Department> listDepartments(Department department);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<Department> listPageDepartment(Department department);
}