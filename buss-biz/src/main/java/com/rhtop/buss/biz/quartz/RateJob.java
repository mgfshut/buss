package com.rhtop.buss.biz.quartz;



import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.rhtop.buss.biz.service.CodeValueService;
import com.rhtop.buss.biz.utils.JuheRate;
import com.rhtop.buss.common.utils.DateUtils;



public class RateJob extends QuartzJobBean{
	@Autowired
	private CodeValueService codeValueService;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
	}
	/**
	 * 获取汇率定时任务
	 * @author mgf
	 * @date 2017年1月10日 下午3:06:57
	 */
	public void getRate(){
		System.out.println("----------获取汇率定时任务start-------------");
		String usaRate = JuheRate.getRequestRate("美元");
		if(usaRate != null){
			codeValueService.updateTheCodeValue("rate","us",usaRate,DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		}
		System.out.println("----------获取汇率定时任务end-------------");
	}
}
