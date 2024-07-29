package com.jingxi.officetest.util;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {
    public static final String CURRENT_APK_PATH = "CURRENT_APK_PATH";
    public static final String CURRENT_OTA_PATH = "CURRENT_OTA_PATH";
    public static final String CURRENT_IMG_PATH = "CURRENT_IMG_PATH";

    public static final String fileName = "config.ini";
    static Properties properties;

    public static void init(){
        properties = new Properties();
        File propertiesFile = new File(fileName);
        if(!propertiesFile.exists()){
            try {
                propertiesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(propertiesFile);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String read(String key){
        return properties.getProperty(key);
    }

    public static void update(String key,String value){
        properties.setProperty(key,value);
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(fileName);
            properties.store(outputStream,"");
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
