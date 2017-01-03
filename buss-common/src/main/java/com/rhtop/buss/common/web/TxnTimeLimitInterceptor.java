package com.rhtop.buss.common.web;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rhtop.buss.common.exception.BusException;
import com.rhtop.buss.common.service.TxnTimeLimitService;


/**
 * 交易时限拦截器
 * @author mgf
 *
 */
public class TxnTimeLimitInterceptor extends HandlerInterceptorAdapter {
	private TxnTimeLimitService service;
	/**
	 * 交易黑名单，当名单内的交易在交易时限之外时会被拦截
	 */
	private Set<String> blackList;		// 正则表达式
	
	private boolean isExclude(String uri){
		for (String regex: blackList) {
			Pattern part = Pattern.compile(regex);
			Matcher matcher = part.matcher(uri);
			if(matcher.find()) return true;
		}
		return false;
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(isExclude(request.getRequestURI())&&!service.isTxnTime()){
			throw new BusException("不能在非交易时间进行此操作");
		}
		
		return super.preHandle(request, response, handler);
	}

	public TxnTimeLimitService getService() {
		return service;
	}

	public void setService(TxnTimeLimitService service) {
		this.service = service;
	}

	public Set<String> getBlackList() {
		return blackList;
	}

	public void setBlackList(Set<String> blackList) {
		this.blackList = blackList;
	}
}
