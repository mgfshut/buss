package com.rhtop.buss.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.rhtop.buss.common.entity.DefaultTxnTimeLimit;
import com.rhtop.buss.common.entity.TxnTimeLimit;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.service.TxnTimeLimitService;
import com.rhtop.buss.common.utils.DateUtils;

public class DefaultTxnTimeLimitServiceImpl implements TxnTimeLimitService{
	final private static String TXN_TIME_LIMIT = "TXN_TIME_LIMIT";
	private CacheManager cacheManager;
	private Cache cache;
	
	@Autowired
	private RestService rs; 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean isTxnTime() {
		try{
			// 使用缓存
			TxnTimeLimit limit = cache.get(TXN_TIME_LIMIT, TxnTimeLimit.class);
			//if(limit == null){
				Map<String,Object> map = rs.invoke("config-limit", new HashMap() );
				
				Map m = (Map)map.get("limit");
				limit = new DefaultTxnTimeLimit((String)m.get("startTime"),(String)m.get("endTime"));
				cache.put(TXN_TIME_LIMIT,limit);
			//}
			return DateUtils.checkTxnTime(limit.getStartTime(), limit.getEndTime());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	public CacheManager getCacheManager() {
		return cacheManager;
	}
	public void setCacheManager(CacheManager cacheManager) {
		this.cache = cacheManager.getCache(TXN_TIME_LIMIT);
		this.cacheManager = cacheManager;
	}

}
