/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.RelCustomerCategory;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.utils.UnitUtils;
import com.rhtop.buss.biz.mapper.RelCategoryPriceMapper;
import com.rhtop.buss.biz.mapper.RelCustomerCategoryMapper;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.CodeValueService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;

@Service("relCategoryPriceService")
public class RelCategoryPriceServiceImpl implements RelCategoryPriceService {
	@Autowired
	private RelCategoryPriceMapper relCategoryPriceMapper;
	@Autowired
	private RelCustomerCategoryMapper relCusCatMapper;
	@Autowired
	private CodeValueService codeValueService;
	@Autowired
	private CategoryService catSer;
	
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
	@Transactional
	public List<RelCategoryPrice> listRelCategoryPrices(RelCategoryPrice relCategoryPrice, String memberId) {
		List<RelCategoryPrice> relCategoryPriceList = relCategoryPriceMapper.listRelCategoryPrices(relCategoryPrice);
		List<RelCategoryPrice> rList = new ArrayList<RelCategoryPrice>();
		//根据价格里面的品类ID和渠道ID查询客户和品类关联表内容，主要是确定当前用户是否有这个渠道的品类 信息
		for (int i=0; i<relCategoryPriceList.size(); i++){
			RelCategoryPrice catePrice = relCategoryPriceList.get(i);
			if (StringUtils.isNotEmpty(catePrice.getCusChaId())){
				RelCustomerCategory rBean = relCusCatMapper.selectByChaAndCateAndCreater(memberId, catePrice.getCategoryId(), catePrice.getCusChaId());
				if (rBean == null){
					//当前用户没有这个品类渠道信息
					catePrice.setCreateUser(null);
				}
				
				rList.add(catePrice);
			}
		}
		return rList;
	}
	
	@Override
	public List<RelCategoryPrice> listPageRelCategoryPrice(RelCategoryPrice relCategoryPrice) {
		List<RelCategoryPrice> relCategoryPrices = relCategoryPriceMapper.listPageRelCategoryPrice(relCategoryPrice);
		return relCategoryPrices;
	}

	@Override
	@Transactional
	public ResultInfo createOrUpdateWholesaleAndAcptPriceByCategoryId(ResultInfo readResult,List<RelCategoryPrice> relCategoryPrices, String categoryId, String userId) throws Exception{
		try {
			
			for(RelCategoryPrice relCategoryPrice : relCategoryPrices){
				if(relCategoryPrice.getWholesalePri()==null||relCategoryPrice.getWholesalePri().floatValue() == 0||
						relCategoryPrice.getAcptPri()==null||relCategoryPrice.getAcptPri().floatValue() == 0){
					throw new Exception("请完整填写价格，任一渠道的价格都不可为空。");
				}
				if(relCategoryPrice.getWholesalePri()<=0||relCategoryPrice.getAcptPri()<=0){
					throw new Exception("价格信息不可为负数！");
				}
				relCategoryPrice.setMgrId(userId);
				relCategoryPrice.setCategoryId(categoryId);
				RelCustomerCategory cusCat = relCusCatMapper.selectByChaAndCateAndCreater(userId, relCategoryPrice.getCategoryId(), relCategoryPrice.getCusChaId());
				if (cusCat == null){
					readResult.setCode("500");
					readResult.setMessage("渠道信息不存在，无法采集数据！");
					return readResult;
				}
				//先通过品类ID检查这条关系记录是否已经存在
				List<RelCategoryPrice> data = relCategoryPriceMapper.selectByCategoryIdAndChaId(relCategoryPrice.getCategoryId(), relCategoryPrice.getCusChaId());
				RelCategoryPrice rel = null;
				if (data != null && data.size() > 0){
					rel = data.get(0);
				}
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now = sdf.format(date);
				if(rel==null){
					relCategoryPrice.setCusLoc(cusCat.getCusLoc());
					relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
					relCategoryPrice.setCreateUser(relCategoryPrice.getMgrId());
					relCategoryPrice.setCreateTime(now);
					relCategoryPrice.setUpdateUser(relCategoryPrice.getMgrId());
					relCategoryPrice.setUpdateTime(now);
					relCategoryPrice.setFlgUpdateTime(now);
					relCategoryPriceMapper.insertSelective(relCategoryPrice);
				}else{
					rel.setFlgUpdateTime(now);
					rel.setWholesalePri(relCategoryPrice.getWholesalePri());
					rel.setAcptPri(relCategoryPrice.getAcptPri());
					rel.setUpdateUser(relCategoryPrice.getMgrId());
					rel.setUpdateTime(now);
					relCategoryPriceMapper.updateByCategoryId(rel);
				}
				cusCat.setMgrCk("02");
				relCusCatMapper.updateByPrimaryKeySelective(cusCat);
			}
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage("数据更新异常");
		}
		return readResult;
	}

