package com.rhtop.buss.common.web;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Strings;


/**
 * 记录日志拦截日志
 * @author zhouyang
 *
 */
public class AccessLoggerInterceptor extends HandlerInterceptorAdapter{
	private Logger logger = LoggerFactory.getLogger("access");
	private Set<String> exclude = new HashSet<String>();
//	@Autowired
//	private MenuManager mm;
	private boolean isExclude(String uri){
		for (String regex: exclude) {
			Pattern part = Pattern.compile(regex);
			Matcher matcher = part.matcher(uri);
			if(matcher.find()) return true;
		}
		return false;
	}
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TOMCAT7 异步支持
		//request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
		if(request.getSession() != null){
			if(!Strings.isNullOrEmpty(request.getParameter("numPerPage")))
				request.getSession().setAttribute("numPerPage", request.getParameter("numPerPage"));
		}
		if(!isExclude(request.getRequestURI())){
			request.setAttribute("__startTime", new Date());
			request.setAttribute("__baseUrl", request.getContextPath());
			/*
			try{
				if(SecurityUtils.getSubject().isAuthenticated()){
					// 菜单处理
					Menu menu = mm.findByUrl(request.getRequestURI());
					if(menu != null){
						request.setAttribute("__menuid", menu.getId());
						request.setAttribute("__rootid", menu.getId().substring(0,2));
						request.setAttribute("__curMenu", menu);
						//List<Menu> subMenus = mm.findMenu(1, menu.getId().substring(0,2));
						//request.setAttribute("__submenu", subMenus);
					}
					request.setAttribute("__allmenuhtml", MenuBuilder.buildLeftMenu(mm.findAll(),"mainMenu",
							menu!=null?menu.getId():"",request.getContextPath()));
				}
			}catch(UnavailableSecurityManagerException e){
				
			}
				*/
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(!isExclude(request.getRequestURI())){
			Date now = new Date();
			super.postHandle(request, response, handler, modelAndView);
			Date start = (Date) request.getAttribute("__startTime");
			long span = now.getTime() - start.getTime();
			logger.info("path={}耗时{}", request.getRequestURI(), span);
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	public Set<String> getExclude() {
		return exclude;
	}

	public void setExclude(Set<String> exclude) {
		this.exclude = exclude;
	}

}
