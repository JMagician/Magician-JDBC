package com.magician.jdbc.core.util;

/**
 * 线程工具类
 * @author yuye
 *
 */
public class ThreadUtil {

	private static ThreadLocal threadLocal = new ThreadLocal();
	
	public static ThreadLocal getThreadLocal(){
		return threadLocal;
	}
}
