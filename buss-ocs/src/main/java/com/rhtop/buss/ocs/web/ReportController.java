package com.rhtop.buss.ocs.web;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.utils.AjaxObject;
import com.rhtop.buss.common.web.BaseController;

/**
 * 文件操作控制层。进行文件的上传、下载、删除，excel的导入/导出操作
 * @author 李彬彬
 * 
 */
@Controller
@RequestMapping("/report")
@SuppressWarnings("unused")
public class ReportController extends BaseController {
	@Autowired(required = false)
	private RestService rs;
	private @Value("${biz.server.url}") String coreUrl;
	
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
	public AjaxObject upload(@RequestParam(value = "file") MultipartFile[] files, 
					   HttpServletRequest request, 
					   HttpServletResponse res)throws Exception {
		URI uri = new URI(coreUrl + "/service/category/excelImportFile");
		MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		List<File> localFiles = new ArrayList<File>();
		mvm.add("userId", ((User)SecurityUtils.getSubject().getPrincipal()).getUserId().toString());
		for (int i=0; i<files.length; i++){
			MultipartFile file = files[i];
			File localFile = new File("/" + file.getOriginalFilename());
			Files.write(file.getBytes(), localFile);
			mvm.add("file", new FileSystemResource(localFile));
			localFiles.add(localFile);
		}
		
		RestTemplate restTemplate = new RestTemplate();
		AjaxObject result = restTemplate.postForObject(uri, mvm, AjaxObject.class);
		for (int i=0; i<localFiles.size(); i++){
			if (localFiles.get(i).exists()) {
				localFiles.get(i).delete();
			}
		}
		Thread.sleep(500);
		return result;
	}
}
