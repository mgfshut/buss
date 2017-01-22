package com.rhtop.buss.biz.web;


import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.biz.mapper.SlaTransactionInfoMapper;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.TransactionInfoService;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.SlaTransactionInfo;
import com.rhtop.buss.common.entity.TransactionInfo;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/transactionInfo")
public class TransactionInfoController  extends BaseController {
	@Autowired
	private TransactionInfoService transactionInfoService;
	@Autowired
	private CategoryService cateSer;
	@Autowired
	private CustomerService custSer;
	@Autowired
	private SlaTransactionInfoMapper slaTransactionInfoMapper;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveTransactionInfo(@Valid @RequestParam(value = "userId") String userId,@Valid TransactionInfo transactionInfo){
		if(transactionInfo.getTransactionInfoId() == null || "".equals(transactionInfo.getTransactionInfoId())){
			String transactionInfoId = UUID.randomUUID().toString().replace("-", "");
			transactionInfo.setTransactionInfoId(transactionInfoId);
			transactionInfo.setCreateUser(userId);
			transactionInfo.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			transactionInfo.setUpdateUser(userId);
			transactionInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			transactionInfoService.insertTransactionInfo(transactionInfo);
		}else{
			transactionInfo.setUpdateUser(userId);
			transactionInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			transactionInfoService.updateTransactionInfo(transactionInfo);
		}
		return new HtmlMessage(transactionInfo);
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteTransactionInfo")
	public InfoResult<TransactionInfo> deleteTransactionInfo(@RequestParam("transactionInfoId") String transactionInfoId){
		InfoResult<TransactionInfo> infoResult = new InfoResult<TransactionInfo>();
		int num = transactionInfoService.deleteTransactionInfo(transactionInfoId);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("删除成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("删除失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
     * 修改
     */
	@ResponseBody
	@RequestMapping("/updateTransactionInfo")
	public InfoResult<TransactionInfo> updateTransactionInfo(TransactionInfo transactionInfo){
		InfoResult<TransactionInfo> infoResult = new InfoResult<TransactionInfo>();
		transactionInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = transactionInfoService.updateTransactionInfo(transactionInfo);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("修改成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("修改失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
	 * 根据条件分页查询信息列表
	 */
	@ResponseBody
	@RequestMapping(value="/pager")
	public InfoResult<TransactionInfo> listPageTransactionInfo(Page page,TransactionInfo transactionInfo){
		InfoResult<TransactionInfo> infoResult = new InfoResult<TransactionInfo>();
		transactionInfo.setPage(page);
		List<TransactionInfo> transactionInfoList = transactionInfoService.listPageTransactionInfo(transactionInfo);
		infoResult.setCode("200");
		infoResult.setResList(transactionInfoList);
		infoResult.setPage(transactionInfo.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<TransactionInfo> selectByPrimaryKey(@RequestParam("transactionInfoId") String transactionInfoId) {
		InfoResult<TransactionInfo> infoResult = new InfoResult<TransactionInfo>();
		infoResult.setCode("200");
		TransactionInfo transactionInfo = transactionInfoService.selectByPrimaryKey(transactionInfoId);
		infoResult.setResObject(transactionInfo);
		return infoResult;
	}
	
	/**
	 * 国际人员 pc
	 * 回盘信息 (交易状态为20的交易信息)
	 * @author lujin
	 * @date 2017-1-16
	 * @param 
	 * @return
	 */
	@RequestMapping("/ctofPrice")
	@ResponseBody
	public InfoResult<List<TransactionInfo>> getCtofPrice(Page page,TransactionInfo transactioninfo ){
		InfoResult<List<TransactionInfo>> infoResult = new InfoResult<List<TransactionInfo>>();
		//查询20和21状态的数据
		if (StringUtils.isEmpty(transactioninfo.getTxStatus())){
			transactioninfo.setqType("1");
		}
		List<TransactionInfo> Tranlist = transactionInfoService.listPageInfo(transactioninfo);
		infoResult.setPage(page);
		infoResult.setCode("200");
		infoResult.setResObject(Tranlist);
		infoResult.setPage(transactioninfo.getPage());
		return infoResult;
		}
	/**
	 * 回盘记录详情
	 * @author lujin
	 * @date 2017-1-17
	 * @param transactioninfoId
	 * @return
	 */
	@RequestMapping("/{transactioninfoId}")
	@ResponseBody
	public TransactionInfo getTranInfo(@PathVariable("transactioninfoId") String transactioninfoId) {
		TransactionInfo tran = transactionInfoService.selectByPrimaryKey(transactioninfoId);
		Category cate = cateSer.selectByPrimaryKey(tran.getCategoryId());
		Customer cust = custSer.selectByPrimaryKey(tran.getCustomerId());
		//以及最后一条交易记录
		SlaTransactionInfo sla = new  SlaTransactionInfo();
		sla.setTransactionInfoId(transactioninfoId);
		sla.setTxAmo(tran.getTxAmo());
		try{
			sla = slaTransactionInfoMapper.listSlaTransactionInfos(sla).get(0);//因为根据更新时间降序排序的
		}catch(Exception e){
			
		}
		tran.setCate(cate);
		tran.setCust(cust);
		tran.setSlaInfo(sla);
		return tran;
	}
	
	/**
	 * 国际委员更新回盘价
	 * @param userId
	 * @param 
	 * @date 2017-1-20
	 * @return
	 */
	@RequestMapping("/updateCategoryPrice")
	@ResponseBody
	public ResultInfo updateUniCtofPrice(@Valid @RequestParam(value = "userId") String userId,@Valid SlaTransactionInfo slaTransactionInfo){
		ResultInfo readResult = new ResultInfo();
		try {
			//国际部回盘价，以及回盘时效
//			slaTransactionInfo.getUniCtofPri();//回盘价
//			slaTransactionInfo.getCtofAging();//回盘时效
			slaTransactionInfo.setCtofPerId(userId);//回盘人
			slaTransactionInfo.setCtofTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));//回盘时间
			slaTransactionInfoMapper.updateByPrimaryKeySelective(slaTransactionInfo);
			//更新交易状态
			TransactionInfo transactionInfo = new TransactionInfo();
			transactionInfo.setTransactionInfoId(slaTransactionInfo.getTransactionInfoId());
			transactionInfo.setTxStatus("21");
			transactionInfoService.updateTransactionInfo(transactionInfo);
			readResult.setCode("200");
			readResult.setMessage("更新成功！");
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
			log.error("[CategoryController.updateCategoryPrice]数据更新异常", e);
		}
		return readResult;
	}
}