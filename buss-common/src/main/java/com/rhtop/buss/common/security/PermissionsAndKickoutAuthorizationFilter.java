package com.rhtop.buss.common.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;


public class PermissionsAndKickoutAuthorizationFilter extends
		PermissionsAuthorizationFilter {
	private SessionKickout kickout = null;
	@Autowired
	private DefaultFilterFactoryBean filterFactoryBean;
	
	@Override
	public boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws IOException {
		try {
			filterFactoryBean.reloadPermission(false);
			if(kickout != null && !kickout.isAccessAllowed(getSubject(request, response))) return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.isAccessAllowed(request, response, mappedValue);
	}
	public SessionKickout getKickout() {
		return kickout;
	}
	public void setKickout(SessionKickout kickout) {
		this.kickout = kickout;
	}
}
