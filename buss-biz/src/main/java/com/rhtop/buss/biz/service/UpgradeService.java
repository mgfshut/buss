/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;

import com.rhtop.buss.common.entity.Role;
import com.rhtop.buss.common.entity.Upgrade;

public interface UpgradeService{
    
	/**
	 * 新增
	 */
	int insertUpgrade(Upgrade upgrade);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteUpgrade(String devType);
	
	/**
	 * 修改
	 */
	int updateUpgrade(Upgrade upgrade);
	
	/**
	 * 根据Id查找数据
	 */
	Upgrade selectByPrimaryKey(String devType);
	
	/**
	 * 根据指定设备类型查询是否有大于当前版本的数据
	 */
	Upgrade selectDownload(Upgrade appVer);
}