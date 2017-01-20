package com.rhtop.buss.ocs.web;


import java.io.File;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.rhtop.buss.common.entity.Upgrade;
import com.rhtop.buss.common.utils.AjaxObject;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.integration.RestInvoke;

@Controller
@RequestMapping("/upgrade")
public class UpgradeController  extends BaseController {
	@Autowired
	private RestInvoke rsService;
	
	private @Value("${biz.server.url}") String coreUrl;
	
	@RequestMapping("/uploadNewVer")
	@ResponseBody
	public AjaxObject uploadNewVer(@RequestParam(value = "file") MultipartFile file, Upgrade upgrade, HttpServletRequest request, HttpServletResponse res)throws Exception{
		AjaxObject result = new AjaxObject();
		if (StringUtils.isEmpty(upgrade.getDevType())){
			result.setMessage("请选择更新程序类型！");
			result.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			
			return result;
		}
		if (upgrade.getVersionCode() == null || upgrade.getVersionCode().isNaN()){
			result.setMessage("请输入正确的版本号！");
			result.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			
			return result;
		}
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		URI uri = null;
		try{
			uri = new URI(coreUrl + "/service/upgrade/uploadNewVer");
		}catch(Exception e){
			
		}
		mvm.add("devType", upgrade.getDevType());
		mvm.add("versionCode", upgrade.getVersionCode());
		mvm.add("content", upgrade.getContent());
		File localFile = new File(FileUtils.getTempDirectoryPath() + File.separator + file.getOriginalFilename()) ;
		try{
			Files.write(file.getBytes(), localFile);
			mvm.add("file", new FileSystemResource(localFile));
		}catch(Exception e){
			
		}
		
		result = restTemplate.postForObject(uri, mvm, AjaxObject.class);
		if (localFile.exists()) {
			localFile.delete();
		}
		return result;
	}
}