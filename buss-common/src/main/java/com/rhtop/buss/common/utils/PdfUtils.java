package com.rhtop.buss.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

public class PdfUtils {
	
	public static String createPdf(Map<String, String> map,String pdfTempPath,String filePath){
		try {
			//加载word打印模板
			File templateDocFile = new File(pdfTempPath);
			FileInputStream fis = new FileInputStream(templateDocFile);
			HWPFDocument doc = new HWPFDocument(fis);
			//读取word文本内容
			Range bodyRange = doc.getRange();
			//将值覆盖word模板里面的关键字
			for(Map.Entry<String,String> entry : map.entrySet()){
				bodyRange.replaceText("${"+entry.getKey()+"}", entry.getValue());
			}
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			doc.write(ostream);
			//写入新的word文件
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
	        String ymd = sdf.format(new Date());  
	        filePath += File.separator + ymd;
	        
	        //创建文件夹  
	        File dirFile = new File(filePath);  
	        if (!dirFile.exists()) {  
	            dirFile.mkdirs();  
	        }  
	        
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + ".doc";  
	        
			File outWordFile = new File(filePath + File.separator + newFileName);
			OutputStream outs = new FileOutputStream(outWordFile);
			outs.write(ostream.toByteArray());
			outs.close();
			return ymd+newFileName;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
