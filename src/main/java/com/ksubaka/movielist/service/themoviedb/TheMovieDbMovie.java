package com.ksubaka.movielist.service.themoviedb;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ksubaka.movielist.service.Movie;

import java.io.IOException;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TheMovieDbMovie implements Movie {

    private String id;

    private String title;

    @JsonProperty("release_date")
    @JsonDeserialize(using = YearDeserializer.class)
    private Year releaseDate;

    private String director;

    private TheMovieDbMovie() {
    }

    public static Builder from(Movie movie) {
        return new Builder()
                .withId(movie.getId())
                .withTitle(movie.getTitle())
                .withDirector(movie.getDirector())
                .withReleaseDate(movie.getYear());
    }

    @Override
    public String getId() {
        return id;
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
        return releaseDate;
    }

    public static class Builder {
        private final TheMovieDbMovie movie;

        private Builder() {
            movie = new TheMovieDbMovie();
        }

        public Builder withId(String id) {
            movie.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            movie.title = title;
            return this;
        }

        public Builder withDirector(String director) {
            movie.director = director;
            return this;
        }

        public Builder withReleaseDate(Year releaseDate) {
            movie.releaseDate = releaseDate;
            return this;
        }

        public TheMovieDbMovie build() {
            Objects.requireNonNull(movie.id, "Id is null");
            Objects.requireNonNull(movie.title, "Title is null");
            return movie;
        }
    }

    public static class YearDeserializer extends JsonDeserializer<Year> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        public YearDeserializer(){}

        @Override
        public Year deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = jsonParser.readValueAs(String.class);
            return Year.parse(value, formatter);
        }
    }
}
