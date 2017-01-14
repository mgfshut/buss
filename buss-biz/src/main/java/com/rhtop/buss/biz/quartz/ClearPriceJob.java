package com.rhtop.buss.biz.quartz;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.HisRelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.RelCategoryPrice;

public class ClearPriceJob  extends QuartzJobBean{

	/**
	 * 1.定时 将价格表中的数据导入价格从表中
	 * 2.将价格主表的价格清零，以及留下品类主键，还有品类表中报价和报价时效赋值空
	 */
	@Autowired
	private RelCategoryPriceService relCategoryPriceService;
	@Autowired
	private HisRelCategoryPriceService hisRelcategoryPriceService;
	@Autowired
	private CategoryService categoryService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}
	
	public void clearPrice(){
		//得到记录
		List<RelCategoryPrice>  rels = relCategoryPriceService.listRelCategoryPrices(new RelCategoryPrice());
		for(RelCategoryPrice rel:rels){
			//复制记录
			hisRelcategoryPriceService.insertRelCategoryPrice(rel);
		}
		//删除主表记录
		for(RelCategoryPrice rel:rels){
			relCategoryPriceService.updateSelective(rel);
			Category category = new Category();
			category.setCategoryId(rel.getCategoryId());
			category.setOfferAging("0");//将报盘价格报价时效赋值为0
			category.setOfferPri(0f);
			categoryService.updateCategory(category);
		}
	}
	
}
