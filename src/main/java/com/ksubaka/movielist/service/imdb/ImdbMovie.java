package com.ksubaka.movielist.service.imdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ksubaka.movielist.service.Movie;

import java.time.Year;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbMovie implements Movie {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Year")
    private String year;

    private ImdbMovie() {
        super();
    }

    public static Builder from(Movie movie){
        return new Builder()
                .withTitle(movie.getTitle())
                .withDirector(movie.getDirector())
                .withYear(movie.getYear().toString());
    }

    @Override
    public String getId() {
        return title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDirector() {
        return director;
    }

    @Override
    public Year getYear() {
        if (year.matches("\\d{4}.\\d{4}")) {
            return Year.parse(year.substring(0,4));
        } else {
            return Year.parse(year);
        }
    }

    public static class Builder{
        private final ImdbMovie movie;

        private Builder(){
            movie = new ImdbMovie();
        }

        public Builder withTitle(String title){
            movie.title = title;
            return this;
        }

        public Builder withDirector(String director){
            movie.director = director;
            return this;
        }

        public Builder withYear(String year){
            movie.year = year;
            return this;
        }

        public ImdbMovie build(){
            Objects.requireNonNull(movie.title, "Title is null");
            return movie;
        }
    }
}
