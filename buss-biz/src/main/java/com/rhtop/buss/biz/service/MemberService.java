/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.Member;

public interface MemberService{
    
	/**
	 * 新增
	 */
	int insertMember(Member member);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteMember(String memberId);
	
	/**
	 * 修改
	 */
	int updateMember(Member member);
	
	/**
	 * 根据Id查找数据
	 */
	Member selectByPrimaryKey(String memberId);
	
	/**
	 * 根据条件查询列表
	 */
	List<Member> listMembers(Member member);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<Member> listPageMember(Member member);
}