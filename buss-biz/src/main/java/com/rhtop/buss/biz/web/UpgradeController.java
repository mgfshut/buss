package com.rhtop.buss.biz.web;


import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.rhtop.buss.biz.service.UpgradeService;
import com.rhtop.buss.common.entity.Upgrade;
import com.rhtop.buss.common.utils.AjaxObject;
import com.rhtop.buss.common.utils.Constant;
import com.rhtop.buss.common.web.BaseController;

@Controller
@RequestMapping("service/upgrade")
public class UpgradeController  extends BaseController {
	@Autowired
	private UpgradeService upgradeService;
	
	
	@RequestMapping("/getCurVer/{devType}")
	@ResponseBody
	public Upgrade getCurVer(@PathVariable String devType){
		try{
			return upgradeService.selectByPrimaryKey(devType);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping("/uploadNewVer")
	@ResponseBody
	public AjaxObject uploadNewVer(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "devType")String devType, @RequestParam(value = "content")String content, @RequestParam(value = "versionCode")Float versionCode, HttpServletRequest request, HttpServletResponse res)throws Exception{
		String basePath = Constant.GETBASEPATH(request);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain;charset=utf-8");
		AjaxObject result = new AjaxObject("");
		String relativeFolder = Constant.UPLOADPATH;
		if (StringUtils.isEmpty(devType)){
			result.setMessage("请选择待更新的程序类型！");
			result.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			
			return result;
		}
		if (versionCode == null || versionCode.floatValue() <= 0){
			result.setMessage("请输入正确的程序版本号！");
			result.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			
			return result;
		}
		//设置保存路径，如果路径不存在，则自动创建
		File saveFolder = new File(basePath + relativeFolder);
		if (!saveFolder.exists()){
			saveFolder.mkdirs();
		}
		Upgrade upgrade = new Upgrade();
		upgrade.setDevType(devType);
		upgrade.setVersionCode(versionCode);
		upgrade.setContent(content);
		upgrade.setDownloadFiles(relativeFolder + file.getOriginalFilename().toLowerCase());
		
		File localFile = new File(saveFolder.getAbsolutePath() + File.separator  + file.getOriginalFilename().toLowerCase());
		Files.write(file.getBytes(), localFile);
		Upgrade exist = upgradeService.selectByPrimaryKey(devType);
		if (exist == null){
			this.upgradeService.insertUpgrade(upgrade);
		}else{
			this.upgradeService.updateUpgrade(upgrade);
		}
		
		result.setMessage("新版本发布成功！");
		result.setCallbackType("");
		return result;
	}
}