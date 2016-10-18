package com.ksubaka.movielist.client;

public class GET<T> implements ApiRequest {

    private final String url;

    private GET(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public static <T> GET<T> of(String url) {
        return new GET<>(url);
    }

    @Override
    public String toString() {
        return "GET{" +
                "url='" + url + '\'' +
                '}';
    }
}
