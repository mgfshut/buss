package com.rhtop.buss.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rhtop.buss.common.entity.Dept;
import com.rhtop.buss.common.entity.Member;


public class TreeMap {
	private List<Dept> depts = new ArrayList<Dept>();
	private List<Member> members = new ArrayList<Member>();
	
	private Map<String,List<Dept>> deptMap = new HashMap<String,List<Dept>>();
	private Map<String,List<Member>> memberMap = new HashMap<String,List<Member>>();
	
	public TreeMap(List<Dept> depts, List<Member> members) {
		this.depts = depts;
		this.members = members;
		initMap();
	}

	public TreeMap(List<Dept> depts) {
		this.depts = depts;
		initMap();
	}

	public TreeMap() {
		super();
	}
	private void initMap() {
		List<Dept> tempDeptList = null;
		for (Dept d : depts) {
			if(d.getParentDept() == null)continue;
			tempDeptList = deptMap.get(d.getParentDept());
			if(tempDeptList == null){
				tempDeptList = new ArrayList<Dept>();
			}
			tempDeptList.add(d);
			deptMap.put(d.getParentDept(), tempDeptList);
		}
		
		List<Member> tempUserList = null;
		for (Member u : members) {
			if(u.getDeptId() == null)continue;
			tempUserList = memberMap.get(u.getDeptId());
			if(tempUserList == null){
				tempUserList = new ArrayList<Member>();
			}
			tempUserList.add(u);
			memberMap.put(u.getDeptId(), tempUserList);
		}
		
	}
	
	public List<Dept> getChildDepts(String deptId){
		return deptMap.get(deptId);
	}
	
	public List<Member> getDeptMembers(String deptId){
		return memberMap.get(deptId);
	}

	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public Map<String, List<Dept>> getDeptMap() {
		return deptMap;
	}

	public void setDeptMap(Map<String, List<Dept>> deptMap) {
		this.deptMap = deptMap;
	}

	public Map<String, List<Member>> getMemberMap() {
		return memberMap;
	}

	public void setMemberMap(Map<String, List<Member>> memberMap) {
		this.memberMap = memberMap;
	}

	
}
