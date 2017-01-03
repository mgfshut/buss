/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.User;

public interface UserMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(User user);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String userId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(User user);
    /**
	 * 根据主键查询对象
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