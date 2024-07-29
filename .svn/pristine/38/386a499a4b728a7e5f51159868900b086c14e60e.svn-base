package com.jingxi.officetest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public class FileUtil {
	public static String WORK_SPACE = System.getProperty("user.dir");
	
	public static File getLogFile(){
		return new File(WORK_SPACE,"log.txt");
	}
	
	public static File getDownloadDir(){
		File dir = new File(WORK_SPACE,"download");
		if(!dir.exists()){
			dir.mkdirs();
		}
		return dir;
	}

    public static String getMD5Checksum(String filename) {
        if (!new File(filename).isFile()) {
            return null;
        }
        byte[] b = createChecksum(filename);
        if(null == b){
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    private static byte[] createChecksum(String filename) {
        InputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            int numRead = -1;
            while ((numRead = fis.read(buffer)) != -1) {
                complete.update(buffer, 0, numRead);
            }
            return complete.digest();
        } catch (FileNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    public static String readFile(File file){
        if(file == null || !file.exists() || file.isDirectory()){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        InputStream inputStream = null;
        try{
            byte[] data = new byte[1024 * 10];
            inputStream = new FileInputStream(file);
            int index = -1;
            while ((index = inputStream.read(data)) != -1){
                builder.append(new String(data,0,index));
            }
        }catch (Exception e){

        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    public static void writeFile(File file,String data){
        if(file == null || file.isDirectory()){
            return;
        }

        StringBuilder builder = new StringBuilder();
        OutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.flush();
        }catch (Exception e){

        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	static SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void logFile(String data){
    	appendFile(getLogFile(),"[" + simpleFormat.format(System.currentTimeMillis()) + "] " + data);
    }
    
    public static void appendFile(File file,String data){
        if(file == null || file.isDirectory()){
            return;
        }

        OutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file,true);
            outputStream.write("\n".getBytes());
            outputStream.write(data.getBytes());
            outputStream.flush();
        }catch (Exception e){

        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
