package com.rhtop.buss.biz.web;


import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Module;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.Role;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.RsRoleModule;
import com.rhtop.buss.biz.service.ModuleService;
import com.rhtop.buss.biz.service.RoleService;
import com.rhtop.buss.biz.service.RsRoleModuleService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private RsRoleModuleService rsRoleModuleService;
	@Autowired
	private ModuleService moduleService;
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/insertRole")
	public InfoResult<Role> insertRole(@RequestBody Role role){
		InfoResult<Role> infoResult = new InfoResult<Role>();
		role.setRoleId(UUID.randomUUID().toString());
		role.setCreateUser(role.getUpdateUser());
		role.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		role.setUpdateUser(role.getUpdateUser());
		role.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = roleService.insertRole(role);
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
	@RequestMapping("/deleteRole")
	public InfoResult<Role> deleteRole(@RequestParam("roleId") String roleId){
		InfoResult<Role> infoResult = new InfoResult<Role>();
		int num = roleService.deleteRole(roleId);
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
	@RequestMapping("/updateRole")
	public InfoResult<Role> updateRole(@RequestBody Role role){
		InfoResult<Role> infoResult = new InfoResult<Role>();
		role.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = roleService.updateRole(role);
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
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<Role> selectByPrimaryKey(@RequestParam("roleId") String roleId) {
		InfoResult<Role> infoResult = new InfoResult<Role>();
		infoResult.setCode("200");
		Role role = roleService.selectByPrimaryKey(roleId);
		infoResult.setResObject(role);
		return infoResult;
	}
	
	/**
	 * 根据条件分页查询信息列表
	 */
	@ResponseBody
	@RequestMapping(value="/pager")
	public InfoResult<Role> listPageRole(Page page,Role role){
		InfoResult<Role> infoResult = new InfoResult<Role>();
		role.setPage(page);
		List<Role> roleList = roleService.listPageRole(role);
		infoResult.setCode("200");
		infoResult.setResList(roleList);
		infoResult.setPage(role.getPage());
		return infoResult;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public HtmlMessage save(@Valid @RequestParam(value = "userId") String userId,@Valid Role role){
		if(role.getRoleId() == null || "".equals(role.getRoleId())){
			String roleId = UUID.randomUUID().toString().replace("-", "");
			role.setRoleId(roleId);
			for(String authName:role.getPermissionList()){
				RsRoleModule rsRoleModule = new RsRoleModule();
				rsRoleModule.setRsRoleModuleId(UUID.randomUUID().toString().replace("-", ""));
				rsRoleModule.setRoleId(roleId);
				Module module = moduleService.selectByAuthName(authName);
				rsRoleModule.setModuleId(module.getModuleId());
				rsRoleModuleService.insertRsRoleModule(rsRoleModule);
			}
			role.setCreateUser(userId);
			role.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			role.setUpdateUser(userId);
			role.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			roleService.insertRole(role);
		}else{
			rsRoleModuleService.deleteRsRoleModuleByRoleId(role.getRoleId());
			for(String authName:role.getPermissionList()){
				RsRoleModule rsRoleModule = new RsRoleModule();
				rsRoleModule.setRsRoleModuleId(UUID.randomUUID().toString().replace("-", ""));
				rsRoleModule.setRoleId(role.getRoleId());
				Module module = moduleService.selectByAuthName(authName);
				rsRoleModule.setModuleId(module.getModuleId());
				rsRoleModuleService.insertRsRoleModule(rsRoleModule);
			}
			role.setUpdateUser(userId);
			role.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			roleService.updateRole(role);
		}
		return new HtmlMessage(role);
	}
	
	@RequestMapping("/{roleId}")
	@ResponseBody
	public Role getByRoleId(@PathVariable("roleId") String roleId){
		Role role = roleService.selectByPrimaryKey(roleId);
		List<String> permissionList = roleService.getPermissionListByRoleId(roleId);
		role.setPermissionList(permissionList);
		return role;
	}
	
	@RequestMapping("/remove/{roleId}")
	@ResponseBody
	public HtmlMessage  removeRole(@PathVariable("roleId") String roleId){
		roleService.deleteRole(roleId);
		return new HtmlMessage("删除角色成功").setCallbackType(null);
	}
}