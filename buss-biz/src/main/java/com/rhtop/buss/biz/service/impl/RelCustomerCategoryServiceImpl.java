/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.rhtop.buss.common.entity.RelCustomerCategory;
import com.rhtop.buss.biz.mapper.RelCustomerCategoryMapper;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;

@Service("relCustomerCategoryService")
public class RelCustomerCategoryServiceImpl implements RelCustomerCategoryService {
	@Autowired
	private RelCustomerCategoryMapper relCustomerCategoryMapper;
	
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
	public List<RelCustomerCategory> listRelCustomerCategorys(RelCustomerCategory relCustomerCategory) {
		List<RelCustomerCategory> relCustomerCategoryList = relCustomerCategoryMapper.listRelCustomerCategorys(relCustomerCategory);
		return relCustomerCategoryList;
	}
	
	@Override
	public List<RelCustomerCategory> listPageRelCustomerCategory(RelCustomerCategory relCustomerCategory) {
		List<RelCustomerCategory> relCustomerCategorys = relCustomerCategoryMapper.listPageRelCustomerCategory(relCustomerCategory);
		return relCustomerCategorys;
	}

}