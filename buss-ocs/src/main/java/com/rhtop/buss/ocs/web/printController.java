package com.rhtop.buss.ocs.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.utils.Jwt;
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
	@RequestMapping("contractInfo/{contractInfoId}")
	public String contractInfoPrint(HttpServletRequest request, Model model, @PathVariable("contractInfoId") String contractInfoId){
		
		ContractInfo contractInfo = (ContractInfo) rs.invoke("contractInfo-print-" + contractInfoId, "GET", new HashMap(), ContractInfo.class);
		model.addAttribute("contractInfoId", contractInfo.getContractInfoId());
		return "module/sys/contractInfo/print";
	}
//	/**
//	 * app合同打印
//	 * @author mgf
//	 * @date 2017年1月15日 上午10:12:13 
//	 * @param request
//	 * @param model
//	 * @param contractInfoId
//	 * @return
//	 */
//	@RequestMapping("app/contractInfo/{contractInfoId}")
//	public String contractInfoAppPrint(HttpServletRequest request, Model model, @PathVariable("contractInfoId") String contractInfoId){
//		String token = request.getHeader("token");
//		String memberId = request.getHeader("memberId");
//		Map<String,Object> result = Jwt.validToken(memberId,token);
//		if("200".equals(result.get("code").toString())){
//			ContractInfo contractInfo = (ContractInfo) rs.invoke("contractInfo-" + contractInfoId, "GET", new HashMap(), ContractInfo.class);
//			model.addAttribute("contractInfoId", contractInfo.getContractInfoId());
//		}
//		return "module/sys/contractInfo/print";
//	}
}