	@Override
	@Transactional
	public ResultInfo createOrUpdateMidPriceByCategoryId(ResultInfo readResult, List<RelCategoryPrice> relCategoryPrices)throws Exception {
		try {
			String now = DateUtils.getNowTime();
			//遍历填进去的每一个新的价格
			for(RelCategoryPrice relCategoryPrice : relCategoryPrices){
				String userId = relCategoryPrice.getUpdateUser();
				//查出这个品类、这个操作人（是不是记录创建人）、渠道在客户品类关系表中是否有记录
				RelCustomerCategory cusCat = relCusCatMapper.selectByChaAndCateAndCreater(relCategoryPrice.getUpdateUser(), relCategoryPrice.getCategoryId(), relCategoryPrice.getCusChaId());
				//有记录，那么这条记录是直接由分部经理创建的，因此他要维护五个价格（包括客户经理的那两个）。
				if(cusCat!=null){
//					//做非空校验,由于前端已经做了校验，如果信息不完整传的是空数组，所以在Controller已经做了判断了，这里就不需要了但是还需要一个大小的和不小于零的判断。
					if(relCategoryPrice.getWholesalePri()==null||relCategoryPrice.getWholesalePri().equals(0)||
							relCategoryPrice.getAcptPri()==null||relCategoryPrice.getAcptPri().equals(0)||
							relCategoryPrice.getSpotMin()==null||relCategoryPrice.getSpotMin().equals(0)||
							relCategoryPrice.getSpotMax()==null||relCategoryPrice.getSpotMax().equals(0)||
							 relCategoryPrice.getFutMin()==null||relCategoryPrice.getFutMin().equals(0)||
							 relCategoryPrice.getFutMax()==null||relCategoryPrice.getFutMax().equals(0)||
						relCategoryPrice.getInterFutMin()==null||relCategoryPrice.getInterFutMin().equals(0)||
						relCategoryPrice.getInterFutMax()==null||relCategoryPrice.getInterFutMax().equals(0)){
						throw new Exception("请填写所有数据内容！");
					}else if(relCategoryPrice.getSpotMin()>=relCategoryPrice.getSpotMax()||
							relCategoryPrice.getFutMin()>=relCategoryPrice.getFutMax()||
							relCategoryPrice.getInterFutMin()>=relCategoryPrice.getInterFutMax()){
						throw new Exception("数据不合理，最小值不可大于等于对应的最大值！");
					}else if(relCategoryPrice.getWholesalePri()<0||relCategoryPrice.getAcptPri()<0||
							relCategoryPrice.getSpotMin()<0||relCategoryPrice.getSpotMax()<0||
							relCategoryPrice.getFutMin()<0|| relCategoryPrice.getFutMax()<0||
							relCategoryPrice.getInterFutMin()<0||relCategoryPrice.getInterFutMax()<0){
						throw new Exception("数据不合理，价格不能小于零。");
					}else{
						//查关系表数据
						List<RelCategoryPrice> data = relCategoryPriceMapper.selectByCategoryIdAndChaId(relCategoryPrice.getCategoryId(), relCategoryPrice.getCusChaId());
						RelCategoryPrice rel = null;
						if (data != null && data.size() > 0){
							rel = data.get(0);
						}
						//如果没有数据，关系表新增记录
						if(rel==null){
							relCategoryPrice.setCusLoc(cusCat.getCusLoc());
							relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
							relCategoryPrice.setCreateUser(userId);
							relCategoryPrice.setCreateTime(now);
							relCategoryPrice.setMidUpdateTime(now);
							relCategoryPrice.setFlgUpdateTime(now);
							relCategoryPrice.setMgrId(userId);
							relCategoryPrice.setRegMgrId(userId);
							relCategoryPriceMapper.insertSelective(relCategoryPrice);
						}else{
						//如果有数据，则更新价格表数据。
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
							rel.setFlgUpdateTime(now);
							rel.setUpdateTime(now);
							relCategoryPriceMapper.updateByCategoryId(rel);
						}
						//价格表数据更新完成后，对品类客户表的经理审核状态进行修改。
						cusCat.setMgrCk("02");
						cusCat.setRegMgrCk("02");
						relCusCatMapper.updateByPrimaryKeySelective(cusCat);
					}
				//没有记录，这条记录不是这个分部经理创建的，他只需要填写后面的三个价格。
				}else{
					//做非空校验
					if(		relCategoryPrice.getSpotMin()==null||relCategoryPrice.getSpotMin().equals(0)||
							relCategoryPrice.getSpotMax()==null||relCategoryPrice.getSpotMax().equals(0)||
							 relCategoryPrice.getFutMin()==null||relCategoryPrice.getFutMin().equals(0)||
							 relCategoryPrice.getFutMax()==null||relCategoryPrice.getFutMax().equals(0)||
						relCategoryPrice.getInterFutMin()==null||relCategoryPrice.getInterFutMin().equals(0)||
						relCategoryPrice.getInterFutMax()==null||relCategoryPrice.getInterFutMax().equals(0)){
						readResult.setCode("500");
						//readResult.setMessage("数据不完整！");
						throw new Exception("请填写所有数据内容！");
					}else if(relCategoryPrice.getSpotMin()>=relCategoryPrice.getSpotMax()||
							relCategoryPrice.getFutMin()>=relCategoryPrice.getFutMax()||
							relCategoryPrice.getInterFutMin()>=relCategoryPrice.getInterFutMax()){
						throw new Exception("数据不合理，最小值不可大于等于对应的最大值！");
					}else if(relCategoryPrice.getSpotMin()<0||relCategoryPrice.getSpotMax()<0||
							relCategoryPrice.getFutMin()<0|| relCategoryPrice.getFutMax()<0||
							relCategoryPrice.getInterFutMin()<0||relCategoryPrice.getInterFutMax()<0){
						throw new Exception("数据不合理，价格不能小于零。");
					}else{
						//查关系表数据
						List<RelCategoryPrice> data = relCategoryPriceMapper.selectByCategoryIdAndChaId(relCategoryPrice.getCategoryId(), relCategoryPrice.getCusChaId());
						RelCategoryPrice rel = null;
						if (data != null && data.size() > 0){
							rel = data.get(0);
						}
						//如果没有数据，新建一条关系表记录。
						if(rel==null){
							relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
							relCategoryPrice.setCreateUser(userId);
							relCategoryPrice.setCreateTime(now);
							relCategoryPrice.setMidUpdateTime(now);
							relCategoryPrice.setRegMgrId(userId);
							relCategoryPriceMapper.insertSelective(relCategoryPrice);
						}else{
						//如果有数据，则更新价格表数据。
							rel.setMidUpdateTime(now);
							rel.setSpotMin(relCategoryPrice.getSpotMin());
							rel.setSpotMax(relCategoryPrice.getSpotMax());
							rel.setInterFutMin(relCategoryPrice.getInterFutMin());
							rel.setInterFutMax(relCategoryPrice.getInterFutMax());
							rel.setFutMin(relCategoryPrice.getFutMin());
							rel.setFutMax(relCategoryPrice.getFutMax());
							rel.setUpdateUser(relCategoryPrice.getRegMgrId());
							rel.setUpdateTime(now);
							rel.setRegMgrId(userId);
							relCategoryPriceMapper.updateByCategoryId(rel);
						}
						//价格表数据更新完成后，对品类客户表的经理审核状态进行修改。
						List<RelCustomerCategory> cusCats = relCusCatMapper.selectByChaAndCate(relCategoryPrice.getCusChaId(), relCategoryPrice.getCategoryId());
						for(RelCustomerCategory cusCat1 : cusCats){
							cusCat1.setRegMgrCk("02");
							relCusCatMapper.updateByPrimaryKeySelective(cusCat1);
						}
					}
				}
			}
		} catch (Exception e) {
			readResult.setCode("500");
			throw e;
		}
		return readResult;
	}

	@Override
	@Transactional
	public ResultInfo createOrUpdateOfferPriceAndTimeByCategoryId(ResultInfo readResult, 
			RelCategoryPrice relCategoryPrice) throws Exception{
		try {
			//检测报盘时效offerAging如果大于24，是不是24的倍数，不是则抛出异常
			String offerAgingStr = relCategoryPrice.getOfferAging();
			Float offerAging = Float.parseFloat(offerAgingStr);
			if(1<offerAging/24){
				if(0!=offerAging%24){
					throw new Exception("回盘时效若大于24小时则必须是24的整数倍。");
				}
			}
			//从字典表中拿汇率数据
			String rate = null;
			List<CodeValue> rates = codeValueService.listCodeValuesByCode("rate");
			if (rates == null || rates.size() == 0){
				readResult.setCode("500");
				readResult.setMessage("暂无汇率字典信息，无法进行美元转换！");
				
				return readResult;
			}
			
			for(CodeValue val : rates){
				if(val.getCodeValue().trim().equals("us")){
					rate = val.getCodeValueDescribe();
				}
			}
			//换算价格
			BigDecimal offerPri = UnitUtils.unitConver(relCategoryPrice.getCurrency(), new BigDecimal(relCategoryPrice.getCatePri()), relCategoryPrice.getUnit(), rate);
			//换算好的报盘价添加到品类表中
			Category cat = catSer.selectByPrimaryKey(relCategoryPrice.getCategoryId());
			cat.setOfferPri(offerPri.floatValue());
			cat.setOfferAging(relCategoryPrice.getOfferAging());
			catSer.updateCategory(cat);
			//检查品类/价格关系表中是否有记录
			List<RelCategoryPrice> catPriList = relCategoryPriceMapper.selectByCategoryId(relCategoryPrice.getCategoryId());
			if(catPriList == null || catPriList.size() == 0){
				//没有则新建记录
				relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
				relCategoryPrice.setCreateTime(relCategoryPrice.getUpdateTime());
				relCategoryPrice.setCreateUser(relCategoryPrice.getUpdateUser());
				relCategoryPriceMapper.insertSelective(relCategoryPrice);
			}else{
				//有则更新记录。
				int i = relCategoryPriceMapper.updateByCategoryId(relCategoryPrice);
				readResult.setMessage("成功更新"+i+"条数据");
			}
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage("数据更新时出现异常。"+e.getMessage());
		}
		return readResult;
	}
	
	@Override
	@Transactional
	public ResultInfo universeAddCategoryAndPrice(ResultInfo readResult,
			Category cat) throws Exception {
		//检查要新增的品类是否已存在于数据库中
		String userId = cat.getUpdateUser();
		String now = cat.getUpdateTime();
		Category exist = catSer.checkCategoryExist(cat);
		if(exist == null){
			//新增品类
			cat.setCategoryId(UUID.randomUUID().toString().replace("-", ""));
			cat.setCreateTime(now);
			cat.setCreateUser(userId);
			catSer.insertCategory(cat);
			//新增品类价格关系数据
			RelCategoryPrice catPri = new RelCategoryPrice();
			catPri.setCategoryId(cat.getCategoryId());
			catPri.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
			catPri.setUniMgrId(userId);
			catPri.setUpdateTime(now);
			catPri.setUpdateUser(userId);
			catPri.setCateSup(cat.getCateSup());
			catPri.setCurrency(cat.getCurrency());
			catPri.setUnit(cat.getUnit());
			catPri.setOfferAging(cat.getOfferAging());
			catPri.setCatePri(cat.getCatePri());
			catPri.setCreateUser(userId);
			catPri.setCreateTime(now);
			createOrUpdateOfferPriceAndTimeByCategoryId(readResult, catPri);
		}else{
			//更新品类价格信息
			RelCategoryPrice catPri = new RelCategoryPrice();
			catPri.setCategoryId(exist.getCategoryId());
			catPri.setUniMgrId(userId);
			catPri.setUpdateTime(now);
			catPri.setUpdateUser(userId);
			catPri.setCateSup(cat.getCateSup());
			catPri.setCurrency(cat.getCurrency());
			catPri.setUnit(cat.getUnit());
			catPri.setOfferAging(cat.getOfferAging());
			catPri.setCatePri(cat.getCatePri());
			createOrUpdateOfferPriceAndTimeByCategoryId(readResult, catPri);
		}
		return readResult;
	}
	

	@Override
	public int updateSelective(RelCategoryPrice relCategoryPrice) {
		relCategoryPriceMapper.updateSelective(relCategoryPrice);
		return 0;
	}

	


}
