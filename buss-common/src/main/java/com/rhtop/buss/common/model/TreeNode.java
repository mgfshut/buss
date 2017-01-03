package com.rhtop.buss.common.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.rhtop.buss.common.model.TreeProp.TreePropType;


public class TreeNode {
	private BaseEntity entity;
	private List<TreeNode> childrens = new ArrayList<TreeNode>();
	private String path;
	private Object code;
	private String text;
	private Object parent;
	private String id;
	
	
	public TreeNode() {
		super();
	}

	public TreeNode(BaseEntity entity){
		this.setEntity(entity);
	}
	
	public void addChildren(TreeNode node){
		this.childrens.add(node);
	}
	
	public BaseEntity getEntity() {
		return entity;
	}
	public String getPath() {
		return path;
	}
	public Object getCode() {
		return code;
	}
	public String getText() {
		return text;
	}
	public Object getParent() {
		return parent;
	}
	public String getId() {
		return id;
	}

	public List<TreeNode> getChildrens() {
		return childrens;
	}

	public void setEntity(BaseEntity entity) {
		this.entity = entity;
		initNode();
	}

	private void initNode() {
		Method[] methods = entity.getClass().getMethods();
		Method codeMethod = null,textMethod = null,parentMethod = null,pathMethod = null,idMethod = null;
		for (Method method : methods) {
			TreeProp treeProp = method.getAnnotation(TreeProp.class);
			if(treeProp == null) continue;
			try {
				if(treeProp.value() == TreePropType.CODE){
					codeMethod = method;
				}
				else if(treeProp.value() == TreePropType.TEXT){
					textMethod = method;
				}
				else if(treeProp.value() == TreePropType.PATH){
					pathMethod = method;
				}
				else if(treeProp.value() == TreePropType.PARENT){
					parentMethod = method;
				}
				else if(treeProp.value() == TreePropType.ID){
					idMethod = method;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		try {
			if(codeMethod != null) this.code = codeMethod.invoke(entity);
			if(textMethod != null) this.text = (String) textMethod.invoke(entity);
			if(pathMethod != null) this.path= (String) pathMethod.invoke(entity);
			if(idMethod != null) this.id= (String) idMethod.invoke(entity);
			if(parentMethod != null){
				Object value = parentMethod.invoke(entity);
				if(value != null && codeMethod != null && value.getClass().equals(entity.getClass())){
					parent = codeMethod.invoke(value);
				}
				else{
					parent = value;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
		}
	}
}
