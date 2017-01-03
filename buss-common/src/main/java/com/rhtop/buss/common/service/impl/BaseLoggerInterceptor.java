package com.rhtop.buss.common.service.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhtop.buss.common.entity.SysLogger;
import com.rhtop.buss.common.logger.annotation.Logger;
import com.rhtop.buss.common.utils.DateUtils;


/**
 * 日志拦截器
 * @author mgf
 *
 */
public abstract class BaseLoggerInterceptor extends HandlerInterceptorAdapter{
	private ObjectMapper om = new ObjectMapper();
	protected abstract void writeLogger(SysLogger logger);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof HandlerMethod){
			try{
				HandlerMethod method = (HandlerMethod) handler;
				Logger logger = method.getMethod().getAnnotation(Logger.class);
				if(logger != null){
					String data = "uri=" + request.getRequestURI() 
							+ ",data=" + om.writeValueAsString(request.getParameterMap());
					SysLogger log = new SysLogger();
					log.setTitle(logger.title());
					String user = request.getParameter("userId");
					if(user != null) log.setCreateUser(user);
					log.setCreateTime(DateUtils.getNowTime());
					log.setType(logger.type());
					log.setData(data);
					this.writeLogger(log);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return super.preHandle(request, response, handler);
	}
}
