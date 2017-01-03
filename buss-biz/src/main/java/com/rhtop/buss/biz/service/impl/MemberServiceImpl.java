/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.biz.mapper.MemberMapper;
import com.rhtop.buss.biz.service.MemberService;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public int insertMember(Member member) {
		return memberMapper.insertSelective(member);
	}

	@Override
	public int deleteMember(String memberId) {
		return memberMapper.deleteByPrimaryKey(memberId);
	}

	@Override
	public int updateMember(Member member) {
		return memberMapper.updateByPrimaryKeySelective(member);
	}
	
	@Override
	public Member selectByPrimaryKey(String memberId){
		return memberMapper.selectByPrimaryKey(memberId);
	}

	@Override
	public List<Member> listMembers(Member member) {
		List<Member> memberList = memberMapper.listMembers(member);
		return memberList;
	}
	
	@Override
	public List<Member> listPageMember(Member member) {
		List<Member> members = memberMapper.listPageMember(member);
		return members;
	}

}