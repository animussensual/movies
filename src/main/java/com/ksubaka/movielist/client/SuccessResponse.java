package com.ksubaka.movielist.client;

import java.util.Optional;

public class SuccessResponse implements ApiResponse {

    private byte[] body;

    private SuccessResponse() {
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Status getStatus() {
        return Status.SUCCESS;
    }

    @Override
    public Optional<byte[]> getBody() {
        return Optional.ofNullable(body);
    }

    public static class Builder {

        private final SuccessResponse response;

        private Builder() {
            response = new SuccessResponse();
        }

        public Builder withBody(byte[] body) {
            response.body = body;
            return this;
        }

        public SuccessResponse build() {
            return response;
        }
    }
}
