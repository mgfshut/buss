package com.rhtop.buss.common.web;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.exception.BusException;
import com.rhtop.buss.common.service.RestService;


@Controller
@RequestMapping("")
public class CommonController extends BaseController {
	private static final String CODEMAP_CACHE = "CODEMAP_CACHE";
	private Cache codeCache = new ConcurrentMapCache(CODEMAP_CACHE);
	private CacheManager cacheManager;
	public final static String OPERATION_VIEW = "view";
	
	@Autowired(required=false)
	private RestService rs; 
	
	@Autowired(required=false)
	private Producer captchaProducer = null;
	
	@RequestMapping("/module/{module}")
	public String subModule(HttpServletRequest request,@PathVariable("module") String module){
		return "module/" + module.replace('-', '/');
	}
//	@RequestMapping("//{module}")
//	public void serviceFile(HttpServletRequest request,@PathVariable("module") String module){
//	
//	}
	@RequestMapping("/module/{module}/{service}")
	public String serviceModule(HttpServletRequest request,@PathVariable("module")String module,@PathVariable("service")String service,
			Model model){
		Map<String,String[]> param = Maps.newHashMap(request.getParameterMap());
//		if(!param.containsKey("numPerPage") && request.getSession().getAttribute("numPerPage") != null){
//			param.put("numPerPage", new String[]{(String) request.getSession().getAttribute("numPerPage")});
//		}
	
		//附加session用户ID
		if(request.getSession().getAttribute("User") != null){
			param.put("userId", new String[]{((User)request.getSession().getAttribute("User")).getUserId()} );
		}
		
		Map<String,Object> map = rs.invoke(service, param);
		if(map.containsKey("type") && HtmlMessage.class.toString().equals(map.get("type"))){
			throw new BusException((String)map.get("message"),(String)map.get("statusCode"));
		}
		model.addAllAttributes(map);
		return "module/" + module.replace('-', '/');
	}
	@RequestMapping("/list/service/{service}")
	@ResponseBody
	public List<Map<String, Object>> queryList(WebRequest request,@PathVariable("service")String service,
			Model model){
		List<Map<String, Object>> list = (List<Map<String, Object>>) rs.invoke(service, "POST", request.getParameterMap(), 
				TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Map.class));
		return list;
	}
	
	@RequestMapping("/codemapClean")
	@ResponseBody
	public HtmlMessage codemapClean(){
		try{
			codeCache.clear();
		}catch(Exception e){
			throw new BusException("缓存更新异常", "300");
		}
		return new HtmlMessage("200", "缓存更新成功");
	}
	
	@RequestMapping("/ui/codemapSelect/{codemap}")
	public String codemapSelect(WebRequest request,@PathVariable("codemap") String codemap,	Model model){
		try{
			List<Map<String,String>> result;
			ValueWrapper vw = codeCache.get(codemap);
			LinkedHashMap<String,String> itemMap;
			if(vw == null){
				Map<String,Object> map = rs.invoke("code-" + codemap + "-items", request.getParameterMap());
				itemMap = Maps.newLinkedHashMap();
				for (Map<String,String> im : ((List<Map<String,String>>)map.get("list"))) {
					itemMap.put(im.get("code"), im.get("codeitemname"));
				}
				codeCache.put(codemap, itemMap);
				result = (List<Map<String, String>>) map.get("list");
			}
			else{
				itemMap = (LinkedHashMap<String, String>) vw.get();
				result = Lists.newArrayList();
				for (Entry<String, String> entry : itemMap.entrySet()) {
					Map<String,String> map = Maps.newHashMap();
					map.put("name", entry.getValue());
					map.put("code", entry.getKey());
					result.add(map);
				}
			}
			model.addAttribute("list",result);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "module/ui/select";
	}
	
	@RequestMapping("/ui/codemapSelect2/{codemap}")
	public String codemapSelect2(WebRequest request,@PathVariable("codemap") String codemap,	Model model){
		try{
			List<Map<String,String>> result;
			ValueWrapper vw = codeCache.get(codemap);
			LinkedHashMap<String,String> itemMap;
			if(vw == null){
				Map<String,Object> map = rs.invoke("code-" + codemap + "-items", request.getParameterMap());
				itemMap = Maps.newLinkedHashMap();
				for (Map<String,String> im : ((List<Map<String,String>>)map.get("list"))) {
					itemMap.put(im.get("code"), im.get("codeitemname"));
				}
				codeCache.put(codemap, itemMap);
				result = (List<Map<String, String>>) map.get("list");
			}
			else{
				itemMap = (LinkedHashMap<String, String>) vw.get();
				result = Lists.newArrayList();
				for (Entry<String, String> entry : itemMap.entrySet()) {
					Map<String,String> map = Maps.newHashMap();
					map.put("codeitemname", entry.getValue());
					map.put("code", entry.getKey());
					result.add(map);
				}
			}
			model.addAttribute("list",result);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "module/ui/select2";
	}
	
	@RequestMapping("/ui/serviceSelect/{service}")
	public String serviceSelect(WebRequest request,@PathVariable("service")String service,
			Model model){
		Map<String,Object> map = rs.invoke(service, request.getParameterMap());
		model.addAllAttributes(map);
		return "module/ui/select";
	}
	@RequestMapping("/convert/codeValue/{codemap}-{codeitem}")
	public String convertText(WebRequest request,
			@PathVariable("codemap") String codemap,@PathVariable("codeitem") String codeitem,
			Model model){
		try{
			ValueWrapper vw = codeCache.get(codemap);
			LinkedHashMap<String,String> itemMap;
			if(vw == null){
				Map<String,Object> map = rs.invoke("codeValue-" + codemap + "-items", request.getParameterMap());
				itemMap = Maps.newLinkedHashMap();
				for (Map<String,String> im : ((List<Map<String,String>>)map.get("list"))) {
					itemMap.put(im.get("codeValue"), im.get("codeValueDescribe"));
				}
				codeCache.put(codemap, itemMap);
			}else{
				itemMap = (LinkedHashMap<String, String>) vw.get();
			}
			
			model.addAttribute("codeValueDescribe",itemMap.get(codeitem));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "module/ui/convert";
	}
	@RequestMapping("/unauthorized")
	public String unauthorized(){
		return "error/unauthorized";
	}
	@RequestMapping("/Kaptcha.jpg")  
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {  
  
        response.setDateHeader("Expires", 0);  
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");  
        // return a jpeg  
        response.setContentType("image/jpeg");  
        // create the text for the image  
        String capText = captchaProducer.createText();  
        // store the text in the session  
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // create the image with the text  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        // write the data out  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    }
	public CacheManager getCacheManager() {
		return cacheManager;
	}
	@Autowired(required=false)
	public void setCacheManager(CacheManager cacheManager) {
		this.codeCache = cacheManager.getCache(CODEMAP_CACHE);
		this.cacheManager = cacheManager;
	}  
	
}
