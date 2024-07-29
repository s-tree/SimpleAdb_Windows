package com.jingxi.officetest.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jingxi.officetest.observers.MessageEvent;

/**
 * Created by Administrator on 2018/3/13.
 */

public class RuntimeUtil {

    public static String[] exec(String cmd) {
    	return exec(cmd,false);
    }

    public static String[] exec(String cmd,boolean needLog) {
    	if(needLog){
    		FileUtil.logFile(cmd);
    	}
        String[] result = new String[2];
        StringBuilder builder = new StringBuilder();
        builder.delete(0,builder.length());
        InputStream stderr = null;
        InputStream response = null;
        Process proc = null;
        try {
            Runtime rt = Runtime.getRuntime();
            proc = rt.exec(cmd);
            int exitVal = 0;
            exitVal = proc.waitFor();
            String line = null;

            if (exitVal == 0) {
                /**
                 * 执行成功读取 response
                 */
                builder.delete(0,builder.length());
                response = proc.getInputStream();
                InputStreamReader reader = new InputStreamReader(response);
                BufferedReader bufferReader = new BufferedReader(reader);
                while ((line = bufferReader.readLine()) != null){
                    builder.append(line);
                    builder.append("\n");
                }
                if(builder.toString().trim().isEmpty()){
                    result[1] = "success";
                }
                else{
                    result[1] = builder.toString();
                }
                return result;
            }
            else{
                result[0] = "error";
                /**
                 * 执行失败读取失败日志
                 */
                stderr = proc.getErrorStream();
                InputStreamReader isr = new InputStreamReader(stderr);
                BufferedReader br = new BufferedReader(isr);
                while ((line = br.readLine()) != null){
                    builder.append(line);
                }
        		FileUtil.logFile("RuntimeUtil error : cmd = " + cmd + " errorMessage = " + builder.toString());
                result[1] = builder.toString();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeIO(stderr);
            closeIO(response);
            if(proc != null){
            	proc.destroy();
            }
        }
        return result;
    }

    public static String[] execWithMessage(String cmd){
        String[] result = new String[2];
        StringBuilder builder = new StringBuilder();
        builder.delete(0,builder.length());
        InputStream stderr = null;
        InputStream response = null;
        Process proc = null;
        try {
            Runtime rt = Runtime.getRuntime();
            proc = rt.exec(cmd);
            int exitVal = 0;
            new ReadResponseThread(proc.getInputStream()).start();
            exitVal = proc.waitFor();
            String line = null;

            if (exitVal == 0) {
                /**
                 * 执行成功读取 response
                 */
                result[1] = "success";
                return result;
            }
            else{
                result[0] = "error";
                /**
                 * 执行失败读取失败日志
                 */
                stderr = proc.getErrorStream();
                InputStreamReader isr = new InputStreamReader(stderr);
                BufferedReader br = new BufferedReader(isr);
                while ((line = br.readLine()) != null){
                    builder.append(line);
                }
                result[1] = builder.toString();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeIO(stderr);
            closeIO(response);
            if(proc != null){
            	proc.destroy();
            }
        }
        return result;
    }

    private static void closeIO(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class ReadResponseThread extends Thread{
        InputStream inputStream;

        public ReadResponseThread(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public void run(){
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(reader);
            String temp = "";
            String line = "";
            try{
                while ((line = bufferReader.readLine()) != null){
                    if(temp.isEmpty()){
                        MessageEvent.append(line);
                    }else{
                        MessageEvent.update(temp,line);
                    }
                    temp = line;
                }
            }catch (Exception e){

            }
        }
    }
}
