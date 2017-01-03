package com.rhtop.buss.common.security;

import org.apache.shiro.authc.Account;


/**
 * 用户登录服务接口
 * @author mgf
 *
 */
public interface UserService {
	public Account findUser(String loginName);
}
