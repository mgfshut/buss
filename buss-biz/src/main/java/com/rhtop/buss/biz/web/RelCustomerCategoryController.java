package com.rhtop.buss.biz.web;


import java.util.UUID;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.RelCustomerCategory;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/relCustomerCategory")
public class RelCustomerCategoryController  extends BaseController {
	@Autowired
	private RelCustomerCategoryService relCustomerCategoryService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveRelCustomerCategory(@Valid @RequestParam(value = "userId") String userId,@Valid RelCustomerCategory relCustomerCategory){
		if(relCustomerCategory.getRelCustomerCategoryId() == null || "".equals(relCustomerCategory.getRelCustomerCategoryId())){
			String relCustomerCategoryId = UUID.randomUUID().toString().replace("-", "");
			relCustomerCategory.setRelCustomerCategoryId(relCustomerCategoryId);
			relCustomerCategory.setCreateUser(userId);
			relCustomerCategory.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			relCustomerCategory.setUpdateUser(userId);
			relCustomerCategory.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			relCustomerCategoryService.insertRelCustomerCategory(relCustomerCategory);
		}else{
			relCustomerCategory.setUpdateUser(userId);
			relCustomerCategory.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			relCustomerCategoryService.updateRelCustomerCategory(relCustomerCategory);
		}
		return new HtmlMessage(relCustomerCategory);
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteRelCustomerCategory")
	public InfoResult<RelCustomerCategory> deleteRelCustomerCategory(@RequestParam("relCustomerCategoryId") String relCustomerCategoryId){
		InfoResult<RelCustomerCategory> infoResult = new InfoResult<RelCustomerCategory>();
		int num = relCustomerCategoryService.deleteRelCustomerCategory(relCustomerCategoryId);
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
	@RequestMapping("/updateRelCustomerCategory")
	public InfoResult<RelCustomerCategory> updateRelCustomerCategory(RelCustomerCategory relCustomerCategory){
		InfoResult<RelCustomerCategory> infoResult = new InfoResult<RelCustomerCategory>();
		relCustomerCategory.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = relCustomerCategoryService.updateRelCustomerCategory(relCustomerCategory);
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
	public InfoResult<RelCustomerCategory> listPageRelCustomerCategory(Page page,RelCustomerCategory relCustomerCategory){
		InfoResult<RelCustomerCategory> infoResult = new InfoResult<RelCustomerCategory>();
		List<RelCustomerCategory> relCustomerCategoryList = relCustomerCategoryService.listPageRelCustomerCategory(relCustomerCategory);
		infoResult.setCode("200");
		infoResult.setResList(relCustomerCategoryList);
		infoResult.setPage(relCustomerCategory.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<RelCustomerCategory> selectByPrimaryKey(@RequestParam("relCustomerCategoryId") String relCustomerCategoryId) {
		InfoResult<RelCustomerCategory> infoResult = new InfoResult<RelCustomerCategory>();
		infoResult.setCode("200");
		RelCustomerCategory relCustomerCategory = relCustomerCategoryService.selectByPrimaryKey(relCustomerCategoryId);
		infoResult.setResObject(relCustomerCategory);
		return infoResult;
	}
	/**
	 * 品类导出数据查询
	 */
	@ResponseBody
	@RequestMapping(value="/categoryExportList")
	public ResultInfo categoryExportList(@RequestParam("body") String body){
		ObjectMapper mapper = new ObjectMapper();
		Category category = null;
		try{
			category = mapper.readValue(body, Category.class);
		}catch(Exception e){
			log.error("[RelCustomerCategoryController.categoryExportList]数据解析异常", e);
		}
		
		JSONObject jsonObject=JSONObject.fromObject(body);
		ResultInfo resultInfo = new ResultInfo();
		List<RelCustomerCategory> relCustomerCategoryList = relCustomerCategoryService.categoryExportList(category);
		resultInfo.setCode("200");
		resultInfo.setRecords(relCustomerCategoryList);
		return resultInfo;
	}
	
}