/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhtop.buss.biz.mapper.CategoryMapper;
import com.rhtop.buss.biz.mapper.RelCategoryPriceMapper;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.RelCategoryPrice;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private RelCategoryPriceMapper relCategoryPriceMapper;
	
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
		//品类信息
		Category cate = categoryMapper.selectInfoByPrimaryKey(categoryId);
		//价格信息
		RelCategoryPrice relCategoryPrice = relCategoryPriceMapper.selectByCategoryId(categoryId);
		cate.setRelCategoryPrice(relCategoryPrice);
		cate.setCatePic("" + cate.getCatePic());//需要返回的是图片在服务器上的绝对路径
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
	public List<Category> listPageCategoeyByPrice(String memberId) {
		List<Category> categorys = null;
		//判断memberId是否为空,
		if("".equals(memberId)){//查询所有未采集（接盘价和批发价）的品类信息
			   categorys = categoryMapper.listPageNotPrice(memberId);
		}else{//查询客户经理采集的品类信息
			  categorys = categoryMapper.listPagePrice(memberId);
		}
		return categorys;
	}

}