package com.ksubaka.movielist.service;

import java.util.List;

/**
 * Allows to search movies, exact search depends on the implementation.
 */
public interface MovieService {

    /**
     * Searches movies using their title. Title can be partial match.
     * Returns all movies which title matches.
     *
     * @param title Complete of partial movie title
     * @return Moovies with matching title
     */
    List<Movie> find(String title);
}
