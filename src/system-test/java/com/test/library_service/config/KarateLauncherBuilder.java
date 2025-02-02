package com.test.library_service.config;

import com.intuit.karate.junit5.Karate;

public class KarateLauncherBuilder {

    private String pathInClassPath;
    private KarateEnvironment environment;
    private String scenarioNameRegex;

    Karate build(Karate karate) {
        var launcher = configureDefaultLauncher(karate);
        if (pathInClassPath == null) pathInClassPath = "karate";
        launcher.path("classpath:" + pathInClassPath);
        if (environment != null) launcher.karateEnv(environment.toString());
        if (scenarioNameRegex != null) launcher.scenarioName(scenarioNameRegex);
        return launcher;
    }

    public Karate build() {
        return build(new Karate());
    }

    Karate configureDefaultLauncher(Karate karateLauncher) {
        return karateLauncher
                .outputCucumberJson(true)
                .outputJunitXml(true)
                .outputHtmlReport(true)
                .reportDir("build/reports/karate");
    }
}
