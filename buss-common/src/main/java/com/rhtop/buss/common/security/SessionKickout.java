package com.rhtop.buss.common.security;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhtop.buss.common.entity.User;


/**
 * 用户session会话管理，控件用户登录数
 * @author 扬
 *
 */
public class SessionKickout{
	private static final Logger log = LoggerFactory.getLogger(SessionKickout.class);
	private static final String KICK_OUT_SESSION_CACHE = "KICK_OUT_SESSION_CACHE";
	private String kickoutUrl;
	private Integer maxSession = 99;
	private Boolean kickoutAfter = false;
	private CacheManager cacheManager;
	private Cache<String,Deque<Serializable>> cache;
	private SessionManager sessionManager;
	public CacheManager getCacheManager() {
		return cacheManager;
	}
	public void setCacheManager(CacheManager cacheManager) {
		cache = cacheManager.getCache(KICK_OUT_SESSION_CACHE);
		this.cacheManager = cacheManager;
	}
	public String getKickoutUrl() {
		return kickoutUrl;
	}
	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}
	public Integer getMaxSession() {
		return maxSession;
	}
	public void setMaxSession(Integer maxSession) {
		this.maxSession = maxSession;
	}
	public Boolean getKickoutAfter() {
		return kickoutAfter;
	}
	public void setKickoutAfter(Boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}
	public Cache<String, Deque<Serializable>> getCache() {
		return cache;
	}
	public void setCache(Cache<String, Deque<Serializable>> cache) {
		this.cache = cache;
	}
	public SessionManager getSessionManager() {
		return sessionManager;
	}
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	protected boolean isAccessAllowed(Subject subject) throws Exception {
	    if(!subject.isAuthenticated() && !subject.isRemembered()) {  
	        //如果没有登录，直接进行之后的流程  
	        return true;  
	    }  
	  
	    Session session = subject.getSession();  
	    User user = (User) subject.getPrincipal();  
	    Serializable sessionId = session.getId();  
	  
	    //TODO 同步控制  
	    Deque<Serializable> deque = cache.get(user.getUserName());
	    if(deque == null) {  
	        deque = new LinkedList<Serializable>();  
	        cache.put(user.getUserName(), deque);  
	    }  
	  
	    //如果队列里没有此sessionId，且用户没有被踢出；放入队列  
	    if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
	        deque.push(sessionId);  
	    }  
	  
	    //如果队列里的sessionId数超出最大会话数，开始踢人 
	    while(deque.size() > maxSession) {  
	        Serializable kickoutSessionId = null;  
	        if(kickoutAfter) { //如果踢出后者  
	            kickoutSessionId = deque.removeFirst();  
	        } else { //否则踢出前者  
	            kickoutSessionId = deque.removeLast();  
	        }  
	        try {  
	            Session kickoutSession =  
	                sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
	            
	            if(kickoutSession != null) {  
	                //设置会话的kickout属性表示踢出了
	                kickoutSession.setAttribute("kickout", true);  
	            }  
	        } catch (Exception e) {//ignore exception  
//	        	e.printStackTrace();
	        }  
	    }  
	  
	    //如果被踢出了，直接退出，重定向到踢出后的地址  
	    if (session.getAttribute("kickout") != null) {  
	        //会话被踢出了  
	        try {
	        	log.info("用户被踢出,sessionid:[{}],userinfo:[{}]",session.getId(),subject.getPrincipal());
	            subject.logout();  
	        } catch (Exception e) { //ignore  
	        }  
	        //WebUtils.issueRedirect(request, response, kickoutUrl);  
	        return false;  
	    }  
	    return true;  
	}
}
