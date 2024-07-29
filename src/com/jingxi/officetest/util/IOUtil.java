package com.jingxi.officetest.util;

import java.io.Closeable;

public class IOUtil {

	public static void close(Closeable... closeAbles){
		if(closeAbles == null || closeAbles.length == 0){
			return;
		}
		for(Closeable able : closeAbles){
			try{
				able.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
