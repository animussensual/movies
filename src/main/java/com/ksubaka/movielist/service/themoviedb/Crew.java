package com.ksubaka.movielist.service.themoviedb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class Crew {

    @JsonProperty("crew")
    private List<Job> crew;

    public List<Job> getCrew() {
        return crew;
    }
}
