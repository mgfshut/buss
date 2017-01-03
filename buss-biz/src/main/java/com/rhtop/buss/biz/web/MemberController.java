package com.rhtop.buss.biz.web;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.biz.service.MemberService;
import com.rhtop.buss.biz.service.UserService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.utils.PasswordUtils;

@Controller
@RequestMapping("service/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private UserService userService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public InfoResult<Member> insertMember(Member member){
		InfoResult<Member> infoResult = new InfoResult<Member>();
		String memberId = UUID.randomUUID().toString().replace("-", "+");
		member.setMemberId(memberId);
		member.setCreateUser(member.getUpdateUser());
		member.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		member.setUpdateUser(member.getUpdateUser());
		member.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = memberService.insertMember(member);
		if (num > 0) {
			User user = new User();
			user.setUserName(member.getUserName());
			user.setUserStatus(member.getUserStatus());
			user.setUserPassword(PasswordUtils.md5Password("123456"));
			user.setUserId(memberId);
			user.setCreateUser(user.getUpdateUser());
			user.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			user.setUpdateUser(user.getUpdateUser());
			user.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			int userNum = userService.insertUser(user);
			if(userNum > 0){
				infoResult.setCode("200");
				infoResult.setMsg("新增成功");
			}else{
				infoResult.setCode("500");
				infoResult.setMsg("新增失败");
			}
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("新增失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteMember")
	public InfoResult<Member> deleteMember(@RequestParam("memberId") String memberId){
		InfoResult<Member> infoResult = new InfoResult<Member>();
		int num = memberService.deleteMember(memberId);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("删除成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("删除失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
     * 修改
     */
	@ResponseBody
	@RequestMapping("/updateMember")
	public InfoResult<Member> updateMember(Member member){
		InfoResult<Member> infoResult = new InfoResult<Member>();
		member.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = memberService.updateMember(member);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("修改成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("修改失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
	 * 根据条件分页查询信息列表
	 */
	@ResponseBody
	@RequestMapping(value="/pager")
	public InfoResult<Member> listPageMember(Page page,Member member){
		InfoResult<Member> infoResult = new InfoResult<Member>();
		member.setPage(page);
		List<Member> memberList = memberService.listPageMember(member);
		infoResult.setCode("200");
		infoResult.setResList(memberList);
		infoResult.setPage(member.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<Member> selectByPrimaryKey(@RequestParam("memberId") String memberId) {
		InfoResult<Member> infoResult = new InfoResult<Member>();
		infoResult.setCode("200");
		Member member = memberService.selectByPrimaryKey(memberId);
		infoResult.setResObject(member);
		return infoResult;
	}
}