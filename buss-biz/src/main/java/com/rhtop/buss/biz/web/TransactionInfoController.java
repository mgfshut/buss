package com.rhtop.buss.biz.web;


import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.TransactionInfoService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/transactionInfo")
public class TransactionInfoController  extends BaseController {
	@Autowired
	private TransactionInfoService transactionInfoService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveTransactionInfo(@Valid @RequestParam(value = "userId") String userId,@Valid TransactionInfo transactionInfo){
		if(transactionInfo.getTransactionInfoId() == null || "".equals(transactionInfo.getTransactionInfoId())){
			String transactionInfoId = UUID.randomUUID().toString().replace("-", "");
			transactionInfo.setTransactionInfoId(transactionInfoId);
			transactionInfo.setCreateUser(userId);
			transactionInfo.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			transactionInfo.setUpdateUser(userId);
			transactionInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			transactionInfoService.insertTransactionInfo(transactionInfo);
		}else{
			transactionInfo.setUpdateUser(userId);
			transactionInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			transactionInfoService.updateTransactionInfo(transactionInfo);
		}
		return new HtmlMessage(transactionInfo);
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteTransactionInfo")
	public InfoResult<TransactionInfo> deleteTransactionInfo(@RequestParam("transactionInfoId") String transactionInfoId){
		InfoResult<TransactionInfo> infoResult = new InfoResult<TransactionInfo>();
		int num = transactionInfoService.deleteTransactionInfo(transactionInfoId);
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
	@RequestMapping("/updateTransactionInfo")
	public InfoResult<TransactionInfo> updateTransactionInfo(TransactionInfo transactionInfo){
		InfoResult<TransactionInfo> infoResult = new InfoResult<TransactionInfo>();
		transactionInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = transactionInfoService.updateTransactionInfo(transactionInfo);
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
	public InfoResult<TransactionInfo> listPageTransactionInfo(Page page,TransactionInfo transactionInfo){
		InfoResult<TransactionInfo> infoResult = new InfoResult<TransactionInfo>();
		transactionInfo.setPage(page);
		List<TransactionInfo> transactionInfoList = transactionInfoService.listPageTransactionInfo(transactionInfo);
		infoResult.setCode("200");
		infoResult.setResList(transactionInfoList);
		infoResult.setPage(transactionInfo.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<TransactionInfo> selectByPrimaryKey(@RequestParam("transactionInfoId") String transactionInfoId) {
		InfoResult<TransactionInfo> infoResult = new InfoResult<TransactionInfo>();
		infoResult.setCode("200");
		TransactionInfo transactionInfo = transactionInfoService.selectByPrimaryKey(transactionInfoId);
		infoResult.setResObject(transactionInfo);
		return infoResult;
	}
	
	/**
	 * 国际人员 pc
	 * 回盘信息 
	 * @param 
	 * @return
	 */
	@RequestMapping("/ctofPrice")
	@ResponseBody
	public InfoResult<List<TransactionInfo>> getCtofPrice(Page page,TransactionInfo transactioninfo ){
		InfoResult<List<TransactionInfo>> infoResult = new InfoResult<List<TransactionInfo>>();
		List<TransactionInfo> Tranlist = transactionInfoService.listPageInfo(transactioninfo);
		infoResult.setPage(page);
		infoResult.setCode("200");
		infoResult.setResObject(Tranlist);
		infoResult.setPage(transactioninfo.getPage());
		return infoResult;
		}
}