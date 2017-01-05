package com.rhtop.buss.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.rhtop.buss.common.web.HtmlMessage;

public class FileUtil {
	private int status = 1;//1表示操作过程无错误。
    /**
     * 将信息从指定的输入流中读取，并写入到指定的输出流中
     * @param input
     * @param output
     */
    public static void write(InputStream input,OutputStream output) throws IOException{
        int length=0;
        byte[] info=new byte[1024];
        while((length=input.read(info))!=-1){
            output.write(info,0,length);
        }
    }
	/**
	 * 
	 * @param file
	 * @return 图片的文件名或者是null，
	 */
	public HtmlMessage uploadOneFile(MultipartFile file){
		String locPath = null;
		try {
			//从配置文件中读取上传文件的存放路径
			locPath = PropertyUtil.getCommonProp("picUploadPath");
			//读取文件后缀名
			String fileName = file.getName();
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
			//生成前缀
			String perfix = UUID.randomUUID().toString().replace("-", "").substring(10);
			//组成在服务器保存的文件名
			fileName = perfix+suffix;
			//创建空文件
			File pic = new File(locPath, fileName);
			//如果新文件创建成功，就向其写入信息
			if(pic.createNewFile()){
				OutputStream os = new FileOutputStream(pic);
				FileUtil.write(file.getInputStream(), os);
				if(os!=null){
					os.close();
				}
			}
			return new HtmlMessage;
		} catch (Exception e) {
			e.printStackTrace();
			status = 0;
		}
		return null;
		
	}
}
