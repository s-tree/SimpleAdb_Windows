package com.jingxi.officetest.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.jingxi.officetest.util.FileUtil;
import com.jingxi.officetest.util.IOUtil;

public class NetworkManager {

	public static String getUrl(String url){
		if(url.isEmpty()){
			return "";
		}
		StringBuilder builder = new StringBuilder();
		InputStream input = null;
		byte[] datas = new byte[10240];
		int index = -1;
		try{
			URL u = new URL(url);
			input = u.openStream();
			while((index = input.read(datas)) != -1){
//				builder.append(new String(new String(datas,0,index).getBytes(),"UTF-8"));
				builder.append(new String(datas,0,index,"UTF-8"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			IOUtil.close(input);
		}
		return builder.toString();
//		return "name\n"
//				+ "չʾ���� www.baidu.com\n"
//				+ "����1 www.baidu.com\n"
//				+ "����2 www.baidu.com\n"
//				+ "����3 www.baidu.com\n"
//				+ "����4 www.baidu.com\n";
	}
	
	public static boolean download(String url,String filePath,String md5){
		File localFile = new File(filePath);
		if(localFile.exists()){
			String localMD5 = FileUtil.getMD5Checksum(filePath).toUpperCase();
			FileUtil.logFile("download localFile = " + localFile.getAbsolutePath() + " localMd5 = " + localMD5 + " remoteMD5 = " + md5);
			if(localMD5.equals(md5)){
				return true;
			}
		}
		localFile.delete();
		try{
			localFile.createNewFile();
		}catch(Exception e){
			e.printStackTrace();
		}
		InputStream input = null;
		OutputStream output = null;
		byte[] datas = new byte[10240];
		int index = -1;
		try{
			URL u = new URL(url);
			input = u.openStream();
			output = new FileOutputStream(localFile);
			while((index = input.read(datas)) != -1){
				output.write(datas, 0, index);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			IOUtil.close(input);
			IOUtil.close(output);
		}
		return true;
	}
}
