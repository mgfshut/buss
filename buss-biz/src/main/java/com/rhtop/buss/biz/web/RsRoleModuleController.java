package com.rhtop.buss.biz.web;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.RsRoleModule;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.biz.service.RsRoleModuleService;

@Controller
@RequestMapping("service/rsRoleModule")
public class RsRoleModuleController  extends BaseController {
	@Autowired
	private RsRoleModuleService rsRoleModuleService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/insertRsRoleModule")
	public InfoResult<RsRoleModule> insertRsRoleModule(@RequestBody RsRoleModule rsRoleModule){
		InfoResult<RsRoleModule> infoResult = new InfoResult<RsRoleModule>();
		rsRoleModule.setRsRoleModuleId(UUID.randomUUID().toString());
		int num = rsRoleModuleService.insertRsRoleModule(rsRoleModule);
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
	@RequestMapping("/deleteRsRoleModule")
	public InfoResult<RsRoleModule> deleteRsRoleModule(@RequestParam("rsRoleModuleId") String rsRoleModuleId){
		InfoResult<RsRoleModule> infoResult = new InfoResult<RsRoleModule>();
		int num = rsRoleModuleService.deleteRsRoleModule(rsRoleModuleId);
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
	@RequestMapping("/updateRsRoleModule")
	public InfoResult<RsRoleModule> updateRsRoleModule(@RequestBody RsRoleModule rsRoleModule){
		InfoResult<RsRoleModule> infoResult = new InfoResult<RsRoleModule>();
		int num = rsRoleModuleService.updateRsRoleModule(rsRoleModule);
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
	public InfoResult<RsRoleModule> selectByPrimaryKey(@RequestParam("rsRoleModuleId") String rsRoleModuleId) {
		InfoResult<RsRoleModule> infoResult = new InfoResult<RsRoleModule>();
		infoResult.setCode("200");
		RsRoleModule rsRoleModule = rsRoleModuleService.selectByPrimaryKey(rsRoleModuleId);
		infoResult.setResObject(rsRoleModule);
		return infoResult;
	}
}