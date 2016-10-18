package com.ksubaka.movielist.service.themoviedb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class Results {

    private List<TheMovieDbMovie> results;

    public List<TheMovieDbMovie> getResults() {
        return results;
    }
}
