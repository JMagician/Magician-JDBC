package com.magician.jdbc.helper.manager;

import com.magician.jdbc.core.constant.enums.TractionLevel;
import com.magician.jdbc.core.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * transaction management
 * @author yuye
 *
 */
public class TransactionManager {

	private static Logger logger = LoggerFactory.getLogger(TransactionManager.class);

	/**
	 * Get a database connection and set it to not commit automatically
	 * Put the acquired connection in the cache
	 */
	public static void beginTraction() throws Exception {
		beginTraction(TractionLevel.READ_COMMITTED);
	}

	/**
	 * Get a database connection and set it to not commit automatically
	 * Put the acquired connection in the cache
	 */
	public static void beginTraction(TractionLevel tractionLevel) throws Exception {
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
			logger.error("Error begin transaction", e);
			throw e;
		}
	}

	/**
	 * Get the current thread's database connection from the cache and commit the transaction
	 */
	public static void commit() throws Exception {
		Map<String, Connection> connections = getConnectionMap();
		if (connections == null) {
			return;
		}

		boolean success = true;

		for (String key : connections.keySet()) {
			try {
				Connection connection = connections.get(key);
				connection.commit();
			} catch (Exception e) {
				logger.error("Error committing transaction", e);
				success = false;
				continue;
			}
		}

		close(connections);

		ThreadUtil.getThreadLocal().remove();

		if(success == false){
			throw new Exception("rollback transaction error");
		}
	}

	/**
	 * Get the current thread's database connection from the cache and roll back the transaction
	 */
	public static void rollback() throws Exception {
		Map<String, Connection> connections = getConnectionMap();
		if (connections == null) {
			return;
		}

		boolean success = true;

		for (String key : connections.keySet()) {
			try {
				Connection connection = connections.get(key);
				connection.rollback();
			} catch (Exception e) {
				logger.error("rollback transaction error", e);
				success = false;
				continue;
			}
		}

		close(connections);

		ThreadUtil.getThreadLocal().remove();

		if(success == false){
			throw new Exception("rollback transaction error");
		}
	}

	/**
	 * Close the connection
	 * @param connections
	 */
	private static void close(Map<String, Connection> connections){
		for (String key : connections.keySet()) {
			try {
				Connection connection = connections.get(key);
				connection.close();
			} catch (Exception e) {
				logger.error("rollback transaction error", e);
				continue;
			}
		}
	}

	/**
	 * Get connection from ThreadLocal
	 * @return
	 */
	private static Map<String, Connection> getConnectionMap(){
		Object transactionObj = ThreadUtil.getThreadLocal().get();
		if(transactionObj == null || (transactionObj instanceof Map) == false){
			return null;
		}
		return (Map<String, Connection>) transactionObj;
	}
}
