package com.CloudKeeper.CloudBalanceBackend.configuration;

import com.snowflake.snowpark.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SnowflakeDataSourceConfig {

    @Value("${snowflake.url}")
    private String url;

    @Value("${snowflake.user}")
    private String user;

    @Value("${snowflake.password}")
    private String password;

    @Value("${snowflake.role}")
    private String role;

    @Value("${snowflake.warehouse}")
    private String warehouse;

    @Value("${snowflake.database}")
    private String database;

    @Value("${snowflake.schema}")
    private String schema;

    @Bean
    public Session snowflakeSession() {

        Map<String, String> configs = new HashMap<>();
        configs.put("url", url);
        configs.put("user", user);
        configs.put("password", password);
        configs.put("role", role);
        configs.put("warehouse", warehouse);
        configs.put("db", database);
        configs.put("schema", schema);

        return Session.builder()
                .configs(configs)
                .create();
    }
}
