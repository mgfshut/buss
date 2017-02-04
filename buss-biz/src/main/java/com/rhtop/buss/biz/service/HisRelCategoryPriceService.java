/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import com.rhtop.buss.common.entity.RelCategoryPrice;

public interface HisRelCategoryPriceService{
    
	/**
	 * 复制RelCategoryPrice表
	 */
	int insertRelCategoryPrice(RelCategoryPrice relCategoryPrice) throws Exception;
    
	
}