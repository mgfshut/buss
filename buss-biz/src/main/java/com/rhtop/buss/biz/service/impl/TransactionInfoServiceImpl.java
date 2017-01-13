/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.rhtop.buss.common.entity.SlaTransactionInfo;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.biz.mapper.SlaTransactionInfoMapper;
import com.rhtop.buss.biz.mapper.TransactionInfoMapper;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.ContractInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.SlaTransactionInfoService;
import com.rhtop.buss.biz.service.TransactionInfoService;

@Service("transactionInfoService")
public class TransactionInfoServiceImpl implements TransactionInfoService {
	
	@Autowired
	private CategoryService catSer;
	@Autowired
	private CustomerService cusSer;
	@Autowired
	private SlaTransactionInfoService slaSer; 
	@Autowired
	private ContractInfoService conSer;
	@Autowired
	private TransactionInfoMapper transactionInfoMapper;
	@Autowired
	private SlaTransactionInfoMapper slaTxMapper;
	
	
	
	@Override
	public int insertTransactionInfo(TransactionInfo transactionInfo) {
		return transactionInfoMapper.insertSelective(transactionInfo);
	}
	@Override
	public String createDeal(TransactionInfo tx){
		String transactionInfoId = null;
		String slaTransactionInfoId = null;
		if(tx.getTransactionInfoId()==null||tx.getTransactionInfoId().trim().equals("")){
			transactionInfoId = UUID.randomUUID().toString().replace("-", "");
			String now = tx.getUpdateTime();
			String userId = tx.getUpdateUser();
			tx.setTransactionInfoId(transactionInfoId);
			tx.setCreateUser(userId);
			tx.setCreateTime(now);
			tx.setPcasTime(now);
//			if(tx.getPcasPri()==null||tx.getPcasPri().trim().equals("")){
//				//如果没有填写价格，默认他是发起交易。
//				tx.setTxStatus("10");
//			}else{
				//填写了价格，认为它是询价中。
				tx.setTxStatus("20");//现在先丢掉“发起交易”这个状态
//			}
			transactionInfoMapper.insertSelective(tx);
			SlaTransactionInfo slaTx = new SlaTransactionInfo();
			slaTransactionInfoId = UUID.randomUUID().toString().replace("-", "");
			slaTx.setSlaTransactionInfoId(slaTransactionInfoId);
			slaTx.setCreateTime(now);
			slaTx.setUpdateTime(now);
			slaTx.setCreateUser(userId);
			slaTx.setUpdateUser(userId);
			slaTx.setTransactionInfoId(transactionInfoId);
			slaTx.setPcasPri(tx.getPcasPri());
			slaTx.setPcasTime(now);
			slaTx.setTxAmo(tx.getTxAmo());
			slaTx.setCusAplSta("00");//00表示未接受，01表示接受。
			slaTxMapper.insertSelective(slaTx);
		}else{
			throw new RuntimeException("交易记录已存在！");
		}
		return transactionInfoId;
	}
	
	@Override
	public String cusNegotiate(TransactionInfo tx){
		tx.setTxStatus("20");
		String transactionInfoId = tx.getTransactionInfoId();
		transactionInfoMapper.updateByPrimaryKeySelective(tx);
		SlaTransactionInfo slaTx = new SlaTransactionInfo();
		String slaTransactionInfoId = UUID.randomUUID().toString().replace("-", "");
		slaTx.setSlaTransactionInfoId(slaTransactionInfoId);
		String now = tx.getUpdateTime();
		String userId = tx.getUpdateUser();
		slaTx.setCreateTime(now);
		slaTx.setUpdateTime(now);
		slaTx.setCreateUser(userId);
		slaTx.setUpdateUser(userId);
		slaTx.setTransactionInfoId(transactionInfoId);
		slaTx.setPcasPri(tx.getPcasPri());
		slaTx.setPcasTime(now);
		slaTx.setTxAmo(tx.getTxAmo());
		slaTxMapper.insertSelective(slaTx);
		return transactionInfoId;
	}
	
	@Override
	public String universeNegotiate(TransactionInfo tx){
		String transactionInfoId = tx.getTransactionInfoId();
		transactionInfoMapper.updateByPrimaryKeySelective(tx);
		SlaTransactionInfo slaTx = slaTxMapper.selectLatestByTransactionInfoId(transactionInfoId);
		String now = tx.getUpdateTime();
		String userId = tx.getUpdateUser();
		slaTx.setUpdateTime(now);
		slaTx.setUpdateUser(userId);
		slaTx.setCtofTime(now);
		slaTx.setCtofAging(tx.getCtofAging());
		slaTx.setCtofPerId(userId);
		slaTx.setUniCtofPri(tx.getCtofPri());
		slaTx.setCtofCkSta("00");
		slaTxMapper.updateByPrimaryKeySelective(slaTx);
		return transactionInfoId;
	}
	
	@Override
	public String domainNegotiate(TransactionInfo tx){
		String transactionInfoId = tx.getTransactionInfoId();
		String userId = tx.getUpdateUser();
		transactionInfoMapper.updateByPrimaryKeySelective(tx);
		SlaTransactionInfo slaTx = slaTxMapper.selectLatestByTransactionInfoId(transactionInfoId);
		String now = tx.getUpdateTime();
		slaTx.setCtofCkSta("22");
		slaTx.setCtofCkPer(userId);
		slaTx.setCtofCkTime(now);
		slaTx.setDomCtofPri(tx.getCtofPri());
		slaTx.setCtofAging(tx.getCtofAging());
		slaTx.setUpdateTime(now);
		slaTx.setUpdateUser(userId);
		slaTxMapper.updateByPrimaryKeySelective(slaTx);
		return transactionInfoId;
	}

	@Override
	public int deleteTransactionInfo(String transactionInfoId) {
		return transactionInfoMapper.deleteByPrimaryKey(transactionInfoId);
	}

	@Override
	public int updateTransactionInfo(TransactionInfo transactionInfo) {
		return transactionInfoMapper.updateByPrimaryKeySelective(transactionInfo);
	}
	
	@Override
	public TransactionInfo selectByPrimaryKey(String transactionInfoId){
		TransactionInfo tra= transactionInfoMapper.selectByPrimaryKey(transactionInfoId);
		//查询该交易的品类详情
		Category cate = catSer.selectByPrimaryKey(tra.getCategoryId());
		//查询该交易的客户详情
		Customer cust = cusSer.selectByPrimaryKey(tra.getCustomerId());
		//回盘信息
		SlaTransactionInfo slatransaction = new SlaTransactionInfo();
		slatransaction.setTransactionInfoId(transactionInfoId);
		List<SlaTransactionInfo> sla = slaSer.listSlaTransactionInfos(slatransaction);
		//合同信息	
		ContractInfo contractinfo = new ContractInfo();
		contractinfo.setTransactionInfoId(transactionInfoId);
		ContractInfo con = conSer.listContractInfos(contractinfo).get(0);
		
		tra.setSla(sla);
		tra.setCate(cate);
		tra.setCust(cust);
		tra.setContract(con);
		return tra;
	}

	@Override
	public List<TransactionInfo> listTransactionInfos(TransactionInfo transactionInfo) {
		List<TransactionInfo> transactionInfoList = transactionInfoMapper.listTransactionInfos(transactionInfo);
		return transactionInfoList;
	}
	
	@Override
	public List<TransactionInfo> listPageTransactionInfo(TransactionInfo transactionInfo) {
		List<TransactionInfo> transactionInfos = transactionInfoMapper.listPageTransactionInfo(transactionInfo);
		return transactionInfos;
	}
	
	@Override
	public List<TransactionInfo> listPageTransactionInfoBycreateUser(TransactionInfo transactionInfo) {
		List<TransactionInfo> transactionInfos = transactionInfoMapper.listPageTransactionInfo(transactionInfo);
		return transactionInfos;
	}

}
