/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhtop.buss.biz.mapper.CategoryMapper;
import com.rhtop.buss.biz.mapper.MemberMapper;
import com.rhtop.buss.biz.mapper.RelCategoryPriceMapper;
import com.rhtop.buss.biz.mapper.RelCustomerCategoryMapper;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.RelCustomerCategory;
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
		return categoryMapper.updateByPrimaryKeySelective(category);
	}
	
	@Override
	public Category selectByPrimaryKey(String categoryId){
		return categoryMapper.selectByPrimaryKey(categoryId);
	}
	
	public Category selectInfoByPrimaryKey(String categoryId){
		//品类信息
		Category cate = categoryMapper.selectByPrimaryKey(categoryId);
		//价格信息
		RelCategoryPrice relCategoryPrice = relCategoryPriceMapper.selectByCategoryId(categoryId);
		//防止cate为空，报空指针异常
		if(null==cate){
			return cate;
		}
		cate.setRelCategoryPrice(relCategoryPrice);
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
			catelist = categoryMapper.listPriceByMgr(memberId);
		}else if("02".equals(memberJob)){//分部经理
			//分部经理查询自己的信息采集情况（自己创建的五个价格需要自己填写）
			catelist = categoryMapper.listPriceByRegMgr(memberId);
		}else if("04".equals(memberJob)){//国际采购部
			//国际采购人员查看报盘情况(已报盘/未报盘)
			String isOffer = category.getIsOffer();
			if("00".equals(isOffer)){//未报盘
				catelist = categoryMapper.listNotPriceByUniMgr(memberId);
			}else{
				catelist = categoryMapper.listPriceByUniMgr(memberId);
			}
		}
		return catelist;
	}

	@Override
	public int insertExcelCategory(List<Category> categorys) {
		for(Category category:categorys){
			Category cate = new Category();
			cate.setCateName(category.getCateName());
			cate.setCateStan(category.getCateStan());
			cate.setManuNum(category.getManuNum());
			cate.setProdPla(category.getProdPla());
			List<Category> categoryList = categoryMapper.listCategorys(cate);
			if(categoryList.size()>0){
				category = categoryList.get(0);
			}else{
				categoryMapper.insertSelective(category);
			}
			RelCustomerCategory relcc = new RelCustomerCategory();
			relcc.setRelCustomerCategoryId(UUID.randomUUID().toString().replace("-", ""));
			relcc.setCategoryId(category.getCategoryId());
//			relcc.setCusChaId(cusChaId);
			relcc.setCusChaVal(category.getCusCha());
			relcc.setCreateUser(category.getUpdateUser());
			relcc.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			relcc.setUpdateUser(category.getUpdateUser());
			relcc.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			relCustomerCategoryMapper.insertSelective(relcc);
			
			RelCategoryPrice relCategoryPrice = new RelCategoryPrice();
			relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
			relCategoryPrice.setCategoryId(category.getCategoryId());
			relCategoryPrice.setCatePri(category.getOfferPri());
			relCategoryPrice.setCusChaVal(category.getCusCha());
			relCategoryPrice.setCreateUser(UUID.randomUUID().toString().replace("-", ""));
			relCategoryPrice.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			relCategoryPrice.setUpdateUser(UUID.randomUUID().toString().replace("-", ""));
			relCategoryPrice.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			relCategoryPriceMapper.insertSelective(relCategoryPrice);
		}
		return 0;
	}

	@Override
	public List<Category> listPageByCustomerAndPrice(String customerId) {
		Category category = new Category ();
		category.setCreateUser(customerId);
		return categoryMapper.listPageByCustomerAndPrice(category);
	}

}
