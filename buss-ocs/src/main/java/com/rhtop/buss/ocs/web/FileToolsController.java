package com.rhtop.buss.ocs.web;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.rhtop.buss.common.entity.UploadBean;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.utils.AjaxObject;
import com.rhtop.buss.common.web.BaseController;
import com.google.common.io.Files;

/**
 * 文件操作控制层。进行文件的上传、下载、删除，excel的导入/导出操作
 * @author 李彬彬
 * 
 */
@Controller
@RequestMapping("/file")
public class FileToolsController extends BaseController {
	@Autowired(required = false)
	private RestService rs;
	private @Value("${biz.server.url}") String coreUrl;
	
	/**
	 * 查看图片，前台传入filepath，图片的相对地址
	 * @param upload
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
	public void getImageFromUrl(UploadBean upload, HttpServletRequest request, HttpServletResponse response) throws Exception{
		URI uri = new URI(coreUrl + upload.getFilepath());
		HttpURLConnection connect = (HttpURLConnection)uri.toURL().openConnection();
		connect.setDoInput(true);
		InputStream in = connect.getInputStream();
		ImageIO.write(ImageIO.read(in), "JPEG", response.getOutputStream());
	}
	/**
	 * 上传文件
	 * @param file
	 * @param upload
	 * @param request
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public AjaxObject upload(@RequestParam(value = "file") MultipartFile file, 
					   UploadBean upload, 
					   HttpServletRequest request, 
					   HttpServletResponse res)throws Exception {
		File localFile = new File("C:/work/" + file.getOriginalFilename());
		Files.write(file.getBytes(), localFile);
		URI uri = new URI(coreUrl + "service/tools/upload");
		MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		mvm.add("json", upload.toString());
		mvm.add("file", new FileSystemResource(localFile));
		RestTemplate restTemplate = new RestTemplate();
		AjaxObject result = restTemplate.postForObject(uri, mvm, AjaxObject.class);
		if (localFile.exists()) {
			localFile.delete();
		}
		
		return result;
	}
	
	/**
	 * 上传文件给指定的service进行处理，不进行文件保存操作。主要适用于excel导入
	 * @param service
	 * @param file
	 * @param upload
	 * @param request
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importFile/{service}")
	@ResponseBody
	public AjaxObject upload(@PathVariable(value = "service") String service, @RequestParam(value = "file") MultipartFile file, 
					   HttpServletRequest request, 
					   HttpServletResponse res)throws Exception {
		File localFile = new File("/" + file.getOriginalFilename());
		Files.write(file.getBytes(), localFile);
		URI uri = new URI(coreUrl + "/service/" + service.replaceAll("-", "/"));
		MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		mvm.add("file", new FileSystemResource(localFile));
		mvm.add("userId", ((User)SecurityUtils.getSubject().getPrincipal()).getUserId());
		RestTemplate restTemplate = new RestTemplate();
		AjaxObject result = restTemplate.postForObject(uri, mvm, AjaxObject.class);
		if (localFile.exists()) {
			localFile.delete();
		}
		
		return result;
	}
	
	/**
	 * 删除文件,传filepath，图片的相对地址
	 * @param file
	 * @param upload
	 * @param request
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxObject delete(UploadBean upload, 
					   HttpServletRequest request, 
					   HttpServletResponse res)throws Exception {
		URI uri = new URI(coreUrl + "/service/tools/delete");
		MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		upload.setOpMethod("delete");
		mvm.add("json", upload.toString());
		RestTemplate restTemplate = new RestTemplate();
		AjaxObject result = restTemplate.postForObject(uri, mvm, AjaxObject.class);
		
		return result;
	}
}
