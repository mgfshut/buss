package com.rhtop.buss.biz.web;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Module;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.model.ListData;
import com.rhtop.buss.common.model.TreeNode;
import com.rhtop.buss.biz.service.ModuleService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.utils.TreeUtils;

@Controller
@RequestMapping("service/module")
public class ModuleController {
	@Autowired
	private ModuleService moduleService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/insertModule")
	public InfoResult<Module> insertModule(@RequestBody Module module){
		InfoResult<Module> infoResult = new InfoResult<Module>();
		module.setModuleId(UUID.randomUUID().toString());
		module.setCreateUser(module.getUpdateUser());
		module.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		module.setUpdateUser(module.getUpdateUser());
		module.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = moduleService.insertModule(module);
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
	@RequestMapping("/deleteModule")
	public InfoResult<Module> deleteModule(@RequestParam("moduleId") String moduleId){
		InfoResult<Module> infoResult = new InfoResult<Module>();
		int num = moduleService.deleteModule(moduleId);
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
	@RequestMapping("/updateModule")
	public InfoResult<Module> updateModule(@RequestBody Module module){
		InfoResult<Module> infoResult = new InfoResult<Module>();
		module.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = moduleService.updateModule(module);
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
	public InfoResult<Module> listPageModule(Module module){
		InfoResult<Module> infoResult = new InfoResult<Module>();
		List<Module> moduleList = moduleService.listPageModule(module);
		infoResult.setCode("200");
		infoResult.setResList(moduleList);
		infoResult.setPage(module.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<Module> selectByPrimaryKey(@RequestParam("moduleId") String moduleId) {
		InfoResult<Module> infoResult = new InfoResult<Module>();
		infoResult.setCode("200");
		Module module = moduleService.selectByPrimaryKey(moduleId);
		infoResult.setResObject(module);
		return infoResult;
	}
	
	@RequestMapping("permission/all")
	@ResponseBody
	public List<Module> getAllUrlPermission() {
		Module module = new Module();
		List<Module> list = moduleService.listModules(module);
		return list;
	}
	
	@RequestMapping("permissionTree/{userId}")
	@ResponseBody
	public ListData<TreeNode> getPermissionModuleTree(@PathVariable("userId") String userId) {
		List<Module> moduleList = moduleService.listModulesByUserId(userId);
		ListData<TreeNode> listData = new ListData<>(TreeUtils.toTreeList(moduleList));
		return listData;
	}
}