package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhtop.buss.biz.mapper.HisRelCategoryPriceMapper;
import com.rhtop.buss.biz.service.HisRelCategoryPriceService;
import com.rhtop.buss.common.entity.RelCategoryPrice;

@Service("hisRelCategoryPriceService")
public class HisRelCategoryPriceServiceImpl implements HisRelCategoryPriceService {
	@Autowired
	private HisRelCategoryPriceMapper hisRelCategoryPriceMapper;

	@Override
	public int insertRelCategoryPrice(RelCategoryPrice relCategoryPrice) {
		return hisRelCategoryPriceMapper.insertSelective(relCategoryPrice);
	}

}
