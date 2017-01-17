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

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.ResultInfo;
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
	public int createOrUpdateWholesaleAndAcptPriceByCategoryId(List<RelCategoryPrice> relCategoryPrices) {
		int status = 0;
		for(RelCategoryPrice relCategoryPrice : relCategoryPrices){
			relCategoryPrice.setMgrId(relCategoryPrice.getUpdateUser());
			//先通过品类ID检查这条关系记录是否已经存在
			RelCategoryPrice rel = relCategoryPriceMapper.selectByCategoryId(relCategoryPrice.getCategoryId());
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			if(rel==null){
				relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
				relCategoryPrice.setCreateUser(relCategoryPrice.getMgrId());
				relCategoryPrice.setCreateTime(now);
				status = relCategoryPriceMapper.insertSelective(relCategoryPrice);
			}else{
				rel.setMidUpdateTime(now);
				rel.setWholesalePri(relCategoryPrice.getWholesalePri());
				rel.setAcptPri(relCategoryPrice.getAcptPri());
				rel.setUpdateUser(relCategoryPrice.getMgrId());
				rel.setUpdateTime(now);
				status = relCategoryPriceMapper.updateByCategoryId(rel);
			}
		}
		return status;
	}

	@Override
	public int createOrUpdateMidPriceByCategoryId(List<RelCategoryPrice> relCategoryPrices) {
		int status = 0;
		for(RelCategoryPrice relCategoryPrice : relCategoryPrices){
			relCategoryPrice.setMgrId(relCategoryPrice.getUpdateUser());
			//先通过品类ID检查这条关系记录是否已经存在
			RelCategoryPrice rel = relCategoryPriceMapper.selectByCategoryId(relCategoryPrice.getCategoryId());
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			if(rel==null){
				relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
				relCategoryPrice.setCreateUser(relCategoryPrice.getRegMgrId());
				relCategoryPrice.setCreateTime(now);
				status = relCategoryPriceMapper.insertSelective(relCategoryPrice);
			}else{
				rel.setMidUpdateTime(now);
				rel.setAcptPri(relCategoryPrice.getAcptPri());
				rel.setWholesalePri(relCategoryPrice.getWholesalePri());
				rel.setSpotMin(relCategoryPrice.getSpotMin());
				rel.setSpotMax(relCategoryPrice.getSpotMax());
				rel.setInterFutMin(relCategoryPrice.getInterFutMin());
				rel.setInterFutMax(relCategoryPrice.getInterFutMax());
				rel.setFutMin(relCategoryPrice.getFutMin());
				rel.setFutMax(relCategoryPrice.getFutMax());
				rel.setUpdateUser(relCategoryPrice.getRegMgrId());
				rel.setMidUpdateTime(now);
				rel.setUpdateTime(now);
				status = relCategoryPriceMapper.updateByCategoryId(rel);
			}
		}
		return status;
	}

	@Override
	public ResultInfo createOrUpdateOfferPriceAndTimeByCategoryId(ResultInfo readResult, 
			RelCategoryPrice relCategoryPrice) {
		try {
			RelCategoryPrice catPri = relCategoryPriceMapper.selectByCategoryId(relCategoryPrice.getCategoryId());
			if(catPri==null){
				relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
				relCategoryPrice.setCreateTime(relCategoryPrice.getUpdateTime());
				relCategoryPrice.setCreateUser(relCategoryPrice.getUpdateUser());
				relCategoryPriceMapper.insertSelective(relCategoryPrice);
			}else{
				int i = relCategoryPriceMapper.updateByCategoryId(relCategoryPrice);
				readResult.setMessage("成功更新"+i+"条数据");
			}
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage("数据更新时出现未知异常。");
		}
		return readResult;
	}

	@Override
	public int updateSelective(RelCategoryPrice relCategoryPrice) {
		relCategoryPriceMapper.updateSelective(relCategoryPrice);
		return 0;
	}


}
