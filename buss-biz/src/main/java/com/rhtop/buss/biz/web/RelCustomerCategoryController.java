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
import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.common.entity.RelCustomerCategory;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.biz.service.CodeValueService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/relCustomerCategory")
public class RelCustomerCategoryController  extends BaseController {
	@Autowired
	private RelCustomerCategoryService relCustomerCategoryService;
	@Autowired
	private CodeValueService codeValueService;
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
		
		ResultInfo resultInfo = new ResultInfo();
		List<RelCustomerCategory> relCustomerCategoryList = relCustomerCategoryService.categoryExportList(category);
		//查询规格字典
		List<CodeValue> cateNameList = this.codeValueService.listCodeValuesByCode("cateStan");
		//查询渠道字典
		List<CodeValue> cusChaList = this.codeValueService.listCodeValuesByCode("cusCha");
		//查询产地字典
		List<CodeValue> prodPlaList = this.codeValueService.listCodeValuesByCode("prodPla");
		for (int i=0; i<relCustomerCategoryList.size(); i++){
			RelCustomerCategory rel = relCustomerCategoryList.get(i);
			if (cateNameList != null && cateNameList.size() > 0){
				for (int c=0; c<cateNameList.size(); c++){
					CodeValue item = cateNameList.get(c);
					if (item.getCodeValue().equals(rel.getCateStan())){
						rel.setCateStan(item.getCodeValueDescribe());
						break;
					}
				}
			}
			if (cusChaList != null && cusChaList.size() > 0){
				for (int c=0; c<cusChaList.size(); c++){
					CodeValue item = cusChaList.get(c);
					if (item.getCodeValue().equals(rel.getCusChaId())){
						rel.setCusChaVal(item.getCodeValueDescribe());
						break;
					}
				}
			}
			if (prodPlaList != null && prodPlaList.size() > 0){
				for (int c=0; c<prodPlaList.size(); c++){
					CodeValue item = prodPlaList.get(c);
					if (item.getCodeValue().equals(rel.getProdPla())){
						rel.setProdPla(item.getCodeValueDescribe());
						break;
					}
				}
			}
		}
		
		resultInfo.setCode("200");
		resultInfo.setRecords(relCustomerCategoryList);
		return resultInfo;
	}
	
}