/**
 *
 */
package com.rhtop.buss.common.entity;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author
 * @version
 */
//@XmlRootElement(name = "SysLogger")
public class SysLogger {
	private String title;//系统日志标题
	
	private String type;//类型
	
	private String data;//内容
	
	private String sysLoggerId;//系统日志ID
	
	private String createTime;//创建时间
	
	private String createUser;//创建人
	
	private Page page;//分页
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSysLoggerId() {
		return sysLoggerId;
	}

	public void setSysLoggerId(String sysLoggerId) {
		this.sysLoggerId = sysLoggerId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String toString(){
		return super.toString();
	}
}