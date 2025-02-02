package com.test.library_service;

import com.intuit.karate.junit5.Karate;
import com.test.library_service.config.AbstractTestcontainersForSystemTest;
import com.test.library_service.config.KarateLauncherBuilder;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class KarateSystemTest extends AbstractTestcontainersForSystemTest {
    @Karate.Test
    Karate runTest() {
        return new KarateLauncherBuilder().build();
    }
}
