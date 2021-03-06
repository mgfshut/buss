/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.rhtop.buss.biz.mapper.CategoryMapper;
import com.rhtop.buss.biz.mapper.ContractInfoMapper;
import com.rhtop.buss.biz.mapper.CustomerMapper;
import com.rhtop.buss.biz.mapper.MemberMapper;
import com.rhtop.buss.biz.mapper.SlaTransactionInfoMapper;
import com.rhtop.buss.biz.mapper.TransactionInfoMapper;
import com.rhtop.buss.biz.service.ContractInfoService;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.SlaTransactionInfo;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.utils.PropertyUtil;

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
	@Transactional
	public int insertContractInfo(ContractInfo contractInfo) {
		return contractInfoMapper.insertSelective(contractInfo);
	}

	@Override
	public int deleteContractInfo(String contractInfoId) {
		return contractInfoMapper.deleteByPrimaryKey(contractInfoId);
	}

	@Override
	@Transactional
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
	@Transactional
	public String createContract(ContractInfo con) throws Exception {
		try {
			TransactionInfo tx = new TransactionInfo();
			tx.setTransactionInfoId(con.getTransactionInfoId());
			tx.setUpdateTime(con.getUpdateTime());
			tx.setUpdateUser(con.getUpdateUser());
			tx.setTxStatus("30");
			txMapper.updateByPrimaryKeySelective(tx);
			tx = txMapper.selectByPrimaryKey(con.getTransactionInfoId());
			con.setTxAmo(Float.parseFloat(tx.getTxAmo()));
			con.setCtofPri(tx.getCtofPri());
			con.setTotPri(Double.parseDouble(tx.getTxAmo())*tx.getCtofPri().doubleValue());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = new Date();
			String endTime = sdf.format(new Date(d.getTime()+Long.parseLong(tx.getCtofAging())*60*60*1000));
			con.setCategoryId(tx.getCategoryId());
			con.setEndTime(endTime);
			con.setCreateTime(con.getUpdateTime());
			con.setCreateUser(con.getUpdateUser());
			SlaTransactionInfo slaTx = slaTxMapper.selectLatestByTransactionInfoId(tx.getTransactionInfoId());
			slaTx.setUpdateUser(tx.getUpdateUser());
			slaTx.setUpdateTime(tx.getUpdateTime());
			slaTx.setCusAplSta("01");
			slaTxMapper.updateByPrimaryKeySelective(slaTx);
			String conId = UUID.randomUUID().toString().replace("-", "");
			con.setContractInfoId(conId);
			con.setContStatus("10");
			
			
			contractInfoMapper.insertSelective(con);
			return conId;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private static final String CON_CODE_PREFIX = "RH-XS";
	private static final String MID_SEP = "-";
	/**
	 * 生成合同编码
	 * 生码规则：RH-XS-20161214(合同处理日期）-A（A为销售合同，B为预售合同）-0001（流水号）
	 * @param contractType 
	 * @param lastConCode  最新的合同编号
	 * @return
	 * @throws ParseException 
	 */
	private String generateConCode(String contractType, String latestConCode) throws ParseException {
		
		contractType = "A"; //目前只有销售合同，以后可能需要修改
		int latestId = 0;
		
		String nowDateStr = DateUtils.getNowTime("yyyyMMdd");
		
		if(null != latestConCode){
			
			//从latestConCode中截取日期
			String conCreateDateStr = latestConCode.substring(CON_CODE_PREFIX.length()+1, CON_CODE_PREFIX.length()+1+8);
			
			//对比两个日期是否一致 
			if(nowDateStr.equals(conCreateDateStr)){
				//从latestConCode中截取latestId
				String latestIdStr = latestConCode.substring(latestConCode.length() - 4);
				//转化为int类型
				latestId = Integer.valueOf(latestIdStr);
			}
		}
		
		String flowCode = String.format("%04d", ++latestId);
		
		StringBuffer conCode = new StringBuffer(CON_CODE_PREFIX);
		conCode.append(MID_SEP).append(nowDateStr)
		.append(MID_SEP).append(contractType)
		.append(MID_SEP).append(flowCode);
		
		return conCode.toString();
	}

	@Override
	@Transactional
	public String checkContract(ContractInfo con) throws Exception {
		String conId = con.getContractInfoId();
		try {
			ContractInfo contract = contractInfoMapper.selectByPrimaryKey(conId);
			if(contract==null){
				throw new Exception("非法操作，合同记录不存在！");
			}
			//检查审核状态是否是“10”
			if(contract.getContStatus()=="10"||contract.getContStatus().trim().equals("10")){
				con.setContStatus("20");
				con.setGenckTime(DateUtils.getNowTime());

				//查询最新的ContractInfo，为了生成最新的合同编号
				ContractInfo contractInfo= contractInfoMapper.selectLatestContract();
				String conCode = null;
				if(null == contractInfo) {
					conCode = generateConCode(null, null);
				} else {
					conCode = generateConCode(null, contractInfo.getConCode());
				}
				con.setConCode(conCode);
				contractInfoMapper.updateByPrimaryKeySelective(con);
				TransactionInfo tx = txMapper.selectByPrimaryKey(contract.getTransactionInfoId());
				tx.setTxStatus("40");
				txMapper.updateByPrimaryKeySelective(tx);
			}else if(contract.getContStatus()=="11"||contract.getContStatus().trim().equals("11")||
					contract.getContStatus()=="21"||contract.getContStatus().trim().equals("21")){
				//检查合同状态是否为11，即总经理驳回状态。
				con.setContStatus("20");
				con.setGenckTime(DateUtils.getNowTime());
				//因为合同编号可能存在，所以需要检查刚刚查出来的合同对象中合同编号是否存在。
				if(contract.getConCode()!=null||!"".equals(contract.getConCode().trim())){
					contractInfoMapper.updateByPrimaryKeySelective(con);
					TransactionInfo tx = txMapper.selectByPrimaryKey(contract.getTransactionInfoId());
					tx.setTxStatus("40");
					txMapper.updateByPrimaryKeySelective(tx);
				}else{
					//查询最新的ContractInfo，为了生成最新的合同编号
					ContractInfo contractInfo= contractInfoMapper.selectLatestContract();
					String conCode = null;
					if(null == contractInfo) {
						conCode = generateConCode(null, null);
					} else {
						conCode = generateConCode(null, contractInfo.getConCode());
					}
					con.setConCode(conCode);
					contractInfoMapper.updateByPrimaryKeySelective(con);
					TransactionInfo tx = txMapper.selectByPrimaryKey(contract.getTransactionInfoId());
					tx.setTxStatus("40");
					txMapper.updateByPrimaryKeySelective(tx);
				}
			}else{
				throw new Exception("非法操作，审核顺序错误！");
			}
		} catch (Exception e) {
			throw e;
		}
		return conId;
	}

	@Override
	@Transactional
	public String contractStamp(ContractInfo con) throws Exception{
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
			if (info != null){
				String[] urls = info.getContUlName().split(",");
				PropertyUtil propertyUtil = new PropertyUtil("properties/common.properties");
				String contractUrlPerfix = propertyUtil.readValue("picUrlPerfix");
				String temp = null;
				for(String url : urls){
					temp = contractUrlPerfix+url;
					urlList.add(temp);
				}
			}else{
				//throw new Exception("合同信息不存在！");
			}
			/*for(int i=0; i<5; i++){
				urlList.add("http://192.168.10.63:9090/img/index-backgroud.jpg");
			}*/
		} catch (Exception e) {
			log.error("电子合同下载请求异常", e);
			//throw new Exception("合同信息不存在！");
		}
		return urlList;
	}

	@Override
	@Transactional
	public String treasurerCheckContract(ContractInfo con) throws Exception{
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
				throw new Exception("非法操作，审核顺序错误！");
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
		//contract.setOfferPri(category.getOfferPri());
		contract.setCateName(category.getCateName());
		//最终成交价格除以数量换算出单价
		DecimalFormat dec = new DecimalFormat("0.00");
		//将吨转换为千克
		contract.setTxAmo(contract.getTxAmo().floatValue() * 1000);
		contract.setTotPriBig(new BigDecimal(contract.getTotPri()));
		contract.setOfferPri(new Float(dec.format(contract.getTotPriBig().doubleValue() / contract.getTxAmo())));
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

	@Override
	public List<ContractInfo> listPageContractInfoByXZStatus( ContractInfo contractInfo) {
		List<ContractInfo> contractInfos = contractInfoMapper.listPageContractInfoByXZStatus(contractInfo);
		return contractInfos;
	}

	@Override
	public List<ContractInfo> listPageContractInfoByCWStatus(ContractInfo contractInfo) {
		List<ContractInfo> contractInfos = contractInfoMapper.listPageContractInfoByCWStatus(contractInfo);
		return contractInfos;
	}

	@Override
	@Transactional
	public String dismissContract(ContractInfo contractinfo) throws Exception {
		String conId = contractinfo.getContractInfoId();
		ContractInfo con = contractInfoMapper.selectByPrimaryKey(conId);
		try {
		//检查合同审核状态是否是“10”
		if (con.getContStatus() == "10" || con.getContStatus().trim().equals("10")) {
			con.setContStatus("11");
			con.setDmRea(contractinfo.getDmRea());
			con.setUpdateUser(contractinfo.getUpdateUser());
			con.setUpdateTime(contractinfo.getUpdateTime());
			contractInfoMapper.updateByPrimaryKeySelective(con);
		} else {
			throw new Exception("非法操作，审核顺序错误10！");
		}
		TransactionInfo  tran = txMapper.selectByPrimaryKey(con.getTransactionInfoId());
		if(tran ==null){
			throw new Exception("非法操作，交易记录不存在！");
		}
		//检查交易状态是否是“30”
		if (tran.getTxStatus() == "30" || tran.getTxStatus().trim().equals("30")) {
			tran.setTxStatus("31");
			tran.setUpdateUser(contractinfo.getUpdateUser());
			tran.setUpdateTime(contractinfo.getUpdateTime());
			txMapper.updateByPrimaryKeySelective(tran);
		} else {
			throw new Exception("非法操作，审核顺序错误30！");
		}
		}catch(Exception e){
			throw e;
		}
		return conId;
	}

	@Override
	@Transactional
	public String dismissContractByXZ(ContractInfo contractinfo) throws Exception {
		String conId = contractinfo.getContractInfoId();
		ContractInfo con = contractInfoMapper.selectByPrimaryKey(conId);
		try {
		//检查合同审核状态是否是“20”
		if (con.getContStatus() == "20" || con.getContStatus().trim().equals("20")) {
			con.setContStatus("21");
			con.setUpdateUser(contractinfo.getUpdateUser());
			con.setUpdateTime(contractinfo.getUpdateTime());
			con.setDmRea(contractinfo.getDmRea());
			contractInfoMapper.updateByPrimaryKeySelective(con);
		} else {
			throw new Exception("非法操作，审核顺序错误！");
		}
		}catch(Exception e){
			throw e;
		}
		return conId;
	}
	
}