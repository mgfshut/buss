package com.rhtop.buss.common.entity;

import java.io.Serializable;

import com.rhtop.buss.common.model.BaseEntity;


public class Upgrade extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//版本号
	public Float versionCode;
	//设备类型
	public String devType;
	//下载文件集合。多个文件中间用','分割，只需要列示文件名，文件下载前缀是固定的
	public String downloadFiles;
	
	public String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Float getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(Float versionCode) {
		this.versionCode = versionCode;
	}
	public String getDevType() {
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	public String getDownloadFiles() {
		return downloadFiles;
	}
	public void setDownloadFiles(String downloadFiles) {
		this.downloadFiles = downloadFiles;
	}
}
