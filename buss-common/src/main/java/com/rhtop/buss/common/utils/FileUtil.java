package com.rhtop.buss.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;


public class FileUtil {
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
     * 在这里根据指定的若干名称按顺序在指定的根目录下生成新目录
     * @param rootPath
     * @param dirs
     * @return
     */
    public static File findOrCreateDirectory(String rootPath,String... dirs) {
        boolean found=true;
        File file=new File(rootPath);
        //开始遍历给定的目录名
        for(String dir:dirs){
            //如果文件存在且为目录
            if(file.exists()&&file.isDirectory()){
                //就遍历该文件下所有的文件名，并查找符合条件的目录
                File[] files=file.listFiles();
                for(File f:files){
                    //如果查找到了满足条件的目录
                    if(f.getName().equals(dir)&&f.isDirectory()){
                        //就以该目录为起点继续往下查找
                        file=new File(file,f.getName());
                        found=false;
                        break;
                    }
                }
                //如果没有找到对应的目录，就新创建一个目录
                if (found) {
                    file=new File(file,dir);
                    file.mkdir();
                }
                found=true;
            }
        }
        //处理完成后返回
        return file;
    }
	/**
	 * 上传一个文件的方法，会自动找配置文件中的文件存储根目录，按照日期生成当日的文件夹，在将文件重命名加五位随机数储存，返回储存的相对路径。
	 * @param file 文件 要求ContentType必须为MultipartFile
	 * @return 文件相对路径
	 */
	public static String uploadOneFile(MultipartFile file){
		String locRootPath = null;
		try {
			//从配置文件中读取上传文件的存放根路径
			locRootPath = PropertyUtil.getCommonProp("picUploadPath");
			//按日期生成中间文件夹
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String uploadPath = sdf.format(date); 
			File dir = FileUtil.findOrCreateDirectory(locRootPath, uploadPath);
			//读取文件后缀名
			String fileName = file.getName();
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
			//生成前缀,32位UUID+5位随机数
			double ran = 100000.0*Math.random();
			int random = (int)ran;
			String perfix = UUID.randomUUID().toString().replace("-", "")+random;
			//组成在服务器保存的文件名
			fileName = perfix+suffix;
			//储存该文件在服务器中保存的相对路径（文件夹/文件名）
			String filePath = dir + File.pathSeparator + fileName;
			//创建空文件
			File pic = new File(dir, fileName);
			//如果新文件创建成功，就向其写入信息
			if(pic.createNewFile()){
				OutputStream os = new FileOutputStream(pic);
				FileUtil.write(file.getInputStream(), os);
				if(os!=null){
					os.close();
				}
			}
			//返回文件相对路径
			return filePath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//出错则返回空。
		return null;
		
	}
}
