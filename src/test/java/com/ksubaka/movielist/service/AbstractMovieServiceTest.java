package com.ksubaka.movielist.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Ignore
public abstract class AbstractMovieServiceTest {

    protected Properties config;

    @Before
    public void setup() throws IOException {
        config = new Properties();
        config.load(getClass().getClassLoader().getResourceAsStream("movielist-test.properties"));
    }

    @Test
    public void canSearchNotExistingMovie() {
        List<Movie> movies = getMovieService().find("");
        assertThat(movies.size(), is(0));
    }

    @Test
    public void canSearchExistingMovie() {
        List<Movie> movies = getMovieService().find("indiana_jones");

        assertThat(movies.size(), is(1));
        assertThat(movies.get(0).getTitle(), is("Indiana_Jones_and_the_Last_Crusade"));
        assertThat(movies.get(0).getDirector(), is("Steven Spielberg"));
        assertThat(movies.get(0).getYear(), is(Year.parse("1989")));
    }

    protected abstract MovieService getMovieService();
}