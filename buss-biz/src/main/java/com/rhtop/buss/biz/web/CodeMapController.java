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

import com.rhtop.buss.common.entity.CodeMap;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.biz.service.CodeMapService;
import com.rhtop.buss.biz.service.CodeValueService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/codeMap")
public class CodeMapController {
	@Autowired
	private CodeMapService codeMapService;
	@Autowired
	private CodeValueService codeValueService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/insertCodeMap")
	public InfoResult<CodeMap> insertCodeMap(@RequestBody CodeMap codeMap){
		InfoResult<CodeMap> infoResult = new InfoResult<CodeMap>();
		codeMap.setCodeMapId(UUID.randomUUID().toString());
		codeMap.setCreateUser(codeMap.getUpdateUser());
		codeMap.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		codeMap.setUpdateUser(codeMap.getUpdateUser());
		codeMap.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = codeMapService.insertCodeMap(codeMap);
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
	@RequestMapping("/deleteCodeMap")
	public InfoResult<CodeMap> deleteCodeMap(@RequestParam("codeMapId") String codeMapId){
		InfoResult<CodeMap> infoResult = new InfoResult<CodeMap>();
		int num = codeMapService.deleteCodeMap(codeMapId);
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
	@RequestMapping("/updateCodeMap")
	public InfoResult<CodeMap> updateCodeMap(@RequestBody CodeMap codeMap){
		InfoResult<CodeMap> infoResult = new InfoResult<CodeMap>();
		codeMap.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = codeMapService.updateCodeMap(codeMap);
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
	public InfoResult<CodeMap> listPageCodeMap(Page page,CodeMap codeMap){
		InfoResult<CodeMap> infoResult = new InfoResult<CodeMap>();
		codeMap.setPage(page);
		List<CodeMap> codeMapList = codeMapService.listPageCodeMap(codeMap);
		infoResult.setCode("200");
		infoResult.setResList(codeMapList);
		infoResult.setPage(codeMap.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<CodeMap> selectByPrimaryKey(@RequestParam("codeMapId") String codeMapId) {
		InfoResult<CodeMap> infoResult = new InfoResult<CodeMap>();
		infoResult.setCode("200");
		CodeMap codeMap = codeMapService.selectByPrimaryKey(codeMapId);
		infoResult.setResObject(codeMap);
		return infoResult;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public HtmlMessage save(@Valid @RequestParam(value = "userId") String userId,@Valid CodeMap codeMap){
		if(codeMap.getCodeMapId() == null || "".equals(codeMap.getCodeMapId())){
			String codeMapId = UUID.randomUUID().toString().replace("-", "");
			codeMap.setCodeMapId(codeMapId);
			codeMap.setCreateUser(userId);
			codeMap.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			codeMap.setUpdateUser(userId);
			codeMap.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			codeMapService.insertCodeMap(codeMap);
		}else{
			codeMap.setUpdateUser(userId);
			codeMap.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			codeMapService.updateCodeMap(codeMap);
		}
		return new HtmlMessage(codeMap);
	}
	
	@RequestMapping("/{codeMapId}")
	@ResponseBody
	public CodeMap getByCodeMapId(@PathVariable("codeMapId") String codeMapId){
		CodeMap codeMap = codeMapService.selectByPrimaryKey(codeMapId);
		return codeMap;
	}
	
	@RequestMapping("/remove/{codeMapId}")
	@ResponseBody
	public HtmlMessage  removeCodeMap(@PathVariable("codeMapId") String codeMapId){
		codeMapService.deleteCodeMap(codeMapId);
		return new HtmlMessage("删除代码集成功").setCallbackType(null);
	}
}