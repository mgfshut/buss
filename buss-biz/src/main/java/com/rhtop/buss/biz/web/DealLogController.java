package com.rhtop.buss.biz.web;


import java.util.UUID;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.DealLog;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.DealLogService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/dealLog")
public class DealLogController {
	@Autowired
	private DealLogService dealLogService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveDealLog(@Valid @RequestParam(value = "userId") String userId,@Valid DealLog dealLog){
		if(dealLog.getDealLogId() == null || "".equals(dealLog.getDealLogId())){
			String dealLogId = UUID.randomUUID().toString().replace("-", "");
			dealLog.setDealLogId(dealLogId);
			dealLogService.insertDealLog(dealLog);
		}else{
			dealLogService.updateDealLog(dealLog);
		}
		return new HtmlMessage(dealLog);
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteDealLog")
	public InfoResult<DealLog> deleteDealLog(@RequestParam("dealLogId") String dealLogId){
		InfoResult<DealLog> infoResult = new InfoResult<DealLog>();
		int num = dealLogService.deleteDealLog(dealLogId);
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
	@RequestMapping("/updateDealLog")
	public InfoResult<DealLog> updateDealLog(DealLog dealLog){
		InfoResult<DealLog> infoResult = new InfoResult<DealLog>();
		int num = dealLogService.updateDealLog(dealLog);
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
	public InfoResult<DealLog> listPageDealLog(Page page,DealLog dealLog){
		InfoResult<DealLog> infoResult = new InfoResult<DealLog>();
		List<DealLog> dealLogList = dealLogService.listPageDealLog(dealLog);
		infoResult.setCode("200");
		infoResult.setResList(dealLogList);
		infoResult.setPage(dealLog.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<DealLog> selectByPrimaryKey(@RequestParam("dealLogId") String dealLogId) {
		InfoResult<DealLog> infoResult = new InfoResult<DealLog>();
		infoResult.setCode("200");
		DealLog dealLog = dealLogService.selectByPrimaryKey(dealLogId);
		infoResult.setResObject(dealLog);
		return infoResult;
	}
}