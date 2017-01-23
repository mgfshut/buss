package com.rhtop.buss.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhtop.buss.biz.mapper.CusckLogMapper;
import com.rhtop.buss.biz.mapper.CustomerMapper;
import com.rhtop.buss.biz.mapper.RelCategoryPriceMapper;
import com.rhtop.buss.biz.service.CusckLogService;
import com.rhtop.buss.common.entity.CusckLog;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.RelCategoryPrice;

@Service("cusckLogService")
public class CusckLogServiceImpl implements CusckLogService {
	@Autowired
	private CusckLogMapper cusckLogMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private RelCategoryPriceMapper relCategoryPriceMapper;
	
	@Override
	public int insertCusckLog(CusckLog cusckLog) {
		return cusckLogMapper.insertSelective(cusckLog);
	}

	@Override
	public int deleteCusckLog(String cusckLogId) {
		return cusckLogMapper.deleteByPrimaryKey(cusckLogId);
	}

	@Override
	public CusckLog selectByPrimaryKey(String cusckLogId) {
		return cusckLogMapper.selectByPrimaryKey(cusckLogId);
	}

	@Override
	public List<CusckLog> listBusinessDiarys(CusckLog cusckLog) {
		List<CusckLog> cusckLogList = cusckLogMapper.listCusckLogs(cusckLog);
		return cusckLogList;
	}

	@Override
	public List<CusckLog> listPageBusinessDiary(CusckLog cusckLog) {
		List<CusckLog> cusckLogs = cusckLogMapper.listPageCusckLog(cusckLog);
		return cusckLogs;
	}
	
	@Override
	public List<Customer> cusCkLogCustomer(Customer customer) {
		//得到客户的信息(分页减少对数据库的访问)
		List<Customer> custs = customerMapper.listPageCustomer(customer);
		//根据客户id得到对客户的操作记录（for循环得到操作记录）
		for(Customer cu:custs){
			CusckLog cusckLog = new CusckLog();
			cusckLog.setCustomerId(cu.getCustomerId());
			List<CusckLog>  cusckLogs = cusckLogMapper.listPageCusckCustomer(cusckLog);
			//将操作记录装到Customer中
			cu.setCusckLogs(cusckLogs);
		}
		return custs;
	}

	@Override
	public List<RelCategoryPrice> cusCkLogRelCategoryPrice(RelCategoryPrice relCategoryPrice) {
		 List<RelCategoryPrice> relCatePrices = relCategoryPriceMapper.listPagePriceGroupBycusCha(relCategoryPrice);
		return relCatePrices;
	}
}
