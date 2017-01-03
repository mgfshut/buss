package com.rhtop.buss.biz.web;


import java.util.UUID;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.SlaTransactionInfo;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.SlaTransactionInfoService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/slaTransactionInfo")
public class SlaTransactionInfoController {
	@Autowired
	private SlaTransactionInfoService slaTransactionInfoService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveSlaTransactionInfo(@Valid @RequestParam(value = "userId") String userId,@Valid SlaTransactionInfo slaTransactionInfo){
		if(slaTransactionInfo.getSlaTransactionInfoId() == null || "".equals(slaTransactionInfo.getSlaTransactionInfoId())){
			String slaTransactionInfoId = UUID.randomUUID().toString().replace("-", "");
			slaTransactionInfo.setSlaTransactionInfoId(slaTransactionInfoId);
			slaTransactionInfo.setCreateUser(userId);
			slaTransactionInfo.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			slaTransactionInfo.setUpdateUser(userId);
			slaTransactionInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			slaTransactionInfoService.insertSlaTransactionInfo(slaTransactionInfo);
		}else{
			slaTransactionInfo.setUpdateUser(userId);
			slaTransactionInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			slaTransactionInfoService.updateSlaTransactionInfo(slaTransactionInfo);
		}
		return new HtmlMessage(slaTransactionInfo);
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteSlaTransactionInfo")
	public InfoResult<SlaTransactionInfo> deleteSlaTransactionInfo(@RequestParam("slaTransactionInfoId") String slaTransactionInfoId){
		InfoResult<SlaTransactionInfo> infoResult = new InfoResult<SlaTransactionInfo>();
		int num = slaTransactionInfoService.deleteSlaTransactionInfo(slaTransactionInfoId);
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
	@RequestMapping("/updateSlaTransactionInfo")
	public InfoResult<SlaTransactionInfo> updateSlaTransactionInfo(SlaTransactionInfo slaTransactionInfo){
		InfoResult<SlaTransactionInfo> infoResult = new InfoResult<SlaTransactionInfo>();
		slaTransactionInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = slaTransactionInfoService.updateSlaTransactionInfo(slaTransactionInfo);
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
	public InfoResult<SlaTransactionInfo> listPageSlaTransactionInfo(Page page,SlaTransactionInfo slaTransactionInfo){
		InfoResult<SlaTransactionInfo> infoResult = new InfoResult<SlaTransactionInfo>();
		slaTransactionInfo.setPage(page);
		List<SlaTransactionInfo> slaTransactionInfoList = slaTransactionInfoService.listPageSlaTransactionInfo(slaTransactionInfo);
		infoResult.setCode("200");
		infoResult.setResList(slaTransactionInfoList);
		infoResult.setPage(slaTransactionInfo.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<SlaTransactionInfo> selectByPrimaryKey(@RequestParam("slaTransactionInfoId") String slaTransactionInfoId) {
		InfoResult<SlaTransactionInfo> infoResult = new InfoResult<SlaTransactionInfo>();
		infoResult.setCode("200");
		SlaTransactionInfo slaTransactionInfo = slaTransactionInfoService.selectByPrimaryKey(slaTransactionInfoId);
		infoResult.setResObject(slaTransactionInfo);
		return infoResult;
	}
}