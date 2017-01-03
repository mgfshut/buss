package com.rhtop.buss.common.security;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultFilterFactoryBean extends ShiroFilterFactoryBean{
	final static private Logger log = LoggerFactory.getLogger(DefaultFilterFactoryBean.class);
	static final private String PERMISSIONS_CACHED = "PERMISSIONS_CACHED";
	private Cache<String,String> permissionsCache;
	private CacheManager cacheManager;
	private UrlPermissionLoader urlPermissionLoader;
	private String filterChainDefinitions;

	@Override
	public void setFilterChainDefinitions(String definitions) {
		this.filterChainDefinitions = definitions;
		super.setFilterChainDefinitions(definitions);
	}
	
	public UrlPermissionLoader getUrlPermissionLoader() {
		return urlPermissionLoader;
	}

	public void setUrlPermissionLoader(UrlPermissionLoader urlPermissionLoader) {
		this.urlPermissionLoader = urlPermissionLoader;
	}
	
	public void reloadPermission(Boolean forceReload) throws Exception{
		// 读取缓存标志
		if(this.permissionsCache.get(PERMISSIONS_CACHED) != null && !forceReload){
			return ;
		}

		String filters = urlPermissionLoader.getAllUrlPermission();
		filters += filterChainDefinitions;
		this.permissionsCache.put(PERMISSIONS_CACHED, filters);
		super.setFilterChainDefinitions(filters);
        //-----------------------
        FilterChainManager manager = createFilterChainManager();

        //Expose the constructed FilterChainManager by first wrapping it in a
        // FilterChainResolver implementation. The AbstractShiroFilter implementations
        // do not know about FilterChainManagers - only resolvers:
        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);

        //Now create a concrete ShiroFilter instance and apply the acquired SecurityManager and built
        //FilterChainResolver.  It doesn't matter that the instance is an anonymous inner class
        //here - we're just using it because it is a concrete AbstractShiroFilter instance that accepts
        //injection of the SecurityManager and FilterChainResolver:
        //return new SpringShiroFilter((WebSecurityManager) securityManager, chainResolver);

        //-----------------------
		AbstractShiroFilter shiroFilter = (AbstractShiroFilter) this.getObject();
		synchronized (shiroFilter) {
			shiroFilter.setFilterChainResolver(chainResolver);
		}
		log.info("reloadPermission success.{}",getFilterChainDefinitionMap().entrySet());
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.permissionsCache = cacheManager.getCache(PERMISSIONS_CACHED);
		this.cacheManager = cacheManager;
	}
}
