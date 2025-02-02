package com.test.library_service.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.DockerComposeContainer;

public abstract class AbstractTestcontainersForSystemTest {

    static DockerComposeContainer<?> dockerComposeContainer;

    protected AbstractTestcontainersForSystemTest() {
    }

    @BeforeAll
    static void configureSystemPropertiesForGlobalSystemTest() {
        dockerComposeContainer = TestcontainersUtilityHelper.buildDockerComposeContainer();
        dockerComposeContainer.start();
    }

    @AfterAll
    static void teardown() {
        dockerComposeContainer.stop();
    }
}