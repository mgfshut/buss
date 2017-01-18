/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.RelCustomerCategory;
import com.rhtop.buss.biz.mapper.MemberMapper;
import com.rhtop.buss.biz.mapper.RelCustomerCategoryMapper;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;

@Service("relCustomerCategoryService")
public class RelCustomerCategoryServiceImpl implements RelCustomerCategoryService {
	@Autowired
	private RelCustomerCategoryMapper relCustomerCategoryMapper;
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public int insertRelCustomerCategory(RelCustomerCategory relCustomerCategory) {
		return relCustomerCategoryMapper.insertSelective(relCustomerCategory);
	}

	@Override
	public int deleteRelCustomerCategory(String relCustomerCategoryId) {
		return relCustomerCategoryMapper.deleteByPrimaryKey(relCustomerCategoryId);
	}

	@Override
	public int updateRelCustomerCategory(RelCustomerCategory relCustomerCategory) {
		return relCustomerCategoryMapper.updateByPrimaryKeySelective(relCustomerCategory);
	}
	
	@Override
	public RelCustomerCategory selectByPrimaryKey(String relCustomerCategoryId){
		return relCustomerCategoryMapper.selectByPrimaryKey(relCustomerCategoryId);
	}
	
	@Override
	public RelCustomerCategory selectByPrimaryParam(RelCustomerCategory relCustomerCategory){
		return relCustomerCategoryMapper.selectByPrimaryParam(relCustomerCategory);
	}

	@Override
	public List<RelCustomerCategory> listRelCustomerCategorys(RelCustomerCategory relCustomerCategory) {
		List<RelCustomerCategory> relCustomerCategoryList = relCustomerCategoryMapper.listRelCustomerCategorys(relCustomerCategory);
		return relCustomerCategoryList;
	}
	
	@Override
	public List<RelCustomerCategory> listPageRelCustomerCategory(RelCustomerCategory relCustomerCategory) {
		List<RelCustomerCategory> relCustomerCategorys = relCustomerCategoryMapper.listPageRelCustomerCategory(relCustomerCategory);
		return relCustomerCategorys;
	}

	@Override
	public List<RelCustomerCategory> categoryExportList(Category category) {
		return relCustomerCategoryMapper.categoryExportList(category);
	}

	@Override
	public List<RelCustomerCategory> selectCuscha(Category category) {
		List<RelCustomerCategory> rels = null;
		//判断用户
		String memberJob = memberMapper.selectByPrimaryKey(category.getCreateUser()).getMemberJob();
		RelCustomerCategory	relCustomerCategory = new RelCustomerCategory();
		relCustomerCategory.setCreateUser(category.getCreateUser());
		relCustomerCategory.setCategoryId(category.getCategoryId());
		if("01".equals(memberJob)){
			rels =	relCustomerCategoryMapper.selectCuschaByMgr(relCustomerCategory);
		}else{ 
			rels =	relCustomerCategoryMapper.selectCuschaByRegMgr(relCustomerCategory);
		}
		return rels;
	}

}