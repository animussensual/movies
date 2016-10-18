package com.ksubaka.movielist.service.themoviedb;


import com.ksubaka.movielist.service.MockOkClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MockTheMovieDbOkClient extends MockOkClient {

    private static final Pattern DETAILS_ID_PATTERN = Pattern.compile(".*/movie/(.*)/.*");

    @Override
    protected String getDir() {
        return "themoviedb";
    }

    protected String extractTitle(String url) {
        if (url.contains("/movie/")) {
            Matcher matcher = DETAILS_ID_PATTERN.matcher(url);
            if (matcher.find()) {
                return matcher.group(1);
            }
            return null;
        } else {
            return url.substring(url.lastIndexOf("=") + 1);

        }
    }
}
