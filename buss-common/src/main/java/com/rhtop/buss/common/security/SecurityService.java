package com.rhtop.buss.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.SimpleAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.entity.Module;
import com.rhtop.buss.common.service.RestService;

public class SecurityService implements UserService,UrlPermissionLoader{
	@Autowired(required=false)
	@Qualifier("restService")
	private RestService service;
	@Override
	public Account findUser(String loginName) {
		User user = (User) service.invoke("user-login-" + loginName, "GET", new HashMap(), User.class);
		SimpleAccount account = new SimpleAccount(user, user.getUserPassword(), "userRealm");
//		for (Role ur : user.getUserRoles()) {
//			account.addRole(ur.getRole().getCode());
//			ur.getRole().getPermissionList();
//			account.addStringPermissions(ur.getRole().getPermissionList());
//		}
		account.addStringPermissions(user.getPermissionList());
		return account;

	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getAllUrlPermission() {
		List<Module> modules = (List<Module>) service.invoke("module-permission-all", "GET", new HashMap(),
				TypeFactory.defaultInstance().constructCollectionType((Class<? extends Collection>) ArrayList.class, Module.class));
		StringBuffer permissions = new StringBuffer();
		for (Module module : modules) {
			if(StringUtils.isNotBlank(module.getMenuUrl())){
				permissions.append(StringUtils.trim(module.getMenuUrl()))
					.append(" = ")
					.append("perms[").append(StringUtils.trim(module.getAuthName())).append("]").append("\n");
			}
		}
		return permissions.toString();

	}

}
