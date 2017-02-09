package com.rhtop.buss.biz.quartz;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.rhtop.buss.biz.service.TransactionInfoService;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.TransactionInfo;

public class TransactionTimeJob extends QuartzJobBean {

	@Autowired
	private TransactionInfoService transactionInfoService;
	protected Logger  log = LoggerFactory.getLogger("error");
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub

	}

	/**
	 * 把超过交易时间的交易的状态置为交易关闭“60”
	 * 
	 * @author lujin
	 * @date 2017-2-9
	 * 
	 */
	public void doFailure() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 1.查2.判断3.改
		TransactionInfo transactioninfo = new TransactionInfo();
		List<TransactionInfo> trans = transactionInfoService.listByTxStatus(transactioninfo);
		for (TransactionInfo tran : trans) {
			try {
				long diff = df.parse(tran.getEndTime()).getTime() - new Date().getTime();
				if (diff < 0) {
					tran.setTxStatus("60");
					tran.setUpdateTime(df.format(new Date()));
					tran.setUpdateUser("系统");
					transactionInfoService.updateTransactionInfo(tran);
				}
			} catch (ParseException e1) {
				log.error("定时任务时间判断异常"+e1.getMessage());
			} catch (Exception e) {
				log.error("定时任务修改异常"+e.getMessage());
			}
		}
	}

}
