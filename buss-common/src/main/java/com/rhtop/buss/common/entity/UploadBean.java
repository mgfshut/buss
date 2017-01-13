package com.rhtop.buss.common.entity;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 图片上传/删除操作结构
 * @author 李彬彬
 *
 */
public class UploadBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9180240500718916683L;
	//上传路径
	private String folder;
	//上传文件大小
	private String filesize;
	//图片尺寸
	private String rect;
	//上传类型.delete为删除图片操作。当这个值为空时表示上传图片
	private String opMethod;
	//文件路径。删除图片时需要指明
	private String filepath;
	//上传成功后存放图片路径对象回调id
	private String callbackid;
	//默认图片地址
	private String defaultPhoto;
	
	public String getDefaultPhoto() {
		return defaultPhoto;
	}
	public void setDefaultPhoto(String defaultPhoto) {
		this.defaultPhoto = defaultPhoto;
	}
	public String getCallbackid() {
		return callbackid;
	}
	public void setCallbackid(String callbackid) {
		this.callbackid = callbackid;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFolder() {
		if (folder == null){
			folder = "";
		}
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getRect() {
		return rect;
	}
	public void setRect(String rect) {
		this.rect = rect;
	}
	public String getOpMethod() {
		return opMethod;
	}
	public void setOpMethod(String opMethod) {
		this.opMethod = opMethod;
	}
	
	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
		}
		return super.toString();
	}
}
