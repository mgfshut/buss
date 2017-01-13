package com.rhtop.buss.biz.web;


import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/relCategoryPrice")
public class RelCategoryPriceController  extends BaseController {
	@Autowired
	private RelCategoryPriceService relCategoryPriceService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveRelCategoryPrice(@Valid @RequestParam(value = "userId") String userId,@Valid RelCategoryPrice relCategoryPrice){
		if(relCategoryPrice.getRelCategoryPriceId() == null || "".equals(relCategoryPrice.getRelCategoryPriceId())){
			String relCategoryPriceId = UUID.randomUUID().toString().replace("-", "");
			relCategoryPrice.setRelCategoryPriceId(relCategoryPriceId);
			relCategoryPrice.setCreateUser(userId);
			relCategoryPrice.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			relCategoryPrice.setUpdateUser(userId);
			relCategoryPrice.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			relCategoryPriceService.insertRelCategoryPrice(relCategoryPrice);
		}else{
			relCategoryPrice.setUpdateUser(userId);
			relCategoryPrice.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			relCategoryPriceService.updateRelCategoryPrice(relCategoryPrice);
		}
		return new HtmlMessage(relCategoryPrice);
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteRelCategoryPrice")
	public InfoResult<RelCategoryPrice> deleteRelCategoryPrice(@RequestParam("relCategoryPriceId") String relCategoryPriceId){
		InfoResult<RelCategoryPrice> infoResult = new InfoResult<RelCategoryPrice>();
		int num = relCategoryPriceService.deleteRelCategoryPrice(relCategoryPriceId);
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
	@RequestMapping("/updateRelCategoryPrice")
	public InfoResult<RelCategoryPrice> updateRelCategoryPrice(RelCategoryPrice relCategoryPrice){
		InfoResult<RelCategoryPrice> infoResult = new InfoResult<RelCategoryPrice>();
		relCategoryPrice.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = relCategoryPriceService.updateRelCategoryPrice(relCategoryPrice);
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
	public InfoResult<RelCategoryPrice> listPageRelCategoryPrice(Page page,RelCategoryPrice relCategoryPrice){
		InfoResult<RelCategoryPrice> infoResult = new InfoResult<RelCategoryPrice>();
		List<RelCategoryPrice> relCategoryPriceList = relCategoryPriceService.listPageRelCategoryPrice(relCategoryPrice);
		infoResult.setCode("200");
		infoResult.setResList(relCategoryPriceList);
		infoResult.setPage(relCategoryPrice.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<RelCategoryPrice> selectByPrimaryKey(@RequestParam("relCategoryPriceId") String relCategoryPriceId) {
		InfoResult<RelCategoryPrice> infoResult = new InfoResult<RelCategoryPrice>();
		infoResult.setCode("200");
		RelCategoryPrice relCategoryPrice = relCategoryPriceService.selectByPrimaryKey(relCategoryPriceId);
		infoResult.setResObject(relCategoryPrice);
		return infoResult;
	}
}