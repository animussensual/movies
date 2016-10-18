package com.ksubaka.movielist.display;

import com.ksubaka.movielist.service.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class StringMoviesFormatter implements MoviesFormatter {

    @Override
    public String formatMovies(List<Movie> movies) {
        return movies.stream()
                .map(movie -> String.format("%s, %s, %s",
                        movie.getTitle(), movie.getDirector(), movie.getYear()))
                .collect(Collectors.joining("\n"));
    }
}
