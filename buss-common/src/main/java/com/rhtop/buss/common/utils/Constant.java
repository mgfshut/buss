package com.rhtop.buss.common.utils;

import javax.servlet.http.HttpServletRequest;

public class Constant {
	
	/**
	 * 默认用户头像图片
	 */
	public final static String DEFAULTUSERLOGO = "/images/default_userphoto.png";
	
	/**
	 * unix系统下的文件分隔符
	 */
	public final static String UNIXFILESUFFIX = "/";
	
	/**
	 * 用户头像名称
	 */
	public final static String USERPHOTONAME = "logo.jpg";
	
	/**
	 * 默认图片
	 */
	public final static String NOPIC = "/images/default_photo.png";
	
	/**
	 * 临时文件上传目录
	 */
	public final static String TEMPUPLOADPATH = "/uploads/temp/";
	
	/**
	 * 上传图片根目录
	 */
	public final static String UPLOADPATH = "/uploads/";
	
	/**
	 * 默认上传文件大小KB
	 */
	public final static int DEFAULTFILESIZE = 100;
	
	/**
	 * 默认上传图片的宽、高
	 */
	public final static String DEFAULTUPLOADSIZE = "256_256";
	
	/**
	 * 接口分页每页最大记录数
	 */
	public final static int MAXINTERFACERESULT = 10;
	
	/**
	 * 获取网站根路径
	 * @param request
	 * @return
	 */
	public static String GETBASEPATH(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
	
	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddrByRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    } 
}
