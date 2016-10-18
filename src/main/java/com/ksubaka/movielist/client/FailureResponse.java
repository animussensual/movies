package com.ksubaka.movielist.client;

import java.util.Objects;

public class FailureResponse implements ApiResponse {

    private Integer errorCode;
    private String errorMessage;

    private FailureResponse() {
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Status getStatus() {
        return Status.FAILURE;
    }

    public static class Builder {
        private final FailureResponse response;

        public Builder() {
            response = new FailureResponse();
        }

        public Builder withErrorCode(Integer errorCode) {
            response.errorCode = errorCode;
            return this;
        }

        public Builder withErrorMessage(String errorMessage) {
            response.errorMessage = errorMessage;
            return this;
        }

        public FailureResponse build() {
            Objects.requireNonNull(response.errorCode, "Error code is null");
            Objects.requireNonNull(response.errorMessage, "Error message is null");
            return response;
        }
    }
}
