package com.ksubaka.movielist.client.okhttp3;


import com.ksubaka.movielist.client.ApiClient;
import com.ksubaka.movielist.client.ApiClientConfig;
import com.ksubaka.movielist.client.ApiResponse;
import com.ksubaka.movielist.client.FailureResponse;
import com.ksubaka.movielist.client.GET;
import com.ksubaka.movielist.client.SuccessResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkApiClient implements ApiClient {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final OkHttpClient client;

    public OkApiClient(ApiClientConfig config) {
        client = new OkHttpClient().newBuilder()
                .connectTimeout(config.getConnectionTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(config.getSocketTimeout(), TimeUnit.MILLISECONDS)
                .build();
    }

    public OkApiClient() {
        this(ApiClientConfig.getDefault());
    }

    @Override
    public ApiResponse sendRequest(GET url) {
        log.info("Request: {}", url.getUrl());

        Request request = new Request.Builder()
                .url(url.getUrl())
                .build();
        try {
            Response response = client.newCall(request).execute();
            log.info("Got response {}", response.code());

            if (response.isSuccessful()) {
                return SuccessResponse.<String>builder()
                        .withBody(response.body().bytes())
                        .build();
            } else {
                return FailureResponse.builder()
                        .withErrorCode(response.code())
                        .withErrorMessage(response.message())
                        .build();
            }
        } catch (IOException e) {
            log.error("Get request failed", e);
            return ApiResponse.error(500, "Get request failed");
        }
    }
}
