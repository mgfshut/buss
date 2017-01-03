/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.Department;
import com.rhtop.buss.biz.mapper.DepartmentMapper;
import com.rhtop.buss.biz.service.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Override
	public int insertDepartment(Department department) {
		return departmentMapper.insertSelective(department);
	}

	@Override
	public int deleteDepartment(String departmentId) {
		return departmentMapper.deleteByPrimaryKey(departmentId);
	}

	@Override
	public int updateDepartment(Department department) {
		return departmentMapper.updateByPrimaryKeySelective(department);
	}
	
	@Override
	public Department selectByPrimaryKey(String departmentId){
		return departmentMapper.selectByPrimaryKey(departmentId);
	}

	@Override
	public List<Department> listDepartments(Department department) {
		List<Department> departmentList = departmentMapper.listDepartments(department);
		return departmentList;
	}
	
	@Override
	public List<Department> listPageDepartment(Department department) {
		List<Department> departments = departmentMapper.listPageDepartment(department);
		return departments;
	}

}