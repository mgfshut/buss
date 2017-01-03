package com.rhtop.buss.biz.web;


import java.util.UUID;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.BusinessDiary;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.BusinessDiaryService;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/businessDiary")
public class BusinessDiaryController {
	@Autowired
	private BusinessDiaryService businessDiaryService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveBusinessDiary(@Valid @RequestParam(value = "userId") String userId,@Valid BusinessDiary businessDiary){
		if(businessDiary.getBusinessDiaryId() == null || "".equals(businessDiary.getBusinessDiaryId())){
			String businessDiaryId = UUID.randomUUID().toString().replace("-", "");
			businessDiary.setBusinessDiaryId(businessDiaryId);
			businessDiaryService.insertBusinessDiary(businessDiary);
		}else{
			businessDiaryService.updateBusinessDiary(businessDiary);
		}
		return new HtmlMessage(businessDiary);
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteBusinessDiary")
	public InfoResult<BusinessDiary> deleteBusinessDiary(@RequestParam("businessDiaryId") String businessDiaryId){
		InfoResult<BusinessDiary> infoResult = new InfoResult<BusinessDiary>();
		int num = businessDiaryService.deleteBusinessDiary(businessDiaryId);
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
	@RequestMapping("/updateBusinessDiary")
	public InfoResult<BusinessDiary> updateBusinessDiary(BusinessDiary businessDiary){
		InfoResult<BusinessDiary> infoResult = new InfoResult<BusinessDiary>();
		int num = businessDiaryService.updateBusinessDiary(businessDiary);
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
	public InfoResult<BusinessDiary> listPageBusinessDiary(Page page,BusinessDiary businessDiary){
		InfoResult<BusinessDiary> infoResult = new InfoResult<BusinessDiary>();
		businessDiary.setPage(page);
		List<BusinessDiary> businessDiaryList = businessDiaryService.listPageBusinessDiary(businessDiary);
		infoResult.setCode("200");
		infoResult.setResList(businessDiaryList);
		infoResult.setPage(businessDiary.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<BusinessDiary> selectByPrimaryKey(@RequestParam("businessDiaryId") String businessDiaryId) {
		InfoResult<BusinessDiary> infoResult = new InfoResult<BusinessDiary>();
		infoResult.setCode("200");
		BusinessDiary businessDiary = businessDiaryService.selectByPrimaryKey(businessDiaryId);
		infoResult.setResObject(businessDiary);
		return infoResult;
	}
}