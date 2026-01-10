package com.CloudKeeper.CloudBalanceBackend.modal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource.snowflake")
public class SnowflakePropsDTO {

    private String url;
//    private String user;
    private String account;
    private String password;
    private String role;
    private String warehouse;
    private String database;
    private String schema;
}
