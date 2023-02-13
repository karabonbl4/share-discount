package com.senla.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.annotation.PreDestroy;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConnectionHolder {

    private Properties properties;
    private Map<Thread, Connection> openedWithTransaction;
    private ComboPooledDataSource comboPooledDataSource;
    private Connection connection;

    public ConnectionHolder() {
        properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            ;
        }
        this.openedWithTransaction = new HashMap<>();
        this.comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setJdbcUrl(properties.getProperty("db.postgres.url"));
        comboPooledDataSource.setUser(properties.getProperty("db.postgres.user"));
        comboPooledDataSource.setPassword(properties.getProperty("db.postgres.password"));
    }

    public Connection getConnection() {
        if (!openedWithTransaction.containsKey(Thread.currentThread())) {
            try {
                connection = comboPooledDataSource.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
        connection = openedWithTransaction.get(Thread.currentThread());
        try {
            connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}
        return connection;
    }

    public void putToOpenedWithTransaction(Connection connection) {
        openedWithTransaction.put(Thread.currentThread(), connection);
    }

    @PreDestroy
    public void closeConnections() {
        comboPooledDataSource.close();
    }

    public ComboPooledDataSource getComboPooledDataSource() {
        return comboPooledDataSource;
    }

    public Properties getProperties() {
        return properties;
    }
}
