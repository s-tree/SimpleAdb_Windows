﻿package com.jingxi.officetest.util;

import com.jingxi.officetest.observers.MessageEvent;

import java.io.File;

public class OTAUpdate {
    static String autoUpdateZipFile = "./AutoZip.apk";
    static String autoUpdateImageFile = "./AutoUpdateImg.apk";

    public static String update(String deviceName,String filePath){
        File autoZipFile = new File(autoUpdateZipFile);
        if(!autoZipFile.exists()){
            return "AutoZip.apk 未能找到，请将该apk放置在此同级目录";
        }
        if(filePath == null || filePath.trim().isEmpty()){
            return "请选择要更新的ota包";
        }
        if(!filePath.endsWith(".zip")){
            return "请选择正确的ota更新包";
        }
        File file = new File(filePath);
        if(!file.exists()){
            return "要更新的OTA文件不存在";
        }
        String[] results = RuntimeUtil.execWithMessage("adb -s " + deviceName + " push " + filePath + " /sdcard/update.zip");
        if(results[0] != null){
            return results[1];
        }
        String[] md5 = RuntimeUtil.exec("adb -s " + deviceName + " shell md5sum /sdcard/update.zip");
        if(md5[0] != null){
            return results[1];
        }
        String deviceMd5 = md5[1].split("\\s+")[0];
        String localMd5 = FileUtil.getMD5Checksum(filePath);
        if(!deviceMd5.trim().toLowerCase().equals(localMd5.trim().toLowerCase())){
            return "文件传输不完整，请重新升级";
        }
        MessageEvent.append("正在安装必要软件");
        String[] installApk = RuntimeUtil.exec("adb -s " + deviceName + " install -r -d AutoZip.apk");
        if(installApk[0] != null){
            return installApk[1];
        }
        String[] otaStart = RuntimeUtil.exec("adb -s " + deviceName + " shell am start -n com.jingxi.smartlife.autozip/.MainActivity");
        if(otaStart[0] != null){
            return otaStart[1];
        }
        return "设备正在准备升级，请耐心等待";
    }

    public static String updateImage(String deviceName,String filePath){
        File autoZipFile = new File(autoUpdateImageFile);
        if(!autoZipFile.exists()){
            return "AutoUpdateImg.apk 未能找到，请将该apk放置在此同级目录";
        }
        if(filePath == null || filePath.trim().isEmpty()){
            return "请选择要更新的固件包";
        }
        if(!filePath.endsWith(".img")){
            return "请选择正确的固件包";
        }
        File file = new File(filePath);
        if(!file.exists()){
            return "要更新的固件包文件不存在";
        }
        String[] results = RuntimeUtil.execWithMessage("adb -s " + deviceName + " push " + filePath + " /sdcard/update.img");
        if(results[0] != null){
            return results[1];
        }
        String[] md5 = RuntimeUtil.exec("adb -s " + deviceName + " shell md5sum /sdcard/update.img");
        if(md5[0] != null){
            return results[1];
        }
        String deviceMd5 = md5[1].split("\\s+")[0];
        String localMd5 = FileUtil.getMD5Checksum(filePath);
        if(!deviceMd5.trim().toLowerCase().equals(localMd5.trim().toLowerCase())){
            return "文件传输不完整，请重新升级";
        }
        MessageEvent.append("正在安装必要软件");
        String[] installApk = RuntimeUtil.exec("adb -s " + deviceName + " install -r -d AutoUpdateImg.apk");
        if(installApk[0] != null){
            return installApk[1];
        }
        String[] otaStart = RuntimeUtil.exec("adb -s " + deviceName + " shell am start -n com.jingxi.smartlife.autozip/.MainActivity");
        if(otaStart[0] != null){
            return otaStart[1];
        }
        return "设备正在准备升级，请耐心等待";
    }

}
