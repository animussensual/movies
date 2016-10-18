package com.ksubaka.movielist.service.imdb;


import com.ksubaka.movielist.service.MockOkClient;

public class MockImdbOkClient extends MockOkClient {

    @Override
    protected String getDir() {
        return "imdb";
    }

    @Override
    protected String extractTitle(String url) {
        return url.substring(url.lastIndexOf("=") + 1);
    }
}
