/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.SlaTransactionInfo;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.utils.PropertyUtil;
import com.rhtop.buss.biz.mapper.CategoryMapper;
import com.rhtop.buss.biz.mapper.ContractInfoMapper;
import com.rhtop.buss.biz.mapper.CustomerMapper;
import com.rhtop.buss.biz.mapper.MemberMapper;
import com.rhtop.buss.biz.mapper.SlaTransactionInfoMapper;
import com.rhtop.buss.biz.mapper.TransactionInfoMapper;
import com.rhtop.buss.biz.service.ContractInfoService;

@Service("contractInfoService")
public class ContractInfoServiceImpl implements ContractInfoService {
	@Autowired
	private ContractInfoMapper contractInfoMapper;
	@Autowired
	private TransactionInfoMapper txMapper; 
	@Autowired
	private SlaTransactionInfoMapper slaTxMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	protected Logger  log = LoggerFactory.getLogger("error");
	
	@Override
	public int insertContractInfo(ContractInfo contractInfo) {
		return contractInfoMapper.insertSelective(contractInfo);
	}

	@Override
	public int deleteContractInfo(String contractInfoId) {
		return contractInfoMapper.deleteByPrimaryKey(contractInfoId);
	}

	@Override
	public int updateContractInfo(ContractInfo contractInfo) {
		return contractInfoMapper.updateByPrimaryKeySelective(contractInfo);
	}
	
	@Override
	public ContractInfo selectByPrimaryKey(String contractInfoId){
		return contractInfoMapper.selectByPrimaryKey(contractInfoId);
	}

	@Override
	public List<ContractInfo> listContractInfos(ContractInfo contractInfo) {
		List<ContractInfo> contractInfoList = contractInfoMapper.listContractInfos(contractInfo);
		return contractInfoList;
	}
	
	@Override
	public List<ContractInfo> listPageContractInfo(ContractInfo contractInfo) {
		List<ContractInfo> contractInfos = contractInfoMapper.listPageContractInfo(contractInfo);
		return contractInfos;
	}

	@Override
	public String createContract(ContractInfo con) {
		try {
			TransactionInfo tx = new TransactionInfo();
			tx.setTransactionInfoId(con.getTransactionInfoId());
			tx.setUpdateTime(con.getUpdateTime());
			tx.setUpdateUser(con.getUpdateUser());
			tx.setTxStatus("30");
			txMapper.updateByPrimaryKeySelective(tx);
			SlaTransactionInfo slaTx = slaTxMapper.selectLatestByTransactionInfoId(tx.getTransactionInfoId());
			slaTx.setUpdateUser(tx.getUpdateUser());
			slaTx.setUpdateTime(tx.getUpdateTime());
			slaTx.setCusAplSta("01");
			slaTxMapper.updateByPrimaryKeySelective(slaTx);
			String conId = UUID.randomUUID().toString().replace("-", "");
			con.setContractInfoId(conId);
			con.setEndTime(tx.getEndTime());
			con.setContStatus("10");
			contractInfoMapper.insertSelective(con);
			return conId;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String checkContract(ContractInfo con) {
		String conId = con.getContractInfoId();
		try {
			ContractInfo contract = contractInfoMapper.selectByPrimaryKey(conId);
			if(contract==null){
				throw new RuntimeException("非法操作，合同记录不存在！");
			}
			//检查审核状态是否是“10”
			if(contract.getContStatus()=="10"||contract.getContStatus().trim().equals("10")){
				con.setContStatus("20");
				con.setGenckTime(DateUtils.getNowTime());
				contractInfoMapper.updateByPrimaryKeySelective(con);
			}else{
				throw new RuntimeException("非法操作，审核顺序错误！");
			}
		} catch (Exception e) {
			throw e;
		}
		return conId;
	}

	@Override
	public String contractStamp(ContractInfo con) {
		String conId = con.getContractInfoId();
		try {
			ContractInfo contract = contractInfoMapper.selectByPrimaryKey(conId);
			if(contract==null){
				throw new RuntimeException("非法操作，合同记录不存在！");
			}
			//检查审核状态是否是“20”
			if(contract.getContStatus()=="20"||contract.getContStatus().trim().equals("20")){
				con.setContStatus("30");
				contractInfoMapper.updateByPrimaryKeySelective(con);
			}else{
				throw new RuntimeException("非法操作，审核顺序错误！");
			}
		} catch (Exception e) {
			throw e;
		}
		return conId;
	}
	
	@Override
	public List<String> downloadContract(ContractInfo con) {
		List<String> urlList = new ArrayList<String>();
		ContractInfo info = contractInfoMapper.selectByPrimaryKey(con.getContractInfoId());
		
		try {
			if (info == null){
				String[] urls = info.getContUlName().split("|");
				/*PropertyUtil propertyUtil = new PropertyUtil("properties/common.properties");
				String contractUrlPerfix = propertyUtil.readValue("contractUrlPerfix");
				String temp = null;
				for(String url : urls){
					temp = contractUrlPerfix+url;
					urlList.add(temp);
				}*/
			}else{
				//throw new Exception("合同信息不存在！");
			}
			for(int i=0; i<5; i++){
				urlList.add("http://192.168.10.63:9090/img/index-backgroud.jpg");
			}
		} catch (Exception e) {
			log.error("电子合同下载请求异常", e);
			//throw new Exception("合同信息不存在！");
		}
		return urlList;
	}

	@Override
	public String treasurerCheckContract(ContractInfo con) {
		String conId = con.getContractInfoId();
		try {
			ContractInfo contract = contractInfoMapper.selectByPrimaryKey(conId);
			if(contract==null){
				throw new RuntimeException("非法操作，合同记录不存在！");
			}
			//检查审核状态是否是“30”
			if(contract.getContStatus()=="30"||contract.getContStatus().trim().equals("30")){
				con.setContStatus("40");
				contractInfoMapper.updateByPrimaryKeySelective(con);
			}else{
				throw new RuntimeException("非法操作，审核顺序错误！");
			}
		} catch (Exception e) {
			throw e;
		}
		return conId;
	}

	@Override
	public ContractInfo printByContractInfoId(String contractInfoId) {
		ContractInfo contract = contractInfoMapper.selectByPrimaryKey(contractInfoId);
		Customer customer = customerMapper.selectByPrimaryKey(contract.getCustomerId());
		contract.setBuyName(customer.getCusName());
		Category category = categoryMapper.selectByPrimaryKey(contract.getCategoryId());
		contract.setManuNum(category.getManuNum());
		contract.setComm(category.getComm());
		contract.setCateStan(category.getCateStan());
		contract.setOfferPri(category.getOfferPri());
		if("02".equals(contract.getDelvOpt())){
			contract.setZtcsgName(contract.getCsgName());
			contract.setZtcsgTel(contract.getCsgTel());
			contract.setZtcsgAddr(contract.getCsgAddr());
			contract.setZtcsgId(contract.getCsgId());
			contract.setCsgName("");
			contract.setCsgTel("");
			contract.setCsgAddr("");
			contract.setCsgId("");
		}else{
			contract.setZtcsgName("");
			contract.setZtcsgTel("");
			contract.setZtcsgAddr("");
			contract.setZtcsgId("");
			contract.setCarNum("");
			contract.setDriNum("");
		}
		return contract;
	}
	

}