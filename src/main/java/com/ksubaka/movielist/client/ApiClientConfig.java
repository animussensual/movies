package com.ksubaka.movielist.client;


import java.util.Properties;

public class ApiClientConfig {

    private final static ApiClientConfig DEFAULT_CONFIG = new ApiClientConfig();

    private final static String CLIENT_CONNECTION_TIMEOUT = "client.connection.timeout";
    private final static String CLIENT_SOCKET_TIMEOUT = "client.socket.timeout";

    private long connectionTimeout = 5000;
    private long socketTimeout = 5000;

    private ApiClientConfig() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ApiClientConfig getDefault() {
        return DEFAULT_CONFIG;
    }

    public static ApiClientConfig create(Properties config) {
        config.putIfAbsent(CLIENT_CONNECTION_TIMEOUT, Long.toString(DEFAULT_CONFIG.getConnectionTimeout()));
        config.putIfAbsent(CLIENT_SOCKET_TIMEOUT, Long.toString(DEFAULT_CONFIG.getSocketTimeout()));

        return builder().withConnectionTimeout(Long.parseLong(config.getProperty(CLIENT_CONNECTION_TIMEOUT)))
                .withSocketTimeout(Long.parseLong(config.getProperty(CLIENT_SOCKET_TIMEOUT)))
                .build();
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public long getSocketTimeout() {
        return socketTimeout;
    }

    public static class Builder {

        private final ApiClientConfig config;

        private Builder() {
            config = new ApiClientConfig();
        }

        public Builder withConnectionTimeout(long timeout) {
            config.connectionTimeout = timeout;
            return this;
        }

        public Builder withSocketTimeout(long socketTimeout) {
            config.socketTimeout = socketTimeout;
            return this;
        }

        public ApiClientConfig build() {
            return config;
        }


    }
}
