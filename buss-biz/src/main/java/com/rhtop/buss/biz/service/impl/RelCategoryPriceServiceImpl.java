/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.biz.mapper.RelCategoryPriceMapper;
import com.rhtop.buss.biz.service.RelCategoryPriceService;

@Service("relCategoryPriceService")
public class RelCategoryPriceServiceImpl implements RelCategoryPriceService {
	@Autowired
	private RelCategoryPriceMapper relCategoryPriceMapper;
	
	@Override
	public int insertRelCategoryPrice(RelCategoryPrice relCategoryPrice) {
		return relCategoryPriceMapper.insertSelective(relCategoryPrice);
	}

	@Override
	public int deleteRelCategoryPrice(String relCategoryPriceId) {
		return relCategoryPriceMapper.deleteByPrimaryKey(relCategoryPriceId);
	}

	@Override
	public int updateRelCategoryPrice(RelCategoryPrice relCategoryPrice) {
		return relCategoryPriceMapper.updateByPrimaryKeySelective(relCategoryPrice);
	}
	
	@Override
	public RelCategoryPrice selectByPrimaryKey(String relCategoryPriceId){
		return relCategoryPriceMapper.selectByPrimaryKey(relCategoryPriceId);
	}

	@Override
	public List<RelCategoryPrice> listRelCategoryPrices(RelCategoryPrice relCategoryPrice) {
		List<RelCategoryPrice> relCategoryPriceList = relCategoryPriceMapper.listRelCategoryPrices(relCategoryPrice);
		return relCategoryPriceList;
	}
	
	@Override
	public List<RelCategoryPrice> listPageRelCategoryPrice(RelCategoryPrice relCategoryPrice) {
		List<RelCategoryPrice> relCategoryPrices = relCategoryPriceMapper.listPageRelCategoryPrice(relCategoryPrice);
		return relCategoryPrices;
	}

	@Override
	public int createOrUpdateByCategoryId(RelCategoryPrice relCategoryPrice) {
		//先通过品类ID检查这条关系记录是否已经存在
		RelCategoryPrice rel = relCategoryPriceMapper.selectByCategoryId(relCategoryPrice.getCategoryId());
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(date);
		int status = 0;
		if(rel==null){
			relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
			relCategoryPrice.setCreateUser(relCategoryPrice.getMgrId());
			relCategoryPrice.setCreateTime(now);
			status = relCategoryPriceMapper.insertSelective(relCategoryPrice);
			return status;
		}else{
			rel.setMidUpdateTime(now);
			rel.setWholesalePri(relCategoryPrice.getWholesalePri());
			rel.setAcptPri(relCategoryPrice.getAcptPri());
			rel.setUpdateUser(relCategoryPrice.getMgrId());
			rel.setUpdateTime(now);
			status = relCategoryPriceMapper.updateByCategoryId(rel);
			return status;
		}
	}

}