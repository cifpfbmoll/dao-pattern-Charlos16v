package edu.pingpong.active.record;

import edu.pingpong.active.record.PostgresqlTestResource.Initializer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

@QuarkusTestResource(Initializer.class)
public class PostgresqlTestResource {

    public static class Initializer implements QuarkusTestResourceLifecycleManager {
        private PostgreSQLContainer postgreSQLContainer;

        @Override
        public Map<String, String> start() {
            this.postgreSQLContainer = new PostgreSQLContainer("postgres:latest");
            this.postgreSQLContainer.start();
            return getConfigurationParameters();
        }

        private Map<String, String> getConfigurationParameters() {

            final Map<String, String> configuration = new HashMap<>();
            configuration.put("quarkus.datasource.jdbc.url", this.postgreSQLContainer.getJdbcUrl());
            configuration.put("quarkus.datasource.username", this.postgreSQLContainer.getUsername());
            configuration.put("quarkus.datasource.password", this.postgreSQLContainer.getPassword());
            return configuration;
        }

        @Override
        public void stop() {
            // Stops the container
            if (this.postgreSQLContainer != null) {
                this.postgreSQLContainer.close();
            }
        }
    }
}
