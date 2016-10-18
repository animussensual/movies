package com.ksubaka.movielist.service.imdb;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class Results {

    @JsonProperty("Search")
    private List<ImdbMovie> results;

    public List<ImdbMovie> getResults() {
        return results;
    }
}
