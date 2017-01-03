/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.rhtop.buss.common.entity.ContactsInfo;
import com.rhtop.buss.biz.mapper.ContactsInfoMapper;
import com.rhtop.buss.biz.service.ContactsInfoService;

@Service("contactsInfoService")
public class ContactsInfoServiceImpl implements ContactsInfoService {
	@Autowired
	private ContactsInfoMapper contactsInfoMapper;
	
	@Override
	public int insertContactsInfo(ContactsInfo contactsInfo) {
		return contactsInfoMapper.insertSelective(contactsInfo);
	}

	@Override
	public int deleteContactsInfo(String contactsInfoId) {
		return contactsInfoMapper.deleteByPrimaryKey(contactsInfoId);
	}

	@Override
	public int updateContactsInfo(ContactsInfo contactsInfo) {
		return contactsInfoMapper.updateByPrimaryKeySelective(contactsInfo);
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