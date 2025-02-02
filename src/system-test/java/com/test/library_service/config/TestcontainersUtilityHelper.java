package com.test.library_service.config;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

import java.io.File;
import java.time.Duration;

@Slf4j
public class TestcontainersUtilityHelper {

    private static final String ENV_TESTCONTAINERS_STARTUP_TIMEOUT_IN_SECONDS_DEFAULT = "240";
    private static final String DEFAULT_DOCKER_COMPOSE_FILENAME = "docker-compose.yml";
    private static final String SPRING_BOOT_APP = "library-service";

    public static DockerComposeContainer buildDockerComposeContainer() {
        var environment = new DockerComposeContainer<>(new File(DEFAULT_DOCKER_COMPOSE_FILENAME));
        environment.withOptions("--compatibility");
        environment.withLocalCompose(true);
        WaitStrategy waitStrategyHealthy = getWaitStrategy();
        environment.waitingFor(SPRING_BOOT_APP, waitStrategyHealthy);
        environment.withBuild(true);
        return environment;
    }

    private static Long getStartupTimeoutInS() {
        var result =
                Long.parseLong(ENV_TESTCONTAINERS_STARTUP_TIMEOUT_IN_SECONDS_DEFAULT);
        log.warn("Testcontainers will use a startup timeout of {}s.", result);
        return result;
    }

    private static WaitStrategy getWaitStrategy() {
        return Wait.forHealthcheck().withStartupTimeout(Duration.ofSeconds(getStartupTimeoutInS()));
    }
}
