package com.ksubaka.movielist.display;

import com.ksubaka.movielist.service.Movie;

import java.util.List;

interface MoviesFormatter {
    String formatMovies(List<Movie> movies);
}
