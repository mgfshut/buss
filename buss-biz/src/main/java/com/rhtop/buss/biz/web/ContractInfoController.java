package com.rhtop.buss.biz.web;


import java.util.UUID;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.biz.service.ContractInfoService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/contractInfo")
public class ContractInfoController  extends BaseController {
	@Autowired
	private ContractInfoService contractInfoService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveContractInfo(@Valid @RequestParam(value = "userId") String userId,@Valid ContractInfo contractInfo){
		if(contractInfo.getContractInfoId() == null || "".equals(contractInfo.getContractInfoId())){
			String contractInfoId = UUID.randomUUID().toString().replace("-", "");
			contractInfo.setContractInfoId(contractInfoId);
			contractInfo.setCreateUser(userId);
			contractInfo.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			contractInfo.setUpdateUser(userId);
			contractInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			contractInfoService.insertContractInfo(contractInfo);
		}else{
			contractInfo.setUpdateUser(userId);
			contractInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			contractInfoService.updateContractInfo(contractInfo);
		}
		return new HtmlMessage(contractInfo);
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteContractInfo")
	public InfoResult<ContractInfo> deleteContractInfo(@RequestParam("contractInfoId") String contractInfoId){
		InfoResult<ContractInfo> infoResult = new InfoResult<ContractInfo>();
		int num = contractInfoService.deleteContractInfo(contractInfoId);
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
	@RequestMapping("/updateContractInfo")
	public InfoResult<ContractInfo> updateContractInfo(ContractInfo contractInfo){
		InfoResult<ContractInfo> infoResult = new InfoResult<ContractInfo>();
		contractInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = contractInfoService.updateContractInfo(contractInfo);
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
	public InfoResult<ContractInfo> listPageContractInfo(Page page,ContractInfo contractInfo){
		InfoResult<ContractInfo> infoResult = new InfoResult<ContractInfo>();
		contractInfo.setPage(page);
		List<ContractInfo> contractInfoList = contractInfoService.listPageContractInfo(contractInfo);
		infoResult.setCode("200");
		infoResult.setResList(contractInfoList);
		infoResult.setPage(contractInfo.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<ContractInfo> selectByPrimaryKey(@RequestParam("contractInfoId") String contractInfoId) {
		InfoResult<ContractInfo> infoResult = new InfoResult<ContractInfo>();
		infoResult.setCode("200");
		ContractInfo contractInfo = contractInfoService.selectByPrimaryKey(contractInfoId);
		infoResult.setResObject(contractInfo);
		return infoResult;
	}
	
	@RequestMapping("/{contractInfoId}")
	@ResponseBody
	public ContractInfo getByContractInfoId(@PathVariable("contractInfoId") String contractInfoId){
		ContractInfo contractInfo = contractInfoService.selectByPrimaryKey(contractInfoId);
		return contractInfo;
	}
	
	@RequestMapping("/print/{contractInfoId}")
	@ResponseBody
	public ContractInfo printByContractInfoId(@PathVariable("contractInfoId") String contractInfoId){
		ContractInfo contractInfo = contractInfoService.printByContractInfoId(contractInfoId);
		return contractInfo;
	}
	
	@RequestMapping("/app/print")
	@ResponseBody
	public ResultInfo appPrintByContractInfoId(@RequestParam("body") String body){
		JSONObject jsonObject=JSONObject.fromObject(body);
		ContractInfo contractInfo = (ContractInfo)JSONObject.toBean(jsonObject, ContractInfo.class);
		ResultInfo readResult = new ResultInfo();
		ContractInfo cif = contractInfoService.selectByPrimaryKey(contractInfo.getContractInfoId());
		readResult.setCode("200");
		readResult.setResObject(cif);
		return readResult;
	}
	
	/**
     * 行政合同审核
     */
	@ResponseBody
	@RequestMapping("/check")
	public HtmlMessage checkContractInfo(@Valid @RequestParam(value = "userId") String userId,@Valid ContractInfo contractInfo){
		HtmlMessage htmlMessage = new HtmlMessage();
		if(contractInfo.getContractInfoId() == null || "".equals(contractInfo.getContractInfoId())){
			htmlMessage.setStatusCode(HtmlMessage.STATUS_CODE_FAILURE);
			htmlMessage.setMessage("合同ID不能为空");
		}else if(contractInfo.getContUlName() == null || "".equals(contractInfo.getContUlName())){
			htmlMessage.setStatusCode(HtmlMessage.STATUS_CODE_FAILURE);
			htmlMessage.setMessage("请上传合同扫描件");
		}else{
			ContractInfo cif = contractInfoService.selectByPrimaryKey(contractInfo.getContractInfoId());
			cif.setContUlName(contractInfo.getContUlName().substring(0, contractInfo.getContUlName().length()-1));
			cif.setContStatus("02");
			cif.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			cif.setUpdateUser(userId);
			contractInfoService.updateContractInfo(cif);
		}
		htmlMessage.setEntity(contractInfo);
		return htmlMessage;
	}
	/**
	 * 财务合同审核
	 */
	@RequestMapping("/checkPay")
	@ResponseBody
	public HtmlMessage checkPay(@Valid @RequestParam(value="userId")String userId, @Valid ContractInfo contractInfo){
		HtmlMessage htmlMessage = new HtmlMessage();
		if(contractInfo.getContractInfoId() == null || "".equals(contractInfo.getContractInfoId())){
			htmlMessage.setStatusCode(HtmlMessage.STATUS_CODE_FAILURE);
			htmlMessage.setMessage("合同ID不能为空");
		}else if(contractInfo.getContUlName() == null || "".equals(contractInfo.getContUlName())){
			htmlMessage.setStatusCode(HtmlMessage.STATUS_CODE_FAILURE);
			htmlMessage.setMessage("请上传收款确认凭证");
		}else{
			ContractInfo cif = contractInfoService.selectByPrimaryKey(contractInfo.getContractInfoId());
//			cif.setContUlName(contractInfo.getContUlName().substring(0, contractInfo.getContUlName().length()-1));
			cif.setContStatus("40");
			cif.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			cif.setUpdateUser(userId);
			contractInfoService.treasurerCheckContract(cif);
		}
		htmlMessage.setEntity(contractInfo);
		return htmlMessage;
	}
}