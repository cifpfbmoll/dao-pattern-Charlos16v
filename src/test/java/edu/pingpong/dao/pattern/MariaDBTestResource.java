package edu.pingpong.dao.pattern;

import edu.pingpong.dao.pattern.MariaDBTestResource.Initializer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.MariaDBContainer;

import java.util.HashMap;
import java.util.Map;

@QuarkusTestResource(Initializer.class)
public class MariaDBTestResource {

    public static class Initializer implements QuarkusTestResourceLifecycleManager {
        private MariaDBContainer mariaDBContainer;

        @Override
        public Map<String, String> start() {
            this.mariaDBContainer = new MariaDBContainer("mariadb:latest");
            this.mariaDBContainer.start();
            return getConfigurationParameters();
        }

        private Map<String, String> getConfigurationParameters() {

            final Map<String, String> configuration = new HashMap<>();
            configuration.put("quarkus.datasource.jdbc.url", this.mariaDBContainer.getJdbcUrl());
            configuration.put("quarkus.datasource.username", this.mariaDBContainer.getUsername());
            configuration.put("quarkus.datasource.password", this.mariaDBContainer.getPassword());
            return configuration;
        }

        @Override
        public void stop() {
            // Stops the container
            if (this.mariaDBContainer != null) {
                this.mariaDBContainer.close();
            }
        }
    }
}
