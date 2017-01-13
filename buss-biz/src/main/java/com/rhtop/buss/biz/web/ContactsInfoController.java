package com.rhtop.buss.biz.web;


import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/contactsInfo")
public class ContactsInfoController  extends BaseController {
	@Autowired
	private ContactsInfoService contactsInfoService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveContactsInfo(@Valid @RequestParam(value = "userId") String userId,@Valid ContactsInfo contactsInfo){
		if(contactsInfo.getContactsInfoId() == null || "".equals(contactsInfo.getContactsInfoId())){
			String contactsInfoId = UUID.randomUUID().toString().replace("-", "");
			contactsInfo.setContactsInfoId(contactsInfoId);
			contactsInfo.setCreateUser(userId);
			contactsInfo.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			contactsInfo.setUpdateUser(userId);
			contactsInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			contactsInfoService.insertContactsInfo(contactsInfo);
		}else{
			contactsInfo.setUpdateUser(userId);
			contactsInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			contactsInfoService.updateContactsInfo(contactsInfo);
		}
		return new HtmlMessage(contactsInfo);
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteContactsInfo")
	public InfoResult<ContactsInfo> deleteContactsInfo(@RequestParam("contactsInfoId") String contactsInfoId){
		InfoResult<ContactsInfo> infoResult = new InfoResult<ContactsInfo>();
		int num = contactsInfoService.deleteContactsInfo(contactsInfoId);
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
	@RequestMapping("/updateContactsInfo")
	public InfoResult<ContactsInfo> updateContactsInfo(ContactsInfo contactsInfo){
		InfoResult<ContactsInfo> infoResult = new InfoResult<ContactsInfo>();
		contactsInfo.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = contactsInfoService.updateContactsInfo(contactsInfo);
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
	public InfoResult<ContactsInfo> listPageContactsInfo(Page page,ContactsInfo contactsInfo){
		InfoResult<ContactsInfo> infoResult = new InfoResult<ContactsInfo>();
		contactsInfo.setPage(page);
		List<ContactsInfo> contactsInfoList = contactsInfoService.listPageContactsInfo(contactsInfo);
		infoResult.setCode("200");
		infoResult.setResList(contactsInfoList);
		infoResult.setPage(contactsInfo.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<ContactsInfo> selectByPrimaryKey(@RequestParam("contactsInfoId") String contactsInfoId) {
		InfoResult<ContactsInfo> infoResult = new InfoResult<ContactsInfo>();
		infoResult.setCode("200");
		ContactsInfo contactsInfo = contactsInfoService.selectByPrimaryKey(contactsInfoId);
		infoResult.setResObject(contactsInfo);
		return infoResult;
	}
}