package com.svn.app;

import com.svn.app.core.config.FileStorageProperties;
import com.svn.app.core.config.tenant.TenantAwareHikariDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class AppApplication {
    @Value( "${spring.datasource.url}" )
    private String dataSourceUrl;
    @Value( "${spring.datasource.username}" )
    private String dataSourceUsername;
    @Value( "${spring.datasource.password}" )
    private String dataSourcePassword;

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new TenantAwareHikariDataSource();

        dataSource.setInitializationFailTimeout(0);
        dataSource.setMaximumPoolSize(5);
        dataSource.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        dataSource.addDataSourceProperty("url", dataSourceUrl);
        dataSource.addDataSourceProperty("user", dataSourceUsername);
        dataSource.addDataSourceProperty("password", dataSourcePassword);

        return dataSource;
    }
}
