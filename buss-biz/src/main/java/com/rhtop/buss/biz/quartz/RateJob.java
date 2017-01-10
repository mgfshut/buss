package com.rhtop.buss.biz.quartz;



import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;



public class RateJob extends QuartzJobBean{
	

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
		System.out.println("----------获取汇率定时任务-------------");
	}
}
