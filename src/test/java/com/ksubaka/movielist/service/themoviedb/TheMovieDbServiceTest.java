package com.ksubaka.movielist.service.themoviedb;

import com.ksubaka.movielist.service.AbstractMovieServiceTest;
import com.ksubaka.movielist.service.MovieService;

public class TheMovieDbServiceTest extends AbstractMovieServiceTest{

    @Override
    protected MovieService getMovieService() {
        return new TheMovieDbService(new MockTheMovieDbOkClient(), config);
    }

}
