/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import com.rhtop.buss.common.entity.Upgrade;

public interface UpgradeMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(Upgrade upgrade);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String devType);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(Upgrade upgrade);
    /**
	 * 根据主键查询对象
	 */
    Upgrade selectByPrimaryKey(String devType);
    
    Upgrade selectDownload(Upgrade appVer);
    
}