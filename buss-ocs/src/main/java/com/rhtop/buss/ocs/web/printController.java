package com.rhtop.buss.ocs.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.web.BaseController;


@Controller
@RequestMapping("/sys/print")
public class printController extends BaseController {
	@Autowired
	private RestService rs; 
	
	/**
	 * 合同打印
	 * @author mgf
	 * @date 2017年1月15日 上午10:12:10 
	 * @param request
	 * @param model
	 * @param contractInfoId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("contractInfo/{contractInfoId}")
	public String contractInfoPrint(HttpServletRequest request, Model model, @PathVariable("contractInfoId") String contractInfoId){
		ContractInfo contractInfo = (ContractInfo) rs.invoke("contractInfo-print-" + contractInfoId, "GET", new HashMap(), ContractInfo.class);
		model.addAttribute("contractInfoId", contractInfo.getContractInfoId());
		model.addAttribute("buyName", contractInfo.getBuyName());
		model.addAttribute("legalPer", contractInfo.getLegalPer());
		model.addAttribute("creditCode", contractInfo.getCreditCode());
		model.addAttribute("entAddr", contractInfo.getEntAddr());
		model.addAttribute("entTel", contractInfo.getEntTel());
		model.addAttribute("manuNum", contractInfo.getManuNum());
		model.addAttribute("cateName", contractInfo.getCateName());
		model.addAttribute("comm", contractInfo.getComm());
		model.addAttribute("cateStan", contractInfo.getCateStan());
		model.addAttribute("offerPri", contractInfo.getOfferPri());
		model.addAttribute("txAmo", contractInfo.getTxAmo());
		model.addAttribute("totPri", contractInfo.getTotPri());
		model.addAttribute("csgName", contractInfo.getCsgName());
		model.addAttribute("csgTel", contractInfo.getCsgTel());
		model.addAttribute("csgAddr", contractInfo.getCsgAddr());
		model.addAttribute("csgId", contractInfo.getCsgId());
		model.addAttribute("ztcsgName", contractInfo.getZtcsgName());
		model.addAttribute("ztcsgTel", contractInfo.getZtcsgTel());
		model.addAttribute("carNum", contractInfo.getCarNum());
		model.addAttribute("driNum", contractInfo.getDriNum());
		model.addAttribute("ztcsgId", contractInfo.getZtcsgId());
		model.addAttribute("execName", contractInfo.getExecName());
		model.addAttribute("execTel", contractInfo.getExecTel());
		model.addAttribute("execAddr", contractInfo.getExecAddr());
		return "module/sys/contractInfo/print";
	}
	/**
	 * app合同打印
	 * @author mgf
	 * @date 2017年1月15日 上午10:12:10 
	 * @param request
	 * @param model
	 * @param contractInfoId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("app/contractInfo/{contractInfoId}")
	public String contractInfoAppPrint(HttpServletRequest request, Model model, @PathVariable("contractInfoId") String contractInfoId){
		ContractInfo contractInfo = (ContractInfo) rs.invoke("contractInfo-print-" + contractInfoId, "GET", new HashMap(), ContractInfo.class);
		model.addAttribute("contractInfoId", contractInfo.getContractInfoId());
		model.addAttribute("buyName", contractInfo.getBuyName());
		model.addAttribute("legalPer", contractInfo.getLegalPer());
		model.addAttribute("creditCode", contractInfo.getCreditCode());
		model.addAttribute("entAddr", contractInfo.getEntAddr());
		model.addAttribute("entTel", contractInfo.getEntTel());
		model.addAttribute("manuNum", contractInfo.getManuNum());
		model.addAttribute("cateName", contractInfo.getCateName());
		model.addAttribute("comm", contractInfo.getComm());
		model.addAttribute("cateStan", contractInfo.getCateStan());
		model.addAttribute("offerPri", contractInfo.getOfferPri());
		model.addAttribute("txAmo", contractInfo.getTxAmo());
		model.addAttribute("totPri", contractInfo.getTotPri());
		model.addAttribute("csgName", contractInfo.getCsgName());
		model.addAttribute("csgTel", contractInfo.getCsgTel());
		model.addAttribute("csgAddr", contractInfo.getCsgAddr());
		model.addAttribute("csgId", contractInfo.getCsgId());
		model.addAttribute("ztcsgName", contractInfo.getZtcsgName());
		model.addAttribute("ztcsgTel", contractInfo.getZtcsgTel());
		model.addAttribute("carNum", contractInfo.getCarNum());
		model.addAttribute("driNum", contractInfo.getDriNum());
		model.addAttribute("ztcsgId", contractInfo.getZtcsgId());
		model.addAttribute("execName", contractInfo.getExecName());
		model.addAttribute("execTel", contractInfo.getExecTel());
		model.addAttribute("execAddr", contractInfo.getExecAddr());
		return "module/sys/contractInfo/appPrint";
	}
}
