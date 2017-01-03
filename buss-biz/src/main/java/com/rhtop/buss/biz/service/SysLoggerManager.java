package com.rhtop.buss.biz.service;

import com.rhtop.buss.common.entity.SysLogger;

public interface SysLoggerManager{
	/**
	 * 保存
	 * @param sysLogger
	 * @return
	 */
	public SysLogger save(SysLogger sysLogger);
}
