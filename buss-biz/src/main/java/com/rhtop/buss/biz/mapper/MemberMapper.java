/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.Member;

public interface MemberMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(Member member);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String memberId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(Member member);
    /**
	 * 根据主键查询对象
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