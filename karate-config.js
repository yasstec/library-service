function fn() {
    karate.configure('connectTimeout', 10000);
    karate.configure('readTimeout', 10000)
    karate.configure('logPrettyRequest', true);
    karate.configure('logPrettyResponse', true);
    karate.configure('ssl', false);

    // Env selection
    let env = karate.env; // get java system property 'karate.env'
    if (!env) env = 'local' // default to localhost
    let config = { // base config JSON
        env: env,
        protocol: '',
        host: '',
        port: '',
        clusterUrl: '',
        servicePath: '',
        baseUrl: '',
    };

    if (env === 'local') {
        config.protocol = 'http'
        config.host = 'localhost'
        let port = karate.properties['service.api.port']
        if(!port) {
            port = '8085'
        }
        config.port = port
    }

    if ((env === 'dev') || (env === 'staging') || (env === 'prod')) {
        config.host = 'com.test.library'
        config.protocol = 'https'
        config.port = 443
        config.servicePath = `library-service/`
    }

    let computedPort = `:${config.port}`
    if ((config.protocol === 'https') && (config.port === 443))
        computedPort = ''

    if(!config.clusterUrl) {
        config.clusterUrl = `${config.protocol}://${config.host}${computedPort}/`
    }
    if(!config.baseUrl) {
        config.baseUrl = `${config.clusterUrl}${config.servicePath}`
    }
    if (config.protocol === 'https') {
        karate.configure('ssl', true);
    }

    return config;
}
