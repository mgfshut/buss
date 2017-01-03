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

import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.model.ListData;
import com.rhtop.buss.biz.service.CodeValueService;
import com.rhtop.buss.common.utils.DateUtils;

@Controller
@RequestMapping("service/codeValue")
public class CodeValueController {
	@Autowired
	private CodeValueService codeValueService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/insertCodeValue")
	public InfoResult<CodeValue> insertCodeValue(@RequestBody CodeValue codeValue){
		InfoResult<CodeValue> infoResult = new InfoResult<CodeValue>();
		codeValue.setCodeValueId(UUID.randomUUID().toString());
		codeValue.setCreateUser(codeValue.getUpdateUser());
		codeValue.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		codeValue.setUpdateUser(codeValue.getUpdateUser());
		codeValue.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = codeValueService.insertCodeValue(codeValue);
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
	@RequestMapping("/deleteCodeValue")
	public InfoResult<CodeValue> deleteCodeValue(@RequestParam("codeValueId") String codeValueId){
		InfoResult<CodeValue> infoResult = new InfoResult<CodeValue>();
		int num = codeValueService.deleteCodeValue(codeValueId);
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
	@RequestMapping("/updateCodeValue")
	public InfoResult<CodeValue> updateCodeValue(@RequestBody CodeValue codeValue){
		InfoResult<CodeValue> infoResult = new InfoResult<CodeValue>();
		codeValue.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = codeValueService.updateCodeValue(codeValue);
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
	public InfoResult<CodeValue> listPageCodeValue(Page page,CodeValue codeValue){
		InfoResult<CodeValue> infoResult = new InfoResult<CodeValue>();
		codeValue.setPage(page);
		List<CodeValue> codeValueList = codeValueService.listPageCodeValue(codeValue);
		infoResult.setCode("200");
		infoResult.setResList(codeValueList);
		infoResult.setPage(codeValue.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<CodeValue> selectByPrimaryKey(@RequestParam("codeValueId") String codeValueId) {
		InfoResult<CodeValue> infoResult = new InfoResult<CodeValue>();
		infoResult.setCode("200");
		CodeValue codeValue = codeValueService.selectByPrimaryKey(codeValueId);
		infoResult.setResObject(codeValue);
		return infoResult;
	}
	
	@RequestMapping("/{code}/items")
	@ResponseBody
	public ListData<CodeValue> codeItems(@PathVariable("code") String code){
		return new ListData<>(codeValueService.listCodeValuesByCode(code));
	}
}