/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.Department;

public interface DepartmentMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(Department department);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String departmentId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(Department department);
    /**
	 * 根据主键查询对象
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