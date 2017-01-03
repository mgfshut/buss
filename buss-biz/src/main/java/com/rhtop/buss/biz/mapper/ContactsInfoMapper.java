/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.ContactsInfo;

public interface ContactsInfoMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(ContactsInfo contactsInfo);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String contactsInfoId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(ContactsInfo contactsInfo);
    /**
	 * 根据主键查询对象
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