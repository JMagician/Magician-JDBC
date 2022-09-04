package com.magician.jdbc.helper.manager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * connection management
 */
public class ConnectionManager {

    private Connection connection;

    private boolean isTransaction;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isTransaction() {
        return isTransaction;
    }

    public void setTransaction(boolean transaction) {
        isTransaction = transaction;
    }

    public void close() throws SQLException {
        // If the connection does not have a transaction close it, otherwise it is handed over to the transaction manager to close it
        if (!isTransaction && connection != null) {
            connection.close();
        }
    }
}
