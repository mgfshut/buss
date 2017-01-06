package com.rhtop.buss.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rhtop.buss.common.model.BaseEntity;
import com.rhtop.buss.common.model.TreeNode;


public class TreeUtils {
	public static <T extends BaseEntity> List<TreeNode> toTreeList(List<T> entitys){
		Map<Object,TreeNode> maps = new HashMap<Object,TreeNode>();
		List<TreeNode> list = new ArrayList<TreeNode>();
		for (T t : entitys) {
			addTreeNode(t,maps,list);
		}
		return list;
	}
	private static <T extends BaseEntity> void addTreeNode(T t, Map<Object, TreeNode> maps,List<TreeNode> list) {
		TreeNode tn = new TreeNode(t);
		if(maps.containsKey(tn.getId())){
			maps.get(tn.getId()).setEntity(t);
		}else{
			maps.put(tn.getId(), tn);
		}
		
		if(tn.getParent() == null){
			list.add(tn);
		}else{
			if(maps.containsKey(tn.getParent())){
				maps.get(tn.getParent()).addChildren(tn);
			}else{
				TreeNode parent = new TreeNode();
				parent.addChildren(tn);
				maps.put(tn.getParent(),parent);
			}
		}
	}
}
