package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.rhtop.buss.common.entity.SysLogger;
import com.rhtop.buss.common.service.impl.BaseLoggerInterceptor;


public class LoggerManagerInerceptor extends BaseLoggerInterceptor {
	@Autowired
	private SysLoggerManagerImpl sysLoggerManagerImpl; 
	@Override
	protected void writeLogger(SysLogger sysLogger) {
		sysLoggerManagerImpl.save(sysLogger);
	}
}
