package com.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJdbcRepositories(basePackageClasses = {com.otus.repository.AClassForStartScan.class})
public class PersistenceConfig {

    @Bean(name = "dBDataSource")
    @ConfigurationProperties("app.datasource.postgres-db")
    public DataSource dBDataSource() {
        return DataSourceBuilder.create().build();
    }
}
