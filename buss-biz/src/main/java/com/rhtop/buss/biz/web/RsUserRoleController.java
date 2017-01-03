package com.rhtop.buss.biz.web;


import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.RsUserRole;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.RsUserRoleService;

@Controller
@RequestMapping("service/rsUserRole")
public class RsUserRoleController {
	@Autowired
	private RsUserRoleService rsUserRoleService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/insertRsUserRole")
	public InfoResult<RsUserRole> insertRsUserRole(@RequestBody RsUserRole rsUserRole){
		InfoResult<RsUserRole> infoResult = new InfoResult<RsUserRole>();
		rsUserRole.setRsUserRoleId(UUID.randomUUID().toString());
		int num = rsUserRoleService.insertRsUserRole(rsUserRole);
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
	@RequestMapping("/deleteRsUserRole")
	public InfoResult<RsUserRole> deleteRsUserRole(@RequestParam("rsUserRoleId") String rsUserRoleId){
		InfoResult<RsUserRole> infoResult = new InfoResult<RsUserRole>();
		int num = rsUserRoleService.deleteRsUserRole(rsUserRoleId);
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
	@RequestMapping("/updateRsUserRole")
	public InfoResult<RsUserRole> updateRsUserRole(@RequestBody RsUserRole rsUserRole){
		InfoResult<RsUserRole> infoResult = new InfoResult<RsUserRole>();
		int num = rsUserRoleService.updateRsUserRole(rsUserRole);
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
	public InfoResult<RsUserRole> selectByPrimaryKey(@RequestParam("rsUserRoleId") String rsUserRoleId) {
		InfoResult<RsUserRole> infoResult = new InfoResult<RsUserRole>();
		infoResult.setCode("200");
		RsUserRole rsUserRole = rsUserRoleService.selectByPrimaryKey(rsUserRoleId);
		infoResult.setResObject(rsUserRole);
		return infoResult;
	}
}