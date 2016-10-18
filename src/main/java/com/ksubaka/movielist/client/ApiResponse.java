package com.ksubaka.movielist.client;

import java.util.Optional;

public interface ApiResponse {

    Status getStatus();

    default Optional<Integer> getErrorCode() {
        return Optional.empty();
    }

    default Optional<String> getErrorMessage() {
        return Optional.empty();
    }

    default Optional<byte[]> getBody() {
        return Optional.empty();
    }

    static ApiResponse error(int code, String message) {

        return new ApiResponse() {
            @Override
            public Optional<Integer> getErrorCode() {
                return Optional.of(code);
            }

            @Override
            public Optional<String> getErrorMessage() {
                return Optional.of(message);
            }

            @Override
            public Status getStatus() {
                return Status.FAILURE;
            }
        };
    }
}
