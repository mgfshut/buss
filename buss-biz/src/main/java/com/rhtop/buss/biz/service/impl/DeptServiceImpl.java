/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.Dept;
import com.rhtop.buss.common.model.TreeMap;
import com.rhtop.buss.biz.mapper.DeptMapper;
import com.rhtop.buss.biz.service.DeptService;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptMapper deptMapper;
	
	@Override
	public int insertDept(Dept dept) {
		return deptMapper.insertSelective(dept);
	}

	@Override
	public int deleteDept(String deptId) {
		return deptMapper.deleteByPrimaryKey(deptId);
	}

	@Override
	public int updateDept(Dept dept) {
		return deptMapper.updateByPrimaryKeySelective(dept);
	}
	
	@Override
	public Dept selectByPrimaryKey(String deptId){
		return deptMapper.selectByPrimaryKey(deptId);
	}

	@Override
	public List<Dept> listDepts(Dept dept) {
		List<Dept> deptList = deptMapper.listDepts(dept);
		return deptList;
	}
	
	@Override
	public List<Dept> listPageDept(Dept dept) {
		List<Dept> depts = deptMapper.listPageDept(dept);
		return depts;
	}

	@Override
	public String getDeptTree(String property) {
		String[] pts = property.replace("_", ".").split(":");
		String deptId = pts[0];
		String deptname= pts[1];
		
		StringBuffer str = new StringBuffer();
		
		List<Dept> depts = listDepts(new Dept());
		TreeMap treeMap = new TreeMap(depts);
		str.append("<ul class=\"tree treeFolder expand\">\r\n");
		for (Dept dept : depts) {
			if(dept.getParentDept() != null && !"".equals(dept.getParentDept()))continue;
			str.append("<li><a href=\"javascript:\" onclick=\"$.bringBack({"+deptId+":'"+dept.getDeptId()+"', "+deptname+":'"+dept.getDeptName()+"'})\">"+dept.getDeptName()+"</a>\r\n");
			str.append(getChildNodes(dept.getDeptId(),treeMap,deptId,deptname));	
			str.append("</li>\r\n");
		}
		str.append("</ul>");
		return str.toString();
	}
	
	
	public String getChildNodes(String parentId,TreeMap map,String id,String name){
		StringBuffer childStr = new StringBuffer();
		
		List<Dept> childDepts = map.getChildDepts(parentId);
		
		if(childDepts != null){
			childStr.append("<ul>\r\n");
			for (Dept dept : childDepts) {
				childStr.append("<li><a href=\"javascript:\" onclick=\"$.bringBack({"+id+":'"+dept.getDeptId()+"', "+name+":'"+dept.getDeptName()+"'})\">"+dept.getDeptName()+"</a>\r\n");
				childStr.append(getChildNodes(dept.getDeptId(),map,id,name));
				childStr.append("</li>\r\n");
			}
			childStr.append("</ul>\r\n");
			return childStr.toString();
		}else{
			return "";
		}
	}
}