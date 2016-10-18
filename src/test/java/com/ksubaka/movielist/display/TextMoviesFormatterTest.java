package com.ksubaka.movielist.display;

import com.ksubaka.movielist.service.Movie;
import org.junit.Test;

import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TextMoviesFormatterTest {

    private final MoviesFormatter moviesFormatter = new StringMoviesFormatter();

    @Test
    public void canDisplayEmptyMovieList() {
        String formattedMovies = moviesFormatter.formatMovies(Collections.emptyList());

        assertThat(formattedMovies, is(""));
    }

    @Test
    public void canDisplayMovies() {
        Movie movie1 = buildMovie("Movie Title1", "Movie Director1", "2001");
        Movie movie2 = buildMovie("Movie Title2", "Movie Director2", "2002");
        List<Movie> movies = Arrays.asList(movie1, movie2);

        String formattedMovies = moviesFormatter.formatMovies(movies);

        String[] lines = formattedMovies.split("\n");
        assertThat(lines[0], is("Movie Title1, Movie Director1, 2001"));
        assertThat(lines[1], is("Movie Title2, Movie Director2, 2002"));
    }

    private static Movie buildMovie(String title, String director, String year) {
        return new Movie() {

            @Override
            public String getId() {
                return null;
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
                return Year.parse(year);
            }
        };

    }
}
