package com.magician.jdbc.core.util;

/**
 * thread tool class
 * @author yuye
 *
 */
public class ThreadUtil {

	private static ThreadLocal threadLocal = new ThreadLocal();
	
	public static ThreadLocal getThreadLocal(){
		return threadLocal;
	}
}
