package com.rhtop.buss.common.model;

import java.util.List;

public class ListData<T> {
	private List<T> list;
	
	public ListData(List<T> list) {
		super();
		this.list = list;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
