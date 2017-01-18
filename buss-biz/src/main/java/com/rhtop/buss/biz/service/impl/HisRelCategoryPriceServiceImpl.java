package com.rhtop.buss.biz.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.log.SysoCounter;
import com.rhtop.buss.biz.mapper.HisRelCategoryPriceMapper;
import com.rhtop.buss.biz.service.HisRelCategoryPriceService;
import com.rhtop.buss.common.entity.HisRelCategoryPrice;
import com.rhtop.buss.common.entity.RelCategoryPrice;

@Service("hisRelCategoryPriceService")
public class HisRelCategoryPriceServiceImpl implements HisRelCategoryPriceService {
	@Autowired
	private HisRelCategoryPriceMapper hisRelCategoryPriceMapper;

	@Override
	public int insertRelCategoryPrice(RelCategoryPrice relCategoryPrice) {

		HisRelCategoryPrice his = new HisRelCategoryPrice();
		his.setHisRelCategoryPriceId(UUID.randomUUID().toString() .replace("-", ""));
		his.setAcptPri(relCategoryPrice.getAcptPri());
		his.setCategoryId(relCategoryPrice.getCategoryId());
		his.setCateSup(relCategoryPrice.getCateSup());
		his.setCreateTime(relCategoryPrice.getCreateTime());
		his.setCreateUser(relCategoryPrice.getCreateUser());
		his.setCusChaVal(relCategoryPrice.getCusChaVal());
		his.setFlgUpdateTime(relCategoryPrice.getFlgUpdateTime());
		his.setFutMax(relCategoryPrice.getFutMax());
		his.setFutMin(relCategoryPrice.getFutMin());
		his.setInterFutMax(relCategoryPrice.getInterFutMax());
		his.setInterFutMin(relCategoryPrice.getInterFutMin());
		his.setMgrId(relCategoryPrice.getMgrId());
		his.setMgrLoc(relCategoryPrice.getMgrLoc());
		his.setMidUpdateTime(relCategoryPrice.getMidUpdateTime());
		his.setOfferAging(relCategoryPrice.getOfferAging());
		his.setOfferUpdateTime(relCategoryPrice.getOfferUpdateTime());
		his.setWholesalePri(relCategoryPrice.getWholesalePri());
		his.setUpdateUser(relCategoryPrice.getUpdateUser());
		his.setUpdateTime(relCategoryPrice.getUpdateTime());
		his.setUniMgrId(relCategoryPrice.getUniMgrId());
		his.setUnit(relCategoryPrice.getUnit());
		his.setSpotMin(relCategoryPrice.getSpotMin());
		his.setSpotMin(relCategoryPrice.getSpotMax());
		his.setRelCategoryPriceId(relCategoryPrice.getRelCategoryPriceId());
		his.setRegMgrId(relCategoryPrice.getRegMgrId());
		
		try{
			hisRelCategoryPriceMapper.insertSelective(his);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return 0;
	}

}
