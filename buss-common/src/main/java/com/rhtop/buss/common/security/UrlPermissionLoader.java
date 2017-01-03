package com.rhtop.buss.common.security;

public interface UrlPermissionLoader {
	/**
	 * 权限Filter字符串，请参考Shiro filterChainDefinitions
	 * @return
	 */
	public String getAllUrlPermission();
}
