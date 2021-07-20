package com.otus.config;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {
    private static final Logger log = LoggerFactory.getLogger(FlywayConfig.class);

    public FlywayConfig(@Qualifier("dBDataSource") DataSource dBDataSource){
        migrateFlyway(dBDataSource);

    }

    public void migrateFlyway(DataSource dBDataSource) {
        log.info("DB schema migration started");
        Flyway flyway = Flyway.configure()
                .dataSource(dBDataSource)
                .load();
        flyway.migrate();
        log.info("DB schema migration finished");

    }
}
