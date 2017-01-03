package com.rhtop.buss.common.security;

import java.util.Map;

import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;



import com.google.common.collect.Maps;
import com.rhtop.buss.common.entity.User;

public class UserRealm extends AuthorizingRealm{
	private Map<Object,Account> accounts = Maps.newHashMap();
	private UserService userService;
	@Override
	/**
	 * 获取用户角色权限
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection user) {
		return userService.findUser(((User)user.getPrimaryPrincipal()).getUserName());
	}

	@Override
	/**
	 * 获取用户信息
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken user) throws AuthenticationException {
		UserLoginToken token = (UserLoginToken) user;
		if(!token.isVerifitionCodePass()) throw new VaildCodeAuthException();
		if(!token.isMobileCodePass()) throw new VaildMobileAuthException();
		Account account = userService.findUser(token.getUsername());
		accounts.put(account.getPrincipals().getPrimaryPrincipal(), account);
		return account;
	}
	
	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		return ((User)principals.getPrimaryPrincipal()).getUserName();
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
