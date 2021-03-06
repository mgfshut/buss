package com.rhtop.buss.biz.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rhtop.buss.common.entity.UploadBean;
import com.rhtop.buss.common.utils.AjaxObject;
import com.rhtop.buss.common.utils.Constant;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.utils.PropertyUtil;
import com.rhtop.buss.common.web.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

/**
 * 用于图片上传、下载、删除或excel的导入、导出功能
 * @author 李彬彬
 *
 */
@Controller
@RequestMapping("/service/file")
public class FileController extends BaseController {
	@RequestMapping("/upload")
	@ResponseBody
	public AjaxObject uploadPhoto(@RequestParam(value = "file") MultipartFile[] files, 
			HttpServletRequest request, HttpServletResponse res)throws Exception{
//		String basePath = Constant.GETBASEPATH(request);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain;charset=utf-8");
		AjaxObject result = new AjaxObject("");
		
		String rootPath = "/filestore/";
		try {
			PropertyUtil propertyUtil = new PropertyUtil("properties/common.properties");
			//从配置文件中读取上传文件的存放根路径
			rootPath = propertyUtil.readValue("file.root.path");
		} catch (IOException e1) {
			e1.printStackTrace();
			log.error("[CategoryController.excelImport]IO异常", e1);
		}
		
		String path = "upload"+File.separator+DateUtils.getToday("yyyyMMdd")+File.separator;
		//设置保存路径，如果路径不存在，则自动创建
		File saveFolder = new File(rootPath+path);
		if (!saveFolder.exists()){
			saveFolder.mkdirs();
		}
		String filePath = "";
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<files.length; i++){
			MultipartFile file = files[i];
			//取文件后缀名
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
			File localFile = new File(saveFolder.getAbsolutePath() + File.separator  + new Date().getTime() + suffix);
			Files.write(file.getBytes(), localFile);
			filePath = (path+localFile.getName()).replaceAll("\\\\", "\\/");
			buff.append(filePath+",");
		}
		filePath = buff.toString().substring(0,buff.length()-1);
		if (!"".equals(filePath)){
			result.setStatusCode(AjaxObject.STATUS_CODE_SUCCESS);
			result.setMessage("文件上传成功！");
			result.setRel(filePath);
		}else{
			result.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			result.setMessage("文件上传失败！");
		}
		
		return result;
	}
	
	/**
	 * 文件删除操作
	 * @param json
	 * @param request
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxObject upload(@RequestParam(value = "json")String json, HttpServletRequest request, HttpServletResponse res)throws Exception {
		String basePath = Constant.GETBASEPATH(request);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain;charset=utf-8");
		UploadBean upload = new ObjectMapper().readValue(json, UploadBean.class);
		AjaxObject result = new AjaxObject("");
		if ("delete".equals(upload.getOpMethod())){
			//文件删除操作
			if (StringUtils.isNotEmpty(upload.getFilepath())){
				File deleteFile = new File(basePath + upload.getFilepath());
				deleteFile.delete();
				result.setStatusCode(AjaxObject.STATUS_CODE_SUCCESS);
				result.setMessage("文件删除成功！");
			}else{
				result.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
				result.setMessage("删除文件必须指明文件名！");
			}
		}else{
			result.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			result.setMessage("无法执行删除操作！");
		}
		
		return result;
	}
	
	/**
	 * 文件查看
	 * @param imageID
	 * @param size
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
	public void getImageFromUrl(@RequestParam("json") String json, HttpServletRequest request, HttpServletResponse response) throws Exception{
		UploadBean upload = new ObjectMapper().readValue(json, UploadBean.class);
		if (StringUtils.isEmpty(upload.getFilepath())){
			return;
		}
		
		File imageFile = new File(Constant.GETBASEPATH(request) + upload.getFilepath());
		if (!imageFile.exists()){
			String path = null;
			if (StringUtils.isNotEmpty(upload.getDefaultPhoto())){
				path = upload.getDefaultPhoto();
			}else{
				path = Constant.NOPIC;
			}
			//如果是缩略图不存在，则显示原图
			imageFile = new File(Constant.GETBASEPATH(request) + path);
		}
		InputStream in = new FileInputStream(imageFile);
		ImageIO.write(ImageIO.read(in), "PNG", response.getOutputStream());
	}
}
