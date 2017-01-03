package com.rhtop.buss.biz.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Department;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.DepartmentService;
import com.rhtop.buss.common.utils.DateUtils;

@Controller
@RequestMapping("service/department")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/insertDepartment")
	public InfoResult<Department> insertDepartment(@RequestBody Department department){
		InfoResult<Department> infoResult = new InfoResult<Department>();
		List<Department> departmentList = departmentService.listDepartments(new Department());
		department.setCompanyCode("BM000"+departmentList.size());
		department.setCreateUser(department.getUpdateUser());
		department.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		department.setUpdateUser(department.getUpdateUser());
		department.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = departmentService.insertDepartment(department);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("新增成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("新增失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteDepartment")
	public InfoResult<Department> deleteDepartment(@RequestParam("departmentId") String departmentId){
		InfoResult<Department> infoResult = new InfoResult<Department>();
		int num = departmentService.deleteDepartment(departmentId);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("删除成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("删除失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
     * 修改
     */
	@ResponseBody
	@RequestMapping("/updateDepartment")
	public InfoResult<Department> updateDepartment(@RequestBody Department department){
		InfoResult<Department> infoResult = new InfoResult<Department>();
		department.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = departmentService.updateDepartment(department);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("修改成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("修改失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
	 * 根据条件分页查询信息列表
	 */
	@ResponseBody
	@RequestMapping(value="/pager")
	public InfoResult<Department> listPageDepartment(Department department){
		InfoResult<Department> infoResult = new InfoResult<Department>();
		List<Department> departmentList = departmentService.listPageDepartment(department);
		infoResult.setCode("200");
		infoResult.setResList(departmentList);
		infoResult.setPage(department.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<Department> selectByPrimaryKey(@RequestParam("departmentId") String departmentId) {
		InfoResult<Department> infoResult = new InfoResult<Department>();
		infoResult.setCode("200");
		Department department = departmentService.selectByPrimaryKey(departmentId);
		infoResult.setResObject(department);
		return infoResult;
	}
}