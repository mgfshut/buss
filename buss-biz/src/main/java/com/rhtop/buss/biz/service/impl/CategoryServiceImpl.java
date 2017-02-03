/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhtop.buss.biz.mapper.CategoryMapper;
import com.rhtop.buss.biz.mapper.MemberMapper;
import com.rhtop.buss.biz.mapper.RelCategoryPriceMapper;
import com.rhtop.buss.biz.mapper.RelCustomerCategoryMapper;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.utils.FileUtil;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private RelCategoryPriceMapper relCategoryPriceMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private RelCustomerCategoryMapper relCustomerCategoryMapper;
	
	@Override
	public int insertCategory(Category category) {
		return categoryMapper.insertSelective(category);
	}

	@Override
	public int deleteCategory(String categoryId) {
		return categoryMapper.deleteByPrimaryKey(categoryId);
	}

	@Override
	public int updateCategory(Category category) {
		return categoryMapper.updateCategory(category);
	}
	
	@Override
	public Category selectByPrimaryKey(String categoryId){
		return categoryMapper.selectByPrimaryKey(categoryId);
	}
	
	public Category selectInfoByPrimaryKey(String categoryId){
		//品类信息
		Category cate = categoryMapper.selectByPrimaryKey(categoryId);
	/*	//价格信息
		List<RelCategoryPrice> relCategoryPrice = relCategoryPriceMapper.selectByCategoryId(categoryId);
		RelCategoryPrice relCategoryPrice = relCategoryPriceMapper.selectByCategoryId(categoryId);
		cate.setRelCategoryPrice(relCategoryPrice);
		//防止cate为空，报空指针异常
		if(null==cate){
			return cate;
		}*/
		String newUrl = null;
		try {
			newUrl = FileUtil.getPicUrl(cate.getCatePic());
		} catch (Exception e) {
			e.printStackTrace();
		}
		cate.setCatePic(newUrl);//需要返回的是图片在服务器上的绝对路径
		return cate;
	}

	@Override
	public List<Category> listCategorys(Category category) {
		List<Category> categoryList = categoryMapper.listCategorys(category);
		return categoryList;
	}
	
	@Override
	public List<Category> listPageCategory(Category category) {
		List<Category> categorys = categoryMapper.listPageCategory(category);
		return categorys;
	}
	
	@Override
	public List<Category> listPageCategoryByIntf(Category category) {
		List<Category> categorys = categoryMapper.listPageCategoryByIntf(category);
		return categorys;
	}

	@Override
	public Category checkCategoryExist(Category category) {
		return categoryMapper.selectByPrimaryParam(category);
	}

	@Override
	public List<Category> listCategoryByCustomer(String customerId) {
		return categoryMapper.listCategoryByCustomer(customerId);
	}

	@Override
	public List<Category> listPageCategoeyByPrice(Category category) {
		String memberId  = category.getCreateUser();
		List<Category> catelist =null; 
		String memberJob = memberMapper.selectByPrimaryKey(memberId).getMemberJob();
		//对职务进行判断
		if("01".equals(memberJob)){//客户经理
			//客户经理查询自己的信息采集情况
			catelist = categoryMapper.listPriceByMgr(category);
		}else if("02".equals(memberJob)){//分部经理
			//分部经理查询自己的信息采集情况（自己创建的五个价格需要自己填写）
			catelist = categoryMapper.listPriceByRegMgr(category);
		}else if("03".equals(memberJob)){//国际采购部
//			国际采购人员查看报盘情况(已报盘/未报盘) 00:未报盘  01:已报盘
//			String isOffer = category.getIsOffer();
				catelist = categoryMapper.listPagePriceByUniMgr(category);
		}
		return catelist;
	}

	@Override
	@Transactional
	public int insertExcelCategory(List<Category> categorys) throws Exception{
		try {
			for(Category category:categorys){
				//数据是决策委员会导入标记
				category.setIsImport("10");
				Category existCate = checkCategoryExist(category);
				Category cate = new Category();
				if (existCate != null){
					cate = existCate;
					//更新已存在品类的报盘价和时效性
					cate.setOfferPri(category.getOfferPri());
					cate.setOfferAging(category.getOfferAging());
					cate.setUpdateUser(category.getUpdateUser());
					cate.setUpdateTime(category.getUpdateTime());
					cate.setIsImport(category.getIsImport());
					
					categoryMapper.updateCategory(cate);
					//价格信息
					List<RelCategoryPrice> relCategoryPriceList = relCategoryPriceMapper.selectByCategoryIdAndChaId(cate.getCategoryId(), category.getCusCha());
					//relCategoryPriceMapper.selectByCategoryId(cate.getCategoryId());
					RelCategoryPrice relCategoryPrice = null;
					if (relCategoryPriceList == null || relCategoryPriceList.size() == 0){
						relCategoryPrice = new RelCategoryPrice();
						relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
						
						if (category.getRelCategoryPrice() == null){
							category.setRelCategoryPrice(new RelCategoryPrice());
						}
						relCategoryPrice.setCateSup(category.getRelCategoryPrice().getCateSup());
						relCategoryPrice.setAcptPri(category.getRelCategoryPrice().getAcptPri());
						relCategoryPrice.setWholesalePri(category.getRelCategoryPrice().getWholesalePri());
						relCategoryPrice.setSpotMin(category.getRelCategoryPrice().getSpotMin());
						relCategoryPrice.setSpotMax(category.getRelCategoryPrice().getSpotMax());
						relCategoryPrice.setInterFutMin(category.getRelCategoryPrice().getInterFutMin());
						relCategoryPrice.setInterFutMax(category.getRelCategoryPrice().getInterFutMax());
						relCategoryPrice.setFutMin(category.getRelCategoryPrice().getFutMin());
						relCategoryPrice.setFutMax(category.getRelCategoryPrice().getFutMax());
						relCategoryPrice.setCusLoc(category.getCusLoc());
						relCategoryPrice.setCategoryId(cate.getCategoryId());
						relCategoryPrice.setCatePri(category.getOfferPri());
						relCategoryPrice.setOfferAging(category.getOfferAging());
						relCategoryPrice.setCusChaId(category.getCusCha());
						relCategoryPrice.setCreateUser(category.getUpdateUser());
						relCategoryPrice.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
						relCategoryPrice.setUpdateUser(category.getUpdateUser());
						relCategoryPrice.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
						relCategoryPriceMapper.insertSelective(relCategoryPrice);
					}else{
						for (int i=0; i<relCategoryPriceList.size(); i++){
							RelCategoryPrice item = relCategoryPriceList.get(i);
							
							if (category.getRelCategoryPrice() == null){
								category.setRelCategoryPrice(new RelCategoryPrice());
							}
							item.setCateSup(category.getRelCategoryPrice().getCateSup());
							item.setAcptPri(category.getRelCategoryPrice().getAcptPri());
							item.setWholesalePri(category.getRelCategoryPrice().getWholesalePri());
							item.setSpotMin(category.getRelCategoryPrice().getSpotMin());
							item.setSpotMax(category.getRelCategoryPrice().getSpotMax());
							item.setInterFutMin(category.getRelCategoryPrice().getInterFutMin());
							item.setInterFutMax(category.getRelCategoryPrice().getInterFutMax());
							item.setFutMin(category.getRelCategoryPrice().getFutMin());
							item.setFutMax(category.getRelCategoryPrice().getFutMax());
							item.setCatePri(category.getOfferPri());
							item.setOfferAging(category.getOfferAging());
							item.setUpdateUser(category.getUpdateUser());
							item.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
							relCategoryPriceMapper.updateByPrimaryKeySelective(item);
						}
					}
				}else{
					cate.setCateName(category.getCateName());
					cate.setCateStan(category.getCateStan());
					cate.setManuNum(category.getManuNum());
					cate.setProdPla(category.getProdPla());
					List<Category> categoryList = categoryMapper.listCategorys(cate);
					if(categoryList.size()>0){
						category = categoryList.get(0);
					}else{
						category.setCategoryId(UUID.randomUUID().toString().replace("-", ""));
						categoryMapper.insertSelective(category);
					}
					/*RelCustomerCategory relcc = new RelCustomerCategory();
					relcc.setRelCustomerCategoryId(UUID.randomUUID().toString().replace("-", ""));
					relcc.setCategoryId(category.getCategoryId());
//					relcc.setCusChaId(cusChaId);
					relcc.setCusChaVal(category.getCusCha());
					relcc.setCreateUser(category.getUpdateUser());
					relcc.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
					relcc.setUpdateUser(category.getUpdateUser());
					relcc.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
					relCustomerCategoryMapper.insertSelective(relcc);*/
					
					List<RelCategoryPrice> relCategoryPriceList = relCategoryPriceMapper.selectByCategoryIdAndChaId(category.getCategoryId(), category.getCusCha());
					RelCategoryPrice relCategoryPrice = null;
					if (relCategoryPriceList == null || relCategoryPriceList.size() == 0){
						relCategoryPrice = new RelCategoryPrice();
						relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
						
						if (category.getRelCategoryPrice() == null){
							category.setRelCategoryPrice(new RelCategoryPrice());
						}
						relCategoryPrice.setCateSup(category.getRelCategoryPrice().getCateSup());
						relCategoryPrice.setAcptPri(category.getRelCategoryPrice().getAcptPri());
						relCategoryPrice.setWholesalePri(category.getRelCategoryPrice().getWholesalePri());
						relCategoryPrice.setSpotMin(category.getRelCategoryPrice().getSpotMin());
						relCategoryPrice.setSpotMax(category.getRelCategoryPrice().getSpotMax());
						relCategoryPrice.setInterFutMin(category.getRelCategoryPrice().getInterFutMin());
						relCategoryPrice.setInterFutMax(category.getRelCategoryPrice().getInterFutMax());
						relCategoryPrice.setFutMin(category.getRelCategoryPrice().getFutMin());
						relCategoryPrice.setFutMax(category.getRelCategoryPrice().getFutMax());
						relCategoryPrice.setCusLoc(category.getCusLoc());
						relCategoryPrice.setCategoryId(category.getCategoryId());
						relCategoryPrice.setCatePri(category.getOfferPri());
						relCategoryPrice.setOfferAging(category.getOfferAging());
						relCategoryPrice.setCusChaId(category.getCusCha());
						relCategoryPrice.setCreateUser(category.getUpdateUser());
						relCategoryPrice.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
						relCategoryPrice.setUpdateUser(category.getUpdateUser());
						relCategoryPrice.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
						relCategoryPriceMapper.insertSelective(relCategoryPrice);
					}else{
						for (int i=0; i<relCategoryPriceList.size(); i++){
							RelCategoryPrice item = relCategoryPriceList.get(i);
							
							if (category.getRelCategoryPrice() == null){
								category.setRelCategoryPrice(new RelCategoryPrice());
							}
							item.setCateSup(category.getRelCategoryPrice().getCateSup());
							item.setAcptPri(category.getRelCategoryPrice().getAcptPri());
							item.setWholesalePri(category.getRelCategoryPrice().getWholesalePri());
							item.setSpotMin(category.getRelCategoryPrice().getSpotMin());
							item.setSpotMax(category.getRelCategoryPrice().getSpotMax());
							item.setInterFutMin(category.getRelCategoryPrice().getInterFutMin());
							item.setInterFutMax(category.getRelCategoryPrice().getInterFutMax());
							item.setFutMin(category.getRelCategoryPrice().getFutMin());
							item.setFutMax(category.getRelCategoryPrice().getFutMax());
							item.setCatePri(category.getOfferPri());
							item.setOfferAging(category.getOfferAging());
							item.setUpdateUser(category.getUpdateUser());
							item.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
							relCategoryPriceMapper.updateByPrimaryKeySelective(item);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return 0;
	}

	@Override
	public List<Category> listPageByCustomerAndPrice(String customerId) {
		Category category = new Category ();
		category.setCreateUser(customerId);
		return categoryMapper.listPageByCustomerAndPrice(category);
	}

	@Override
	public Category custOfferPiceInfo(Category category) {
		return categoryMapper.custOfferPiceInfo(category);
	}

}
