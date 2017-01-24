/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhtop.buss.biz.mapper.CategoryMapper;
import com.rhtop.buss.biz.mapper.ContractInfoMapper;
import com.rhtop.buss.biz.mapper.CustomerMapper;
import com.rhtop.buss.biz.mapper.MemberMapper;
import com.rhtop.buss.biz.mapper.RelCategoryPriceMapper;
import com.rhtop.buss.biz.mapper.SlaTransactionInfoMapper;
import com.rhtop.buss.biz.mapper.TransactionInfoMapper;
import com.rhtop.buss.biz.mapper.UserMapper;
import com.rhtop.buss.biz.service.TransactionInfoService;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ContractInfo;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.SlaTransactionInfo;
import com.rhtop.buss.common.entity.TransactionInfo;

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
	@Transactional
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
	@Override
	@Transactional
	public ResultInfo cusNegotiate(ResultInfo readResult, TransactionInfo tx) throws Exception{
		try {
			tx.setTxStatus("20");
			String transactionInfoId = tx.getTransactionInfoId();
			TransactionInfo tx1 = transactionInfoMapper
					.selectByPrimaryKey(transactionInfoId);
			String txAmo = tx1.getTxAmo();
			if (tx1 == null || tx1.toString().equals(null)) {
				readResult.setCode("500");
				readResult.setMessage("非法操作，交易ID无效。");
			} else {
				if(tx1.getTxStatus()=="20"||tx1.getTxStatus().trim().equals("20")||
						tx1.getTxStatus()=="22"||tx1.getTxStatus().trim().equals("22")){
					transactionInfoMapper.updateByPrimaryKeySelective(tx);
					if(tx1.getTxStatus()=="22"||tx1.getTxStatus().trim().equals("22")){
						SlaTransactionInfo slaTx = new SlaTransactionInfo();
						slaTx.setSlaTransactionInfoId(UUID.randomUUID().toString().replace("-", ""));
						String now = tx.getUpdateTime();
						String userId = tx.getUpdateUser();
						slaTx.setTxAmo(txAmo);
						slaTx.setCreateTime(now);
						slaTx.setUpdateTime(now);
						slaTx.setCreateUser(userId);
						slaTx.setUpdateUser(userId);
						slaTx.setTransactionInfoId(transactionInfoId);
						slaTx.setPcasPri(tx.getPcasPri());
						slaTx.setPcasTime(now);
						slaTxMapper.insertSelective(slaTx);
						readResult.setMessage("修改报价完成！");
					}else{
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
					}
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
	@Transactional
	public String universeNegotiate(TransactionInfo tx) throws Exception{
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
	@Transactional
	public String domainNegotiate(TransactionInfo tx) throws Exception{
		try {
			String transactionInfoId = tx.getTransactionInfoId();
			String userId = tx.getUpdateUser();
			String now = tx.getUpdateTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SlaTransactionInfo slaTx = slaTxMapper.selectLatestByTransactionInfoId(transactionInfoId);
			Date d = new Date();
			String endTime = sdf.format(new Date(d.getTime()+Long.parseLong(slaTx.getCtofAging())*60*60*1000));
			tx.setEndTime(endTime);
			tx.setCtofAging(slaTx.getCtofAging());
			transactionInfoMapper.updateByPrimaryKeySelective(tx);
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
		//格式化回盘记录的时间格式（MM-dd HH:mm ）
				for(SlaTransactionInfo sl : sla){
					try {
						//采取的字符的截取
						if(StringUtils.isEmpty(sl.getCtofTime())){
							sl.setCtofTime(sl.getCtofTime());
						}else{
							sl.setCtofTime(sl.getCtofTime().substring(5, 16));
							
						}
						if(StringUtils.isEmpty(sl.getPcasTime())){
							sl.setPcasTime(sl.getPcasTime());
						}else{
							sl.setPcasTime(sl.getPcasTime().substring(5, 16));
						}	
					} catch (Exception e) {
						throw e;
					}
					}
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
		List<TransactionInfo> transactionInfos = null;
		// 判断用户的类型
		String memberJob = memberMapper.selectByPrimaryKey(
				transactionInfo.getCreateUser()).getMemberJob();
		if ("04".equals(memberJob)) {// 决策委员会,查询所有的交易详情(状态大于等于21)
			transactionInfo.setCreateUser("");
			transactionInfo.setTxStatus("21");
			transactionInfos = transactionInfoMapper.listPageInfoByTxStatus(transactionInfo);
		}else{
			transactionInfos = transactionInfoMapper.listPageTransactionInfoBycreateUser(transactionInfo);
			
		}
		return transactionInfos;
	}

	public TransactionInfo selectTransactionInfo(TransactionInfo transactionInfo) {
		// 交易信息
		TransactionInfo tran = transactionInfoMapper.selectByPrimaryKey(transactionInfo.getTransactionInfoId());
		if(tran == null){
			return tran;
		}
		// 客户信息
		Customer cust = cusMapper.selectByPrimaryKey(tran.getCustomerId());
		// 品类信息
		Category cate = catMapper.selectByPrimaryKey(tran.getCategoryId());
		// 回盘记录信息
		SlaTransactionInfo slaTransactionInfo = new SlaTransactionInfo();
		slaTransactionInfo.setTransactionInfoId(tran.getTransactionInfoId());
		List<SlaTransactionInfo> listSla = slaTxMapper.listSlaTransactionInfos(slaTransactionInfo);
		//格式化回盘记录的时间格式（MM-dd HH:mm ）
		for(SlaTransactionInfo sl : listSla){
			try {
				//采取的字符的截取
				if(StringUtils.isEmpty(sl.getCtofTime())){
					sl.setCtofTime(sl.getCtofTime());
				}else{
					sl.setCtofTime(sl.getCtofTime().substring(5, 16));
					
				}
				if(StringUtils.isEmpty(sl.getPcasTime())){
					sl.setPcasTime(sl.getPcasTime());
				}else{
					sl.setPcasTime(sl.getPcasTime().substring(5, 16));
				}	
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		// 交易的创建者名称
		String mgrName = memberMapper.selectByPrimaryKey(tran.getCreateUser()).getMemberName();
		// 用户的判断
		String memberJob = memberMapper.selectByPrimaryKey(transactionInfo.getCreateUser()).getMemberJob();
		if ("04".equals(memberJob)) {// 决策委员会
			// 价格信息
			RelCategoryPrice rel = new RelCategoryPrice();
			String catId = tran.getCategoryId();
			String chaId = cust.getCusCha();
			try{
				rel = relCPMapper.selectByCategoryIdAndChaId(catId, chaId).get(0);
			}catch(Exception e){
				e.printStackTrace();
			}
			tran.setRel(rel);//价格信息
		}else{
			//合同信息
			ContractInfo cont = new ContractInfo();
			tran.setSla(listSla);
			cont.setCategoryId(tran.getCategoryId());
			cont.setCustomerId(tran.getCustomerId());
			cont.setTransactionInfoId(tran.getTransactionInfoId());
			List<ContractInfo> conts = conMapper.listContractInfos(cont);
			int i = conMapper.listContractInfos(cont).size();
			if (i > 0) {
				cont = conts.get(0);
			}
			tran.setContract(cont);//合同信息
		}
		tran.setCate(cate);//品类信息
		tran.setCust(cust);//客户信息
		tran.setSla(listSla);//回盘列表信息
		tran.setMgrName(mgrName);//交易的创建者
		return tran;
	}


	@Override
	public List<TransactionInfo> listPageInfo(TransactionInfo transactionInfo) {
		//模糊查询--->1.得到关键字，2.得到品类id，3.将品类的id set到TransactionInfo
		List<TransactionInfo> tras = null;
		Category category = new Category();
		category.setCateName(transactionInfo.getCateName());
		List<Category> cates = catMapper.listCategorys(category);
		for (Category cata : cates) {
			transactionInfo.setCategoryId(cata.getCategoryId());
			transactionInfo.setCateName("");
			tras = transactionInfoMapper.listPageInfo(transactionInfo);
			for (TransactionInfo tra : tras) {
				// 品类信息
				tra.setCate(catMapper.selectByPrimaryKey(tra.getCategoryId()));
				// 客户信息
				tra.setCust(cusMapper.selectInfoByPrimaryKey(tra.getCustomerId()));
			}
		}
	return tras;
	}

	@Override
	public TransactionInfo CustAndCateAndPriceInfo(TransactionInfo transactionInfo) {
		TransactionInfo tran = transactionInfoMapper.selectByPrimaryKey(transactionInfo.getTransactionInfoId());//得到该交易的详情
		//品类信息
		tran.setCate(catMapper.selectByPrimaryKey(tran.getCategoryId()));	
		//客户信息
		Customer cust = cusMapper.selectByPrimaryKey(tran.getCustomerId());
		tran.setCust(cust);
		// 回盘记录信息
		SlaTransactionInfo slaTransactionInfo = new SlaTransactionInfo();
		slaTransactionInfo.setTransactionInfoId(tran.getTransactionInfoId());
		List<SlaTransactionInfo> listSla = slaTxMapper.listSlaTransactionInfos(slaTransactionInfo);
		// 格式化回盘记录的时间格式（MM-dd HH:mm ）
		for (SlaTransactionInfo sl : listSla) {
			try {
				// 采取的字符的截取
				if (StringUtils.isEmpty(sl.getCtofTime())) {
					sl.setCtofTime(sl.getCtofTime());
				} else {
					sl.setCtofTime(sl.getCtofTime().substring(5, 16));
				}
				if (StringUtils.isEmpty(sl.getPcasTime())) {
					sl.setPcasTime(sl.getPcasTime());
				} else {
					sl.setPcasTime(sl.getPcasTime().substring(5, 16));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		tran.setSla(listSla);
		return tran;
	}
	
}
