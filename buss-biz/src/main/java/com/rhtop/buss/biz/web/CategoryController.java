package com.rhtop.buss.biz.web;


import java.util.UUID;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveCategory(@Valid @RequestParam(value = "userId") String userId,@Valid Category category){
		if(category.getCategoryId() == null || "".equals(category.getCategoryId())){
			String categoryId = UUID.randomUUID().toString().replace("-", "");
			category.setCategoryId(categoryId);
			category.setCreateUser(userId);
			category.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			category.setUpdateUser(userId);
			category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			categoryService.insertCategory(category);
		}else{
			category.setUpdateUser(userId);
			category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			categoryService.updateCategory(category);
		}
		return new HtmlMessage(category);
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteCategory")
	public InfoResult<Category> deleteCategory(@RequestParam("categoryId") String categoryId){
		InfoResult<Category> infoResult = new InfoResult<Category>();
		int num = categoryService.deleteCategory(categoryId);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("删除成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("删除失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
     * 修改
     */
	@ResponseBody
	@RequestMapping("/updateCategory")
	public InfoResult<Category> updateCategory(Category category){
		InfoResult<Category> infoResult = new InfoResult<Category>();
		category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = categoryService.updateCategory(category);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("修改成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("修改失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
	 * 根据条件分页查询信息列表
	 */
	@ResponseBody
	@RequestMapping(value="/pager")
	public InfoResult<Category> listPageCategory(Page page,Category category){
		InfoResult<Category> infoResult = new InfoResult<Category>();
		category.setPage(page);
		List<Category> categoryList = categoryService.listPageCategory(category);
		infoResult.setCode("200");
		infoResult.setResList(categoryList);
		infoResult.setPage(category.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<Category> selectByPrimaryKey(@RequestParam("categoryId") String categoryId) {
		InfoResult<Category> infoResult = new InfoResult<Category>();
		infoResult.setCode("200");
		Category category = categoryService.selectByPrimaryKey(categoryId);
		infoResult.setResObject(category);
		return infoResult;
	}
}