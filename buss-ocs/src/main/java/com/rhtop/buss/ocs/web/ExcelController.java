package com.rhtop.buss.ocs.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.BaseController;

@Controller
@RequestMapping("/sys/excel")
public class ExcelController extends BaseController {
	@Autowired
	private RestService service;
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("category/export")
	public void excelCategoryExport(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("cateName") String cateName,@RequestParam("manuNum") String manuNum){
		Category category = new Category();
		category.setCateName(cateName);
		category.setManuNum(manuNum);
		JSONObject jsonCategory = JSONObject.fromObject(category);
		ResultInfo readResult = (ResultInfo) service.invoke("relCustomerCategory-categoryExportList", "POST", jsonCategory.toString() , ResultInfo.class);
		List relCustomerCategorys = readResult.getRecords();
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		String s_path = servletContext.getRealPath("/");
		s_path = s_path.concat("WEB-INF/configs/templet");
		
		//首先:从本地磁盘读取模板excel文件,然后读取第一个sheet  
		InputStream inp = null;
		try {
			inp = new FileInputStream(s_path+File.separator+"categoryExport.xls");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			log.error("[ExcelController.excelCategoryExport]文件未找到异常", e1);
		} 
        
        POIFSFileSystem fs = null;
		try {
			fs = new POIFSFileSystem(inp);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("[ExcelController.excelCategoryExport]IO异常", e);
		}  
        Workbook wb = null;
		try {
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("[ExcelController.excelCategoryExport]IO异常", e);
		}  
        Sheet sheet=wb.getSheetAt(0);  
          
        //开始写入数据到模板中: 需要注意的是,因为行头以及设置好,故而需要跳过行头  
        Row row = sheet.createRow(2); 
        
        for (int i = 0; i < relCustomerCategorys.size(); i++){  
            row = sheet.createRow((int) i + 2);  
            LinkedHashMap lhMap = (LinkedHashMap) relCustomerCategorys.get(i);
            if(lhMap == null){continue;};
            //创建单元格，并设置值  
            if(lhMap.get("cateName") != null)
            	row.createCell(0).setCellValue(lhMap.get("cateName").toString());  
            if(lhMap.get("cateStan") != null)
            	row.createCell(1).setCellValue(lhMap.get("cateStan").toString());  
            if(lhMap.get("pkgQuan") != null)
            	row.createCell(2).setCellValue(lhMap.get("pkgQuan").toString()); 
            if(lhMap.get("manuNum") != null)
            	row.createCell(3).setCellValue(lhMap.get("manuNum").toString());
            if(lhMap.get("prodPla") != null)
            	row.createCell(4).setCellValue(lhMap.get("prodPla").toString());    
            if(lhMap.get("comm") != null)
            	row.createCell(5).setCellValue(lhMap.get("comm").toString());  
            if(lhMap.get("cusLoc") != null)
            	row.createCell(6).setCellValue(lhMap.get("cusLoc").toString());  
            if(lhMap.get("cusChaVal") != null)
            	row.createCell(7).setCellValue(lhMap.get("cusChaVal").toString());    
            if(lhMap.get("memberName") != null)
            	row.createCell(8).setCellValue(lhMap.get("memberName").toString());
            if(lhMap.get("wholesalePri") != null)
            	row.createCell(9).setCellValue(lhMap.get("wholesalePri").toString());
            if(lhMap.get("acptPri") != null)
            	row.createCell(10).setCellValue(lhMap.get("acptPri").toString());  
            if(lhMap.get("spotMin") != null && lhMap.get("spotMax") != null )
            	row.createCell(11).setCellValue(lhMap.get("spotMin").toString()+"-"+lhMap.get("spotMax").toString());  
            if(lhMap.get("interFutMin") != null && lhMap.get("interFutMax") != null )
            	row.createCell(12).setCellValue(lhMap.get("interFutMin").toString()+"-"+lhMap.get("interFutMax").toString());  
            if(lhMap.get("futMin") != null && lhMap.get("futMax") != null )
            	 row.createCell(13).setCellValue(lhMap.get("futMin").toString()+"-"+lhMap.get("futMax").toString());  
            if(lhMap.get("cateSup") != null)
            	row.createCell(14).setCellValue(lhMap.get("cateSup").toString()); 
            if(lhMap.get("offerPri") != null)
            	row.createCell(15).setCellValue(lhMap.get("offerPri").toString()); 
            if(lhMap.get("offerAging") != null)
            	row.createCell(16).setCellValue(lhMap.get("offerAging").toString()); 
        }
        try {
			response.setHeader("Content-Disposition", "attachment;filename="+new String(("品类信息表_"+DateUtils.getToday()+".xls").getBytes("utf-8"),"iso8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("[ExcelController.excelCategoryExport]编码异常", e);
		}  
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");  
		try {
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.flush();  
	        out.close();  
		} catch (IOException e) {
			e.printStackTrace();
			log.error("[ExcelController.excelCategoryExport]IO异常", e);
		}  
	}
	
	@RequestMapping("exportDictionary")
	public void excelDictionaryExport(HttpServletRequest request,HttpServletResponse response, String codeValue){
		ResultInfo readResult = (ResultInfo) service.invoke("codeValue-exportList", "POST", "{\"codeValue\":\""+codeValue+"\"}", ResultInfo.class);
		List dictionaryList = readResult.getRecords();
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		String s_path = servletContext.getRealPath("/");
		s_path = s_path.concat("WEB-INF/configs/templet");
		
		//首先:从本地磁盘读取模板excel文件,然后读取第一个sheet  
		InputStream inp = null;
		try {
			inp = new FileInputStream(s_path+File.separator+"temp-dic.xls");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			log.error("[ExcelController.excelDictionaryExport]文件未找到异常", e1);
		} 
        
        POIFSFileSystem fs = null;
		try {
			fs = new POIFSFileSystem(inp);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("[ExcelController.excelDictionaryExport]IO异常", e);
		}  
        Workbook wb = null;
		try {
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("[ExcelController.excelDictionaryExport]IO异常", e);
		}  
        Sheet sheet=wb.getSheetAt(0);  
          
        //开始写入数据到模板中: 需要注意的是,因为行头以及设置好,故而需要跳过行头  
        Row row = sheet.createRow(2); 
        
        for (int i = 0; i < dictionaryList.size(); i++){  
            row = sheet.createRow((int) i + 2);  
            LinkedHashMap item = (LinkedHashMap)dictionaryList.get(i);
            if(item == null){continue;};
            row.createCell(0).setCellValue(item.get("codeMapName") == null ? "" :item.get("codeMapName").toString());  
            //row.createCell(1).setCellValue(item.get("codeValue") == null ? "" :item.get("codeValue").toString());  
            row.createCell(1).setCellValue(item.get("codeValueDescribe") == null ? "" :item.get("codeValueDescribe").toString());  
        }
        try {
			response.setHeader("Content-Disposition", "attachment;filename="+new String(("字典代码表_"+DateUtils.getToday()+".xls").getBytes("utf-8"),"iso8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("[ExcelController.excelDictionaryExport]编码异常", e);
		}  
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");  
		try {
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.flush();  
	        out.close();  
		} catch (IOException e) {
			e.printStackTrace();
			log.error("[ExcelController.excelDictionaryExport]IO异常", e);
		}  
	}
}
