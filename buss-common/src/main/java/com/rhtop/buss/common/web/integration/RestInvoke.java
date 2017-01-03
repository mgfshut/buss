package com.rhtop.buss.common.web.integration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.google.common.collect.Lists;
import com.rhtop.buss.common.entity.User;



public class RestInvoke {
	private final static Logger log = LoggerFactory.getLogger(RestInvoke.class);
	/**
	 * 不记录日志的请求
	 */
	private Set<String> notLogService = new HashSet<>();
	public Message<?> invoke(Message<Map<String, List<String>>> message){
		MessageBuilder<Map<String, List<String>>> mb =  MessageBuilder.fromMessage(message).copyHeaders(message.getHeaders());
		mb.copyHeaders(message.getHeaders());
		String s = (String) message.getHeaders().get("http_requestUrl");
		String serviceName = (String) message.getHeaders().get("service");
		try {
			serviceName = URLEncoder.encode(serviceName,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String uri = serviceName.replace('-', '/');
		if(s.indexOf('?') >= 0){
			uri += s.substring(s.indexOf('?'));
		}
		mb.setHeader("http_requestUri", uri);
		Message<Map<String, List<String>>> result = mb.build();
		
		
		// 登录传递用户ID到后台
		try{
			
			if(SecurityUtils.getSubject().isAuthenticated()){
				result.getPayload().put("userId", Lists.newArrayList(((User)SecurityUtils.getSubject().getPrincipal()).getUserId().toString()));
			//	result.getPayload().put("loginCompanyId", Lists.newArrayList(((User)SecurityUtils.getSubject().getPrincipal()).getCompanyId().toString()));
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
		
		/*try{
			HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			if(request != null){
				Object obj = request.getSession().getAttribute("reUser");
				if(obj != null) {
					result.getPayload().put("loginMemberId", Lists.newArrayList(((ReUser)obj).getId().toString()));
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
		}*/
		
		if(!this.notLogService.contains(serviceName)){
			log.info("调用服务[{}],payload[{}]={}",result.getHeaders().get("service"),
					result.getPayload().getClass(),
					result.getPayload());
		}
		return result;
	}
	public Set<String> getNotLogService() {
		return notLogService;
	}
	public void setNotLogService(Set<String> notLogService) {
		this.notLogService = notLogService;
	}
}
