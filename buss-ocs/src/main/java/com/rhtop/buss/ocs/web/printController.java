package com.rhtop.buss.ocs.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.web.BaseController;


@Controller
@RequestMapping("/sys/print")
public class printController extends BaseController {
	@Autowired
	private RestService rs; 
	
	@RequestMapping("contractInfo/{contractInfoId}")
	public String codeMapModForm(HttpServletRequest request, Model model, @PathVariable("contractInfoId") String contractInfoId){
//		model.addAllAttributes(rs.invoke("codeMap-" + codeMapId, new HashMap()));
		return "module/sys/contractInfo/print";
	}
}
