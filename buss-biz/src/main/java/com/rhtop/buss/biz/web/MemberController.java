package com.rhtop.buss.biz.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Dept;
import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.entity.Role;
import com.rhtop.buss.common.entity.RsUserRole;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.exception.BusException;
import com.rhtop.buss.biz.service.DeptService;
import com.rhtop.buss.biz.service.MemberService;
import com.rhtop.buss.biz.service.RoleService;
import com.rhtop.buss.biz.service.RsUserRoleService;
import com.rhtop.buss.biz.service.UserService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.utils.PasswordUtils;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/member")
public class MemberController  extends BaseController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RsUserRoleService rsUserRoleService;
	@Autowired
	private DeptService deptService;
	
    /**
     * 保存
     */
	@RequestMapping("/save")
	@ResponseBody
	public HtmlMessage save(@Valid @RequestParam(value = "userId") String userId,@Valid Member member){
		if(member.getMemberId() == null || "".equals(member.getMemberId())){
			String memberId = UUID.randomUUID().toString().replace("-", "");
			member.setMemberId(memberId);
			member.setCreateUser(member.getUpdateUser());
			member.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			member.setUpdateUser(member.getUpdateUser());
			member.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			Dept dept = deptService.selectByPrimaryKey(member.getDeptId());
			member.setDeptCity(dept.getDeptCity());
			memberService.insertMember(member);
			User user = new User();
			user.setUserName(member.getUserName());
			user.setUserStatus(member.getUserStatus());
			user.setUserPassword(PasswordUtils.md5Password("123456"));
			user.setUserId(memberId);
			user.setCreateUser(user.getUpdateUser());
			user.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			user.setUpdateUser(user.getUpdateUser());
			user.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			userService.insertUser(user);
		}else{
			member.setUpdateUser(userId);
			member.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			memberService.updateMember(member);
		}
		return new HtmlMessage(member);
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
	 * 根据条件查询用户信息列表
	 * @author mgf
	 * @date 2017年1月11日 下午7:38:51 
	 * @param body
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/listMembers")
	public ResultInfo listCustomers(@RequestParam("body") String body) {
		JSONObject jsonObject=JSONObject.fromObject(body);
		Member member = (Member)JSONObject.toBean(jsonObject, Member.class);
		ResultInfo readResult = new ResultInfo();
		List<Member> members = memberService.listMembers(member);
		readResult.setCode("200");
		readResult.setRecords(members);
		return readResult;
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
	@RequestMapping("/{memberId}")
	@ResponseBody
	public Member getByMemberId(@PathVariable("memberId") String memberId){
		Member member = memberService.selectByPrimaryKey(memberId);
		return member;
	}
	@RequestMapping("role/{memberId}")
	@ResponseBody
	public Member getRoleMemberByMemberId(@PathVariable("memberId") String memberId){
		Member member = memberService.selectByPrimaryKey(memberId);
		List<Role> roleList = roleService.listRolesByMemberId(memberId);
		String str = "";
		StringBuffer sf = new StringBuffer();
		for (Role role : roleList) {
			sf.append(",").append(role.getRoleName());
		}
		if (sf.length() > 0) {
			str = sf.substring(1);
		}
		member.setRoles(str);
		return member;
	}
	
	@RequestMapping("/remove/{memberId}")
	@ResponseBody
	public HtmlMessage  removeMember(@PathVariable("memberId") String memberId){
		memberService.deleteMember(memberId);
		return new HtmlMessage("删除用户成功").setCallbackType(null);
	}
	
	@RequestMapping(value = "/roles/{memberId}")
	@ResponseBody
	public Map<String, Object> findRolesByMember(HttpServletRequest request,
			@PathVariable("memberId") String memberId) {
		List<Role> allRoles = roleService.listRoles(new Role());
		Member member = memberService.selectByPrimaryKey(memberId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allRoles", allRoles);
		RsUserRole rsUserRole = new RsUserRole();
		rsUserRole.setMemberId(memberId);
		List<RsUserRole> rsUserRoleList = rsUserRoleService.listRsUserRoles(rsUserRole);
		String str = "";
		StringBuffer sf = new StringBuffer();
		for (RsUserRole rur : rsUserRoleList) {
			sf.append(",").append(rur.getRoleId());
		}
		if (sf.length() > 0) {
			str = sf.substring(1);
		}
		map.put("memberRoles", str);
		map.put("member", member);
		return map;
	}
	
	/**
	 * 更新用户角色
	 * @return
	 */
	@RequestMapping(value = "/updateMemberRoles")
	@ResponseBody
	public HtmlMessage updateMemberRoles(HttpServletRequest request,
			@RequestParam(value = "memberId") String memberId,
			@RequestParam(value = "roleIds", required = false) String[] roleIds) {
		if (roleIds == null) {
			throw new BusException("请选择角色再提交!");
		}
		rsUserRoleService.deleteRsUserRoleByMemberId(memberId);
		for(String roleId:roleIds){
			RsUserRole rsUserRole = new RsUserRole();
			rsUserRole.setRsUserRoleId(UUID.randomUUID().toString().replace("-", ""));
			rsUserRole.setMemberId(memberId);
			rsUserRole.setRoleId(roleId);
			rsUserRoleService.insertRsUserRole(rsUserRole);
		}
		return new HtmlMessage("角色分配成功!").setNavTabId("sys:member");
	}
}