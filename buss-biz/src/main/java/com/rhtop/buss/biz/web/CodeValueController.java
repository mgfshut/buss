package com.rhtop.buss.biz.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.rhtop.buss.biz.service.CodeMapService;
import com.rhtop.buss.biz.service.CodeValueService;
import com.rhtop.buss.common.entity.CodeMap;
import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.RelCustomerCategory;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.exception.BusException;
import com.rhtop.buss.common.model.ListData;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/codeValue")
public class CodeValueController  extends BaseController {
	@Autowired
	private CodeValueService codeValueService;
	@Autowired
	private CodeMapService codeMapService;
	
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
	
	@RequestMapping("/save")
	@ResponseBody
	public HtmlMessage save(@Valid @RequestParam(value = "userId") String userId,@Valid CodeValue codeValue){
		if(codeValue.getCodeValueId() == null || "".equals(codeValue.getCodeValueId())){
			CodeMap cm = new CodeMap();
			if(codeValue.getCode() == null ||"".equals(codeValue.getCode())){
				throw new BusException("代码集编码没有传值！");
			}
			cm.setCode(codeValue.getCode());
			CodeMap codeMap = codeMapService.listCodeMaps(cm).get(0);
			codeValue.setCodeMapId(codeMap.getCodeMapId());
			String codeValueId = UUID.randomUUID().toString().replace("-", "");
			codeValue.setCodeValueId(codeValueId);
			codeValue.setCreateUser(userId);
			codeValue.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			codeValue.setUpdateUser(userId);
			codeValue.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			codeValueService.insertCodeValue(codeValue);
		}else{
			codeValue.setUpdateUser(userId);
			codeValue.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			codeValueService.updateCodeValue(codeValue);
		}
		return new HtmlMessage(codeValue);
	}
	
	@RequestMapping("/{codeValueId}")
	@ResponseBody
	public CodeValue getByCodeValueId(@PathVariable("codeValueId") String codeValueId){
		CodeValue codeValue = codeValueService.selectByPrimaryKey(codeValueId);
		return codeValue;
	}
	
	@RequestMapping("/remove/{codeValueId}")
	@ResponseBody
	public HtmlMessage  removeCodeValue(@PathVariable("codeValueId") String codeValueId){
		codeValueService.deleteCodeValue(codeValueId);
		return new HtmlMessage("删除代码值成功").setCallbackType(null);
	}
	
	/**
	 * 字典信息导出数据查询
	 */
	@ResponseBody
	@RequestMapping(value="/exportList")
	public ResultInfo exportList(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		String codeValue = null;
		List<String> codeList = new ArrayList<String>();
		ResultInfo resultInfo = new ResultInfo();
		List<CodeValue> rList = new ArrayList<CodeValue>();
		try{
			Map rJson = mapper.readValue(body, Map.class);
			codeValue = (String)rJson.get("codeValue");
			String[] args = codeValue.split(",");
			for (int i=0; i<args.length; i++){
				codeList.add(args[i]);
			}
			
			rList = codeValueService.listCodeValuesByCodes(codeList);
		}catch(Exception e){
			log.error("[RelCustomerCategoryController.exportList]数据解析异常", e);
		}
		
		resultInfo.setCode("200");
		resultInfo.setRecords(rList);
		return resultInfo;
	}
}