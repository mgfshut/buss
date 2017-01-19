/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.SlaTransactionInfo;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.biz.mapper.CategoryMapper;
import com.rhtop.buss.biz.mapper.ContractInfoMapper;
import com.rhtop.buss.biz.mapper.CustomerMapper;
import com.rhtop.buss.biz.mapper.MemberMapper;
import com.rhtop.buss.biz.mapper.RelCategoryPriceMapper;
import com.rhtop.buss.biz.mapper.SlaTransactionInfoMapper;
import com.rhtop.buss.biz.mapper.TransactionInfoMapper;
import com.rhtop.buss.biz.mapper.UserMapper;
import com.rhtop.buss.biz.service.TransactionInfoService;

@Service("transactionInfoService")
public class TransactionInfoServiceImpl implements TransactionInfoService {

	@Autowired
	private CategoryMapper catMapper;
	@Autowired
	private CustomerMapper cusMapper;
	@Autowired
	private ContractInfoMapper conMapper;
	@Autowired
	private TransactionInfoMapper transactionInfoMapper;
	@Autowired
	private SlaTransactionInfoMapper slaTxMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private RelCategoryPriceMapper relCPMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public int insertTransactionInfo(TransactionInfo transactionInfo) {
		return transactionInfoMapper.insertSelective(transactionInfo);
	}

	@Override
	public String createDeal(TransactionInfo tx){
		try {
			String transactionInfoId = null;
			String slaTransactionInfoId = null;
			if (tx.getTransactionInfoId() == null
					|| tx.getTransactionInfoId().trim().equals("")) {
				transactionInfoId = UUID.randomUUID().toString()
						.replace("-", "");
				String now = tx.getUpdateTime();
				String userId = tx.getUpdateUser();
				tx.setTransactionInfoId(transactionInfoId);
				tx.setCreateUser(userId);
				tx.setCreateTime(now);
				tx.setPcasTime(now);
				// if(tx.getPcasPri()==null||tx.getPcasPri().trim().equals("")){
				// //如果没有填写价格，默认他是发起交易。
				// tx.setTxStatus("10");
				// }else{
				// 填写了价格，认为它是询价中。
				tx.setTxStatus("20");// 现在先丢掉“发起交易”这个状态
				// }
				transactionInfoMapper.insertSelective(tx);
				SlaTransactionInfo slaTx = new SlaTransactionInfo();
				slaTransactionInfoId = UUID.randomUUID().toString()
						.replace("-", "");
				slaTx.setSlaTransactionInfoId(slaTransactionInfoId);
				slaTx.setCreateTime(now);
				slaTx.setUpdateTime(now);
				slaTx.setCreateUser(userId);
				slaTx.setUpdateUser(userId);
				slaTx.setTransactionInfoId(transactionInfoId);
				slaTx.setPcasPri(tx.getPcasPri());
				slaTx.setPcasTime(now);
				slaTx.setTxAmo(tx.getTxAmo());
				slaTx.setCusAplSta("00");// 00表示未接受，01表示接受。
				slaTxMapper.insertSelective(slaTx);
			} else {
				throw new RuntimeException("交易记录已存在！");
			}
			return transactionInfoId;
		} catch (Exception e) {
			throw e;
		}
	}
//TODO：国际部未回盘客户可以再次更改客户价
	@Override
	public ResultInfo cusNegotiate(ResultInfo readResult, TransactionInfo tx) {
		try {
			tx.setTxStatus("20");
			String transactionInfoId = tx.getTransactionInfoId();
			TransactionInfo tx1 = transactionInfoMapper
					.selectByPrimaryKey(transactionInfoId);
			if (tx1 == null || tx1.toString().equals(null)) {
				readResult.setCode("500");
				readResult.setMessage("非法操作，交易ID无效。");
			} else {
				if(tx1.getTxStatus()=="20"||tx1.getTxStatus().trim().equals("20")){
					transactionInfoMapper.updateByPrimaryKeySelective(tx);
					SlaTransactionInfo slaTx = slaTxMapper.selectLatestByTransactionInfoId(transactionInfoId);
					String now = tx.getUpdateTime();
					String userId = tx.getUpdateUser();
					slaTx.setCreateTime(now);
					slaTx.setUpdateTime(now);
					slaTx.setCreateUser(userId);
					slaTx.setUpdateUser(userId);
					slaTx.setTransactionInfoId(transactionInfoId);
					slaTx.setPcasPri(tx.getPcasPri());
					slaTx.setPcasTime(now);
					slaTxMapper.updateByPrimaryKeySelective(slaTx);
					readResult.setMessage("修改报价完成！");
				} else{
					readResult.setCode("500");
					readResult.setMessage("请等待本轮回盘完成！");
				}
				return readResult;
			}
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage("业务层未知异常。");
		}
		return readResult;
	}

	@Override
	public String universeNegotiate(TransactionInfo tx) {
		try {
			String transactionInfoId = tx.getTransactionInfoId();
			transactionInfoMapper.updateByPrimaryKeySelective(tx);
			SlaTransactionInfo slaTx = slaTxMapper
					.selectLatestByTransactionInfoId(transactionInfoId);
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
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String domainNegotiate(TransactionInfo tx) {
		try {
			String transactionInfoId = tx.getTransactionInfoId();
			String userId = tx.getUpdateUser();
			transactionInfoMapper.updateByPrimaryKeySelective(tx);
			SlaTransactionInfo slaTx = slaTxMapper
					.selectLatestByTransactionInfoId(transactionInfoId);
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
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int deleteTransactionInfo(String transactionInfoId) {
		return transactionInfoMapper.deleteByPrimaryKey(transactionInfoId);
	}

	@Override
	public int updateTransactionInfo(TransactionInfo transactionInfo) {
		return transactionInfoMapper
				.updateByPrimaryKeySelective(transactionInfo);
	}

	@Override
	public TransactionInfo selectByPrimaryKey(String transactionInfoId) {
		TransactionInfo tra = transactionInfoMapper
				.selectByPrimaryKey(transactionInfoId);
		// 查询该交易的品类详情
		Category cate = catMapper.selectByPrimaryKey(tra.getCategoryId());
		// 查询该交易的客户详情
		Customer cust = cusMapper.selectByPrimaryKey(tra.getCustomerId());
		// 回盘信息
		SlaTransactionInfo slatransaction = new SlaTransactionInfo();
		slatransaction.setTransactionInfoId(transactionInfoId);
		List<SlaTransactionInfo> sla = slaTxMapper
				.listSlaTransactionInfos(slatransaction);
		// 合同信息
		ContractInfo contractinfo = new ContractInfo();
		contractinfo.setTransactionInfoId(transactionInfoId);
		ContractInfo con = null;
		try{
			 con = conMapper.listContractInfos(contractinfo).get(0);
		}catch(Exception e){
			
		}
		tra.setSla(sla);
		tra.setCate(cate);
		tra.setCust(cust);
		tra.setContract(con);
		return tra;
	}

	@Override
	public List<TransactionInfo> listTransactionInfos(
			TransactionInfo transactionInfo) {
		List<TransactionInfo> transactionInfoList = transactionInfoMapper
				.listTransactionInfos(transactionInfo);
		return transactionInfoList;
	}

	@Override
	public List<TransactionInfo> listPageTransactionInfo(
			TransactionInfo transactionInfo) {
		List<TransactionInfo> transactionInfos = transactionInfoMapper
				.listPageTransactionInfo(transactionInfo);
		return transactionInfos;
	}

	@Override
	public List<TransactionInfo> listPageTransactionInfoBycreateUser(
			TransactionInfo transactionInfo) {
		// 判断用户的类型
		String memberJob = memberMapper.selectByPrimaryKey(
				transactionInfo.getCreateUser()).getMemberJob();
		if ("04".equals(memberJob)) {// 决策委员会,查询所有的交易详情
			transactionInfo.setCreateUser("");
		}
		List<TransactionInfo> transactionInfos = transactionInfoMapper
				.listPageTransactionInfoBycreateUser(transactionInfo);
		return transactionInfos;
	}

	public TransactionInfo selectTransactionInfo(TransactionInfo transactionInfo) {
		// 交易信息
		TransactionInfo tran = transactionInfoMapper.selectByPrimaryKey(transactionInfo.getTransactionInfoId());
		// 客户信息
		Customer cust = cusMapper.selectByPrimaryKey(tran.getCustomerId());
		// 品类信息
		Category cate = catMapper.selectByPrimaryKey(tran.getCategoryId());
		// 回盘记录信息
		SlaTransactionInfo slaTransactionInfo = new SlaTransactionInfo();
		slaTransactionInfo.setTransactionInfoId(tran.getTransactionInfoId());
		List<SlaTransactionInfo> listSla = slaTxMapper.listSlaTransactionInfos(slaTransactionInfo);
		// 交易的创建者名称
		String mgrName = userMapper.selectByPrimaryKey(transactionInfo.getCreateUser()).getUserName();
		// 用户的判断
		String memberJob = memberMapper.selectByPrimaryKey(transactionInfo.getCreateUser()).getMemberJob();
		if ("04".equals(memberJob)) {// 决策委员会
			// 价格信息
			RelCategoryPrice rel = new RelCategoryPrice();
			rel.setCategoryId(tran.getCategoryId());
			rel.setCusChaVal(cust.getCusCha());
			RelCategoryPrice relInfo =  null;
			try{
				relInfo = relCPMapper.listPageRelCategoryPrice(rel).get(0);
			}catch(Exception e){
				
			}
			tran.setCate(cate);
			tran.setCust(cust);
			tran.setRel(relInfo);
		}else{
			//合同信息
			tran.setSla(listSla);
			ContractInfo cont = new ContractInfo();
			cont.setCategoryId(tran.getCategoryId());
			cont.setCustomerId(tran.getCustomerId());
			cont.setTransactionInfoId(tran.getTransactionInfoId());
			List<ContractInfo> conts = conMapper.listContractInfos(cont);
			int i = conMapper.listContractInfos(cont).size();
			if (i > 0) {
				cont = conts.get(0);
			}
			tran.setCate(cate);
			tran.setCust(cust);
			tran.setContract(cont);
			tran.setSla(listSla);
			tran.setMgrName(mgrName);
		}
		return tran;
	}

	@Override
	public List<TransactionInfo> listPageInfo(TransactionInfo transactionInfo) {
		List<TransactionInfo> tras = transactionInfoMapper.listPageInfo(transactionInfo);
		return tras;
	}
	
}
