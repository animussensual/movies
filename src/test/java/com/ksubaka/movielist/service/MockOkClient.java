package com.ksubaka.movielist.service;


import com.ksubaka.movielist.client.ApiClient;
import com.ksubaka.movielist.client.ApiResponse;
import com.ksubaka.movielist.client.GET;
import com.ksubaka.movielist.client.SuccessResponse;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class MockOkClient implements ApiClient {

    public ApiResponse sendRequest(GET get) {
        try {
            String url = get.getUrl();
            String title = extractTitle(url);
            if (url.isEmpty() || title.isEmpty()) {
                return SuccessResponse.builder().build();
            }
            Path path = Paths.get("src/test/resources/movies/" + getDir() + "/" + title + ".json");
            byte[] bytes = Files.readAllBytes(path);
            return SuccessResponse.builder()
                    .withBody(bytes)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to send request: " + get, e);
        }
    }

    protected abstract String getDir();

    protected abstract String extractTitle(String url);
}
