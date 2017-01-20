package com.rhtop.buss.common.utils;

import java.math.BigDecimal;

public class UnitUtils {
	
	/**
	 * 转换成元/吨
	 * @author mgf
	 * @date 2017年1月18日 上午9:31:43 
	 * @param currency 币种(人民币 美元)
	 * @param monery 金额
	 * @param weight 重量单位(千克  磅)
	 * @param rate 汇率
	 * @return
	 */
	public static BigDecimal unitConver(String  currency,BigDecimal monery,String weight,String rate){
		BigDecimal b_unitConver = new BigDecimal(1);
		if("01".equals(currency)){
			monery = monery.multiply(new BigDecimal(rate));
		}
		
		if("01".equals(weight)){
			//千克
			b_unitConver = monery.multiply(BigDecimal.valueOf(1000));
		}else{
			//磅
			b_unitConver = monery.multiply(BigDecimal.valueOf(2204.6226)).
					setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return b_unitConver;
	}

}
