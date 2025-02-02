package com.test.library_service.config;

import lombok.Getter;

@Getter
enum KarateEnvironment {
    LOCAL("local"),
    DEV("dev"),
    STAGING("staging"),
    PROD("prod");

    private final String env;

    KarateEnvironment(String env) {
        this.env = env;
    }

    @Override
    public String toString() {
        return getEnv();
    }
}
