package com.CloudKeeper.CloudBalanceBackend.snowflake;

import com.snowflake.snowpark.Session;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConnectingSnowflake implements CommandLineRunner {

    private final Session session;

    public ConnectingSnowflake(Session session) {
        this.session = session;
    }

    @Override
    public void run(String... args) {
        session.sql("""
                    SELECT 
                      CURRENT_ACCOUNT_NAME(),
                      CURRENT_USER(),
                      CURRENT_ROLE(),
                      CURRENT_DATABASE(),
                      CURRENT_SCHEMA()
                """).show();
    }
}
