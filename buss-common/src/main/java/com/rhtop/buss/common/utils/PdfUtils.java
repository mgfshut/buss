 package com.rhtop.buss.common.utils;  

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.rhtop.buss.common.exception.BusException;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
  
  
@SuppressWarnings("restriction")
public class PdfUtils {  
	
	
	/**
	 * 通过模板生成HTML
	 * @author mgf
	 * @date 2017年1月11日 下午3:37:09 
	 * @param ftlPath
	 * @param ftlName
	 * @param params
	 * @return
	 */
	public static InputStream generateHtml(String ftlPath,String ftlName,Map<String,Object> params){
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setOutputEncoding("UTF-8");
		File ftlFile = new File(ftlPath);
		if(ftlFile.exists()){
			try {
				cfg.setDirectoryForTemplateLoading(ftlFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BusException("模板目录设置出现异常！");
			}
		}else{
			throw new BusException("文件路径不正确！");
		}
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		
		Template template = null;
		try {
			template = cfg.getTemplate(ftlName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusException("模板文件未找到！");
		}
		StringWriter writer = new StringWriter();
		try {
			template.process(params, writer);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusException("模板程序处理异常！");
		}
		ByteArrayInputStream input = new ByteArrayInputStream(writer.toString().getBytes());
		return input;
	}
	/**
	 * HTML转换成PDF文件
	 * @author mgf
	 * @date 2017年1月11日 下午4:26:59 
	 * @param ftlPath 模板文件路径
	 * @param ftlName 模板文件名称
	 * @param params  参数map
	 * @param pdfFile PDF文件全路径
	 */
	public static String pdfGennernator(String ftlPath,String ftlName,Map<String,Object> params,String pdfFile){
		String resFile = null;
		InputStream inputStream = generateHtml(ftlPath, ftlName, params);
		Document document = new Document();
		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BusException(pdfFile+"文件未找到异常！");
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new BusException("document异常！");
		}
		document.open();
		try {
			XMLWorkerHelper.getInstance().parseXHtml(writer, document,inputStream, Charset.forName("UTF-8"));
			resFile = pdfFile;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusException("io异常！");
		}
		document.close();
		return resFile;
	}
	
	/**
	 * PDF转换图片
	 * @author mgf
	 * @date 2017年1月11日 下午5:38:50 
	 * @param pdfFile PDF文件全路径
	 * @return
	 */
	@SuppressWarnings({ "resource" })
	public static String setup(String pdfFile){  
		String resFile = null;
        File file = new File(pdfFile);  
        String imgPath = pdfFile.split("\\.")[0];
        PDFFile pdffile = null;
		try {
			RandomAccessFile raf = new  RandomAccessFile(file,  "r" );  
	        FileChannel channel = raf.getChannel();  
	        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0 , channel  
	                .size()); 
			pdffile = new  PDFFile(buf);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusException("io异常！");
		}  
  
		StringBuffer buff = new StringBuffer();
        for(int i =  1 ; i <= pdffile.getNumPages(); i++) {
            PDFPage page = pdffile.getPage(i);
  
            Rectangle rect = new  Rectangle(0,0,(int)page.getBBox()  
                    .getWidth(), (int) page.getBBox().getHeight());  
            Image img = page.getImage(rect.width, rect.height,rect,null,true,true);  
            BufferedImage tag = new  BufferedImage(rect.width, rect.height,  
                    BufferedImage.TYPE_INT_RGB);  
            tag.getGraphics().drawImage(img, 0 ,  0 , rect.width, rect.height,  
                    null );  
			try {
				FileOutputStream out = new  FileOutputStream(imgPath + i + ".jpg");
				com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(out);  
	            encoder.encode(tag); // JPEG编码   
	            out.close(); 
	            buff.append(imgPath + i + ".jpg,");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new BusException(imgPath + i + ".jpg 文件不存在");
			}  // 输出到文件流   
			catch (ImageFormatException e) {
				e.printStackTrace();
				throw new BusException("转换图片异常");
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusException("IO异常");
			}
        }
        if(buff.length() > 0){
        	resFile = buff.substring(0,buff.length()-1);
        }
		return resFile;  
  
    }   
	
	public static void main(String[] args) throws IOException {
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("contractNo", "sdfasfdasfdsdaf");
		String pdfFile = pdfGennernator("C:/work/git/java/buss-biz/src/main/webapp/WEB-INF/configs", "sellTemp.ftl", params, "C:/sellTemp.pdf");
		String imgFilePath = setup(pdfFile);
		System.out.println(imgFilePath);
	}
} 