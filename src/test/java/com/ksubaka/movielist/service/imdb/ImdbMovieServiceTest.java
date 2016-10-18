package com.ksubaka.movielist.service.imdb;

import com.ksubaka.movielist.service.AbstractMovieServiceTest;
import com.ksubaka.movielist.service.MovieService;

public class ImdbMovieServiceTest extends AbstractMovieServiceTest {

    @Override
    protected MovieService getMovieService() {
        return new ImdbMovieService(new MockImdbOkClient(), config);
    }

}
