package com.jingxi.officetest.util;

import java.io.File;

import com.jingxi.officetest.network.NetworkManager;

public class ExecUtil {

	private static String CMD_DOWNLOAD = "download";
	private static String CMD_ROOT = "root";
	private static String CMD_REBOOT = "reboot";
	private static String CMD_RM = "rm";
	private static String CMD_CP = "cp";
	private static String CMD_INSTALL = "install";
	private static String CMD_UNINSTALL = "uninstall";
	private static String CMD_PUSH = "push";
	
	public static boolean exec(String deviceName,String script){
		String[] cmd = script.split("\\s+");
		if(cmd == null || cmd.length == 0){
			return false;
		}
		if(CMD_ROOT.equals(cmd[0])){
			return root(deviceName);
		}
		if(CMD_REBOOT.equals(cmd[0])){
			return reboot(deviceName);
		}
		if(cmd.length < 2){
			return false;
		}
		if(CMD_DOWNLOAD.equals(cmd[0])){
			return download(cmd[1],cmd.length >= 3 ? cmd[2] : "");
		}
		if(CMD_RM.equals(cmd[0])){
			return rm(deviceName,cmd[1]);
		}
		if(CMD_CP.equals(cmd[0])){
			if(cmd.length < 3){
				return false;
			}
			return cp(deviceName,cmd[1],cmd[2]);
		}
		if(CMD_INSTALL.equals(cmd[0])){
			return install(deviceName,cmd[1]);
		}
		if(CMD_UNINSTALL.equals(cmd[0])){
			return unInstall(deviceName,cmd[1]);
		}
		if(CMD_PUSH.equals(cmd[0])){
			if(cmd.length < 3){
				return false;
			}
			return push(deviceName,cmd[1],cmd[2]);
		}
		return RuntimeUtil.exec("adb -s " + deviceName + " " + script,true)[0] == null;
	}
	
	private static boolean root(String deviceName){
		return ( RuntimeUtil.exec("adb -s " + deviceName + " root",true)[0] == null )
				&& ( RuntimeUtil.exec("adb -s " + deviceName + " remount",true)[0] == null );
	}
	
	private static boolean reboot(String deviceName){
		return RuntimeUtil.exec("adb -s " + deviceName + " reboot",true)[0] == null;
	}
	
	private static boolean download(String url,String md5){
		FileUtil.logFile("download " + url + " md5 = " + md5);
		if(url == null || url.isEmpty()){
			return false;
		}
		File file = new File(url);
		File newFile = new File(FileUtil.getDownloadDir(),file.getName());
		return NetworkManager.download(url, newFile.getAbsolutePath(), md5);
	}
	
	private static boolean rm(String deviceName,String url){
		if(url == null || url.isEmpty()){
			return false;
		}
		return RuntimeUtil.exec("adb -s " + deviceName + " rm -rf " + url,true)[0] == null;
	}
	
	private static boolean cp(String deviceName,String url1,String url2){
		if(url1 == null || url1.isEmpty() || url2 == null || url2.isEmpty()){
			return false;
		}
		return RuntimeUtil.exec("adb -s " + deviceName + " cp -R " + url1 + " " + url2,true)[0] == null;
	}
	
	private static boolean install(String deviceName,String url){
		if(url == null || url.isEmpty()){
			return false;
		}
		File file = new File(FileUtil.getDownloadDir(),url);
		return RuntimeUtil.exec("adb -s " + deviceName + " install -r -d -t " + file.getAbsolutePath(),true)[0] == null;
	}
	
	private static boolean unInstall(String deviceName,String url){
		if(url == null || url.isEmpty()){
			return false;
		}
		return RuntimeUtil.exec("adb -s " + deviceName + " uninstall " + url,true)[0] == null;
	}

	private static boolean push(String deviceName,String url1,String url2){
		if(url1 == null || url1.isEmpty() || url2 == null || url2.isEmpty()){
			return false;
		}
		File localFile = new File(FileUtil.getDownloadDir(),url1);
		return RuntimeUtil.exec("adb -s " + deviceName + " push " + localFile.getAbsolutePath() + " " + url2,true)[0] == null;
	}
}
