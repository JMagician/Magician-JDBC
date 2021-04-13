package com.magician.jdbc.core.traction;

import com.magician.jdbc.core.constant.enums.TractionLevel;
import com.magician.jdbc.core.util.ThreadUtil;
import com.magician.jdbc.helper.manager.DataSourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * 事务管理aop
 * @author yuye
 *
 */
public class TractionManager {

	private static Logger logger = LoggerFactory.getLogger(TractionManager.class);

	/**
	 * 获取数据库连接，并设置为不自动提交
	 * 将获取到的连接 放到缓存中
	 */
	public static void beginTraction() {
		beginTraction(TractionLevel.READ_COMMITTED);
	}

	/**
	 * 获取数据库连接，并设置为不自动提交
	 * 将获取到的连接 放到缓存中
	 */
	public static void beginTraction(TractionLevel tractionLevel) {
		try {
			Map<String, DataSource> maps = DataSourceManager.getDruidDataSources();

			Map<String, Connection> connections = new HashMap<>();

			for (String key : maps.keySet()) {
				Connection connection = maps.get(key).getConnection();
				connection.setAutoCommit(false);
				connection.setTransactionIsolation(tractionLevel.getLevel());
				connections.put(key, connection);
			}

			ThreadUtil.getThreadLocal().set(connections);
		} catch (Exception e) {
			logger.error("开启事务出错", e);
		}
	}

	/**
	 * 从缓存中获取当前线程的数据库连接，并提交事务
	 */
	public static void commit() {
		try {
			Map<String, Connection> connections = (Map<String, Connection>) ThreadUtil.getThreadLocal().get();

			for (String key : connections.keySet()) {
				Connection connection = connections.get(key);
				connection.commit();
				connection.close();
			}
		} catch (Exception e) {
			logger.error("提交事务出错", e);
		} finally {
			ThreadUtil.getThreadLocal().remove();
		}
	}

	/**
	 * 从缓存中获取当前线程的数据库连接，并回滚事务
	 */
	public static void rollback() {
		try {
			Map<String, Connection> connections = (Map<String, Connection>) ThreadUtil.getThreadLocal().get();

			for (String key : connections.keySet()) {
				Connection connection = connections.get(key);
				connection.rollback();
				connection.close();
			}
		} catch (Exception ex) {
			logger.error("回滚事务出错", ex);
		} finally {
			ThreadUtil.getThreadLocal().remove();
		}
	}
}
