package com.example.swapSafe.dbManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DatabaseManagerConn {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManagerConn.class);
    private static volatile DatabaseManagerConn instance;
    private Connection connection;
    private final DataSource dataSource;

    @Autowired
    public DatabaseManagerConn(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public static DatabaseManagerConn getInstance(DataSource dataSource) {
        if (instance == null) {
            synchronized (DatabaseManagerConn.class) {
                if (instance == null) {
                    instance = new DatabaseManagerConn(dataSource);
                }
            }
        }
        return instance;
    }

    public void testConnection() {
        try {
            connection = dataSource.getConnection();
            logger.info("Successfully connected to the database");
        } catch (SQLException e) {
            logger.error("Failed to obtain or use database connection", e);
            throw new RuntimeException("Database operation failed", e);
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DBManager] Connection closed.");
            }
        } catch (SQLException e) {
            logger.error("[DBManager] Failed to close connection", e);
            e.printStackTrace();
        }
    }
}
