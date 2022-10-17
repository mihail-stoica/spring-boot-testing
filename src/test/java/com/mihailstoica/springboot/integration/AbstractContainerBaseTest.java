package com.mihailstoica.springboot.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractContainerBaseTest {

    static final MySQLContainer<?> MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest");
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry propertyRegistry) {

        propertyRegistry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        propertyRegistry.add(" spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        propertyRegistry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }
}
