package com.svn.app.core.config.tenant;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TenantAwareHikariDataSource extends HikariDataSource {

    @Override
    public Connection getConnection() throws SQLException {

        Connection connection = super.getConnection();
        if(TenantContext.getTenantName()==null){
            super.setUsername("postgres");
        }

        try (Statement sql = connection.createStatement()) {
            sql.execute("SET app.current_user_id = '" + TenantContext.getTenantName() + "'");
        }

        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = super.getConnection(username, password);

        try (Statement sql = connection.createStatement()) {
            sql.execute("SET app.current_user_id = '" + TenantContext.getTenantName() + "'");
        }

        return connection;
    }

}
