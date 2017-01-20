/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhtop.buss.biz.mapper.UpgradeMapper;
import com.rhtop.buss.biz.service.UpgradeService;
import com.rhtop.buss.common.entity.Upgrade;

@Service("upgradeService")
public class UpgradeServiceImpl implements UpgradeService {
	@Autowired
	private UpgradeMapper upgradeMapper;

	@Override
	public int insertUpgrade(Upgrade upgrade) {
		return upgradeMapper.insertSelective(upgrade);
	}

	@Override
	public int deleteUpgrade(String devType) {
		return upgradeMapper.deleteByPrimaryKey(devType);
	}

	@Override
	public int updateUpgrade(Upgrade upgrade) {
		return upgradeMapper.updateByPrimaryKeySelective(upgrade);
	}

	@Override
	public Upgrade selectByPrimaryKey(String devType) {
		return upgradeMapper.selectByPrimaryKey(devType);
	}

	@Override
	public Upgrade selectDownload(Upgrade appVer) {
		return upgradeMapper.selectDownload(appVer);
	}
	
}