package com.rhtop.buss.ocs.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.common.service.RestService;
import com.rhtop.buss.common.web.BaseController;

@Controller
@RequestMapping("/sys/excel")
public class ExcelController extends BaseController {
	@Autowired
	private RestService service;
	
	
	@RequestMapping("category/export")
	public void excelCategoryExport(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("cateName") String cateName,@RequestParam("manuNum") String manuNum){
		Category category = new Category();
		category.setCateName(cateName);
		category.setManuNum(manuNum);
		JSONObject jsonCategory = JSONObject.fromObject(category);
		ResultInfo readResult = (ResultInfo) service.invoke("category-listCategorys", "POST", jsonCategory.toString() , ResultInfo.class);
		List categorys = readResult.getRecords();
		
        HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = wb.createSheet("品类信息表");  
        HSSFRow row = sheet.createRow(0);  
        
        CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(cra);
        
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        
        HSSFCell firstCell = row.createCell(0);   
        firstCell.setCellValue("品类管理（导出）");
        firstCell.setCellStyle(style);
        
        row = sheet.createRow(1);
        HSSFCell cell = row.createCell(0);  
        cell.setCellValue("品类名称");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("规格");  
        cell.setCellStyle(style);  
        cell = row.createCell(2);  
        cell.setCellValue("包装");  
        cell.setCellStyle(style);  
        cell = row.createCell(3);  
        cell.setCellValue("厂号");  
        cell.setCellStyle(style);  
        cell = row.createCell(4);  
        cell.setCellValue("产地");  
        cell.setCellStyle(style); 
        cell = row.createCell(5);  
        cell.setCellValue("备注（切割方式）");  
        cell.setCellStyle(style); 
        /*cell = row.createCell(6);  
        cell.setCellValue("地区");  
        cell.setCellStyle(style); 
        cell = row.createCell(7);  
        cell.setCellValue("客户经理");  
        cell.setCellStyle(style); 
        cell = row.createCell(8);  
        cell.setCellValue("批发价");  
        cell.setCellStyle(style); 
        cell = row.createCell(9);  
        cell.setCellValue("接盘价");  
        cell.setCellStyle(style); 
        cell = row.createCell(10);  
        cell.setCellValue("现货价");  
        cell.setCellStyle(style); 
        cell = row.createCell(11);  
        cell.setCellValue("半期货价");  
        cell.setCellStyle(style);
        cell = row.createCell(12);  
        cell.setCellValue("期货价");  
        cell.setCellStyle(style);
        cell = row.createCell(13);  
        cell.setCellValue("供应商");  
        cell.setCellStyle(style);
        cell = row.createCell(14);  
        cell.setCellValue("报盘价");  
        cell.setCellStyle(style);
        cell = row.createCell(15);  
        cell.setCellValue("价格时效");  
        cell.setCellStyle(style);*/
  
        for (int i = 0; i < categorys.size(); i++){  
            row = sheet.createRow((int) i + 2);  
            LinkedHashMap lhMap = (LinkedHashMap) categorys.get(i);
            if(lhMap == null){continue;};
            //创建单元格，并设置值  
            row.createCell(0).setCellValue(lhMap.get("cateName").toString());  
            row.createCell(1).setCellValue(lhMap.get("cateStan").toString());  
            row.createCell(2).setCellValue(lhMap.get("pkgQuan").toString());
            row.createCell(3).setCellValue(lhMap.get("manuNum").toString());
            row.createCell(4).setCellValue(lhMap.get("prodPla").toString());  
            row.createCell(5).setCellValue(lhMap.get("comm").toString());  
            /* row.createCell(6).setCellValue(lhMap.get("cusLoc").toString());
            row.createCell(7).setCellValue(lhMap.get("客户经理").toString());
            row.createCell(8).setCellValue(lhMap.get("relCateGoryPrice.wholesalePri").toString());  
            row.createCell(9).setCellValue(lhMap.get("relCateGoryPrice.acptPri").toString());  
            row.createCell(10).setCellValue(lhMap.get("relCateGoryPrice.").toString());
            row.createCell(11).setCellValue(lhMap.get("relCateGoryPrice.").toString());
            row.createCell(12).setCellValue(lhMap.get("relCateGoryPrice.").toString());  
            row.createCell(13).setCellValue(lhMap.get("relCateGoryPrice.").toString());  
            row.createCell(14).setCellValue(lhMap.get("relCateGoryPrice.").toString());
            row.createCell(15).setCellValue(lhMap.get("relCateGoryPrice.").toString());*/
        }
        
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
	        String fileName = "品类信息表";
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        wb.write(os);
	        byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);
	        // 设置response参数，可以打开下载页面
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="
	            + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
	        ServletOutputStream out = response.getOutputStream();
	        
	        bis = new BufferedInputStream(is);
	        bos = new BufferedOutputStream(out);
	        byte[] buff = new byte[2048];
	        int bytesRead;
	        // Simple read/write loop.
	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	        	bos.write(buff, 0, bytesRead);
	        }	
        } catch (Exception e) {
        	e.printStackTrace();
        	log.error("[ExcelController.excelCategoryExport]IO异常", e);
        } finally {
        	if (bis != null)
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("[ExcelController.excelCategoryExport]IO异常", e);
			}
        	if (bos != null)
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("[ExcelController.excelCategoryExport]IO异常", e);
			}
        }
	}
}
