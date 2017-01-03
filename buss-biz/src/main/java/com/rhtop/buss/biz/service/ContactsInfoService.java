/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.ContactsInfo;

public interface ContactsInfoService{
    
	/**
	 * 新增
	 */
	int insertContactsInfo(ContactsInfo contactsInfo);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteContactsInfo(String contactsInfoId);
	
	/**
	 * 修改
	 */
	int updateContactsInfo(ContactsInfo contactsInfo);
	
	/**
	 * 根据Id查找数据
	 */
	ContactsInfo selectByPrimaryKey(String contactsInfoId);
	
	/**
	 * 根据条件查询列表
	 */
	List<ContactsInfo> listContactsInfos(ContactsInfo contactsInfo);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<ContactsInfo> listPageContactsInfo(ContactsInfo contactsInfo);
}