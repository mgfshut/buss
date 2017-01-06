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
@RequestMapping("/sys/code")
public class CodeController extends BaseController {
	@Autowired
	private RestService rs; 
	
	@SuppressWarnings("unchecked")
	@RequestMapping("pager")
	public String codeMapPager(HttpServletRequest request, Model model){
		Map<String,String[]> param = Maps.newHashMap(request.getParameterMap());
		Map<String,Object> map = rs.invoke("codeMap-pager", param);
		model.addAllAttributes(map);
		return "module/sys/codeMap/codemap-pager";
	}
	
	@RequestMapping("form")
	public String codeMapForm(HttpServletRequest request, Model model){
		return "module/sys/codeMap/codemap-form";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("form/{codeMapId}")
	public String codeMapModForm(HttpServletRequest request, Model model, @PathVariable("codeMapId") String codeMapId){
		model.addAllAttributes(rs.invoke("codeMap-" + codeMapId, new HashMap()));
		return "module/sys/codeMap/codemap-form";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("item/pager")
	public String codeItemPager(HttpServletRequest request, Model model){
		Map<String,String[]> param = Maps.newHashMap(request.getParameterMap());
		Map<String,Object> map = rs.invoke("codeValue-pager", param);
		model.addAllAttributes(map);
		return "module/sys/codeMap/codeitem-pager";
	}
	
	@RequestMapping("item/form")
	public String codeItemForm(HttpServletRequest request, Model model){
		return "module/sys/codeMap/codeitem-form";
	}
	
	@RequestMapping("item/form/{id}")
	public String codeItemModForm(HttpServletRequest request, Model model, @PathVariable("id") String id){
		model.addAllAttributes(rs.invoke("codeValue-" + id, new HashMap()));
		return "module/sys/codeMap/codeitem-form";
	}
}
