package com.rhtop.buss.biz.web;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Dept;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.DeptService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/dept")
public class DeptController {
	@Autowired
	private DeptService deptService;
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteDept")
	public InfoResult<Dept> deleteDept(@RequestParam("deptId") String deptId){
		InfoResult<Dept> infoResult = new InfoResult<Dept>();
		int num = deptService.deleteDept(deptId);
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
	@RequestMapping("/updateDept")
	public InfoResult<Dept> updateDept(Dept dept){
		InfoResult<Dept> infoResult = new InfoResult<Dept>();
		dept.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = deptService.updateDept(dept);
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
	public InfoResult<Dept> listPageDept(Page page,Dept dept){
		InfoResult<Dept> infoResult = new InfoResult<Dept>();
		dept.setPage(page);
		List<Dept> deptList = deptService.listPageDept(dept);
		infoResult.setCode("200");
		infoResult.setResList(deptList);
		infoResult.setPage(dept.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<Dept> selectByPrimaryKey(@RequestParam("deptId") String deptId) {
		InfoResult<Dept> infoResult = new InfoResult<Dept>();
		infoResult.setCode("200");
		Dept dept = deptService.selectByPrimaryKey(deptId);
		infoResult.setResObject(dept);
		return infoResult;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public HtmlMessage save(@Valid @RequestParam(value = "userId") String userId,@Valid Dept dept){
		if(dept.getDeptId() == null || "".equals(dept.getDeptId())){
			String deptId = UUID.randomUUID().toString().replace("-", "");
			dept.setDeptId(deptId);
			dept.setCreateUser(userId);
			dept.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			dept.setUpdateUser(userId);
			dept.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			deptService.insertDept(dept);
		}else{
			dept.setUpdateUser(userId);
			dept.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			deptService.updateDept(dept);
		}
		return new HtmlMessage(dept).setNavTabId("sys:dept");
	}
	
	@RequestMapping("/{deptId}")
	@ResponseBody
	public Dept getByDeptId(@PathVariable("deptId") String deptId){
		Dept dept = deptService.selectByPrimaryKey(deptId);
		return dept;
	}
	
	@RequestMapping("/remove/{deptId}")
	@ResponseBody
	public HtmlMessage  removeRole(@PathVariable("deptId") String deptId){
		deptService.deleteDept(deptId);
		return new HtmlMessage("删除机构成功").setCallbackType(null);
	}
	
	@RequestMapping("/select/{property}")
	@ResponseBody
	public Map<String,Object> selectDept(@PathVariable("property") String property){
		try {
			property = URLDecoder.decode(property,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tree", deptService.getDeptTree(property));
		return map;
	}
}