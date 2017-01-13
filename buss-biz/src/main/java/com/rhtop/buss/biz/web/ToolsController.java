package com.rhtop.buss.biz.web;

import java.io.File;
import java.io.FileInputStream;
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
import com.rhtop.buss.common.web.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

/**
 * 用于图片上传、下载、删除或excel的导入、导出功能
 * @author 李彬彬
 *
 */
@Controller
@RequestMapping("/service/tools")
public class ToolsController extends BaseController {
	
	/**
	 * 文件上传操作
	 * @param file
	 * @param upload
	 * @param request
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public AjaxObject upload(MultipartFile file, String json, HttpServletRequest request, HttpServletResponse res)
			throws Exception {
		String basePath = Constant.GETBASEPATH(request);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain;charset=utf-8");
		UploadBean upload = new ObjectMapper().readValue(json, UploadBean.class);
		AjaxObject result = new AjaxObject("");
		String relativeFolder = Constant.UPLOADPATH + upload.getFolder() + File.separator + DateUtils.getToday("yyyyMMdd") + File.separator;
		//设置保存路径，如果路径不存在，则自动创建
		File saveFolder = new File(basePath + relativeFolder);
		if (!saveFolder.exists()){
			saveFolder.mkdirs();
		}
		//取文件后缀名
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		File localFile = new File(saveFolder.getAbsolutePath() + File.separator  + new Date().getTime() + suffix);
		Files.write(file.getBytes(), localFile);
		
		result.setStatusCode(AjaxObject.STATUS_CODE_SUCCESS);
		result.setMessage((relativeFolder+localFile.getName()).replaceAll("\\\\", "\\/"));
		result.setRel(upload.getCallbackid());
		
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
