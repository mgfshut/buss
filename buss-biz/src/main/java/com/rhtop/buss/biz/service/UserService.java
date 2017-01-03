/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.User;

public interface UserService{
    
	/**
	 * 新增
	 */
	int insertUser(User user);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteUser(String userId);
	
	/**
	 * 修改
	 */
	int updateUser(User user);
	
	/**
	 * 根据Id查找数据
	 */
	User selectByPrimaryKey(String userId);
	
	/**
	 * 根据条件查询列表
	 */
	List<User> listUsers(User user);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<User> listPageUser(User user);
}