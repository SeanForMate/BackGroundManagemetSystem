package com.znh.util;

import java.io.File;

public class Commons {
	
	public static final Integer pageSize = 10;
	
	
	/**
	 * 获取当前运行时的路径，不包含jar包和jar包名字。
	 * 
	 * @return 当前路径。返回内容带\
	 */
	public static String GetStartPath() {
		String filePath = System.getProperty("user.dir");
		String pathSplit = System.getProperty("path.separator");// windows下是";",linux下是":"
		if (filePath.contains(pathSplit)) {
			filePath = filePath.substring(0, filePath.indexOf(pathSplit));
		}
		if (filePath.endsWith(".jar")) {
			filePath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
		}
		return filePath;
	}
	

}
