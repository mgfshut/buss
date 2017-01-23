/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.common.utils.Tools;
import com.rhtop.buss.biz.mapper.ContactsInfoMapper;
import com.rhtop.buss.biz.service.ContactsInfoService;

@Service("contactsInfoService")
public class ContactsInfoServiceImpl implements ContactsInfoService {
	@Autowired
	private ContactsInfoMapper contactsInfoMapper;
	
	@Override
	public int insertContactsInfo(ContactsInfo contactsInfo) throws Exception{
		int status = 0;
		try {
			if(Tools.isEmpty(contactsInfo.getContactsInfoId())||
					Tools.isEmpty(contactsInfo.getContactName())||
					Tools.isEmpty(contactsInfo.getContactPhone())){
				throw new Exception("联系人信息不完整！");
			}
			if(!Tools.isMobileNO(contactsInfo.getContactPhone())){
				throw new Exception("手机号格式不正确！");
			}
			if(!Tools.isEmpty(contactsInfo.getContactMail())){
				if(!Tools.checkEmail(contactsInfo.getContactMail())){
					throw new Exception("邮箱格式不正确！");
				}
			}
			status = contactsInfoMapper.insertSelective(contactsInfo);
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	@Override
	public int deleteContactsInfo(String contactsInfoId) {
		return contactsInfoMapper.deleteByPrimaryKey(contactsInfoId);
	}

	@Override
	public int updateContactsInfo(ContactsInfo contactsInfo) throws Exception{
		int status = 0;
		try {
			if(Tools.isEmpty(contactsInfo.getContactsInfoId())||
					Tools.isEmpty(contactsInfo.getContactName())||
					Tools.isEmpty(contactsInfo.getContactPhone())){
				throw new Exception("联系人基本信息不完整！");
			}
			if(!Tools.isMobileNO(contactsInfo.getContactPhone())){
				throw new Exception("手机号格式不正确！");
			}
			if(!Tools.isEmpty(contactsInfo.getContactMail())){
				if(!Tools.checkEmail(contactsInfo.getContactMail())){
					throw new Exception("邮箱格式不正确！");
				}
			}
			status = contactsInfoMapper.updateByPrimaryKeySelective(contactsInfo);
		} catch (Exception e) {
			throw e;
		}
		return status;
	}
	
	@Override
	public ContactsInfo selectByPrimaryKey(String contactsInfoId){
		return contactsInfoMapper.selectByPrimaryKey(contactsInfoId);
	}

	@Override
	public List<ContactsInfo> listContactsInfos(ContactsInfo contactsInfo) {
		List<ContactsInfo> contactsInfoList = contactsInfoMapper.listContactsInfos(contactsInfo);
		return contactsInfoList;
	}
	
	@Override
	public List<ContactsInfo> listPageContactsInfo(ContactsInfo contactsInfo) {
		List<ContactsInfo> contactsInfos = contactsInfoMapper.listPageContactsInfo(contactsInfo);
		return contactsInfos;
	}

}