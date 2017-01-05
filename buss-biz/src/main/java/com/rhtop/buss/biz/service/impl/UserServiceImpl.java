/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.biz.mapper.UserMapper;
import com.rhtop.buss.biz.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public int insertUser(User user) {
		return userMapper.insertSelective(user);
	}

	@Override
	public int deleteUser(String userId) {
		return userMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}
	
	@Override
	public User selectByPrimaryKey(String userId){
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<User> listUsers(User user) {
		List<User> userList = userMapper.listUsers(user);
		return userList;
	}
	
	@Override
	public List<User> listPageUser(User user) {
		List<User> users = userMapper.listPageUser(user);
		return users;
	}

	@Override
	public User selectByUserName(String userName) {
		User user = userMapper.selectByUserName(userName);
		return user;
	}

}