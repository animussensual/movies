package com.ksubaka.movielist;

import java.util.Arrays;
import java.util.Optional;

public enum API {
    IMDB,
    THEMOVIEDB;

    public static Optional<API> parse(String api) {
        return Arrays.stream(API.values())
                .filter(a -> a.name().toLowerCase().equals(api))
                .findFirst();
    }
}
