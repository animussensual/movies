package com.ksubaka.movielist.service.themoviedb;

import com.ksubaka.movielist.client.ApiClient;
import com.ksubaka.movielist.client.GET;
import com.ksubaka.movielist.service.AbstractMovieService;
import com.ksubaka.movielist.service.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;


public class TheMovieDbService extends AbstractMovieService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String apiUrl;
    private final String token;

    public TheMovieDbService(ApiClient apiClient, Properties config) {
        super(apiClient, config);
        apiUrl = config.getProperty("themoviedb.api.url");
        token = config.getProperty("themoviedb.api.token");
    }

    @Override
    protected Optional<Movie> queryDetails(Movie movie) {
        try {
            GET<Object> get = GET.of(formatDetailsUrl(movie));
            Optional<byte[]> body = apiClient.sendRequest(get).getBody();

            if (body.isPresent()) {
                Crew crew = om.readValue(body.get(), Crew.class);
                String director = findDirector(crew.getCrew());
                TheMovieDbMovie withDirector = TheMovieDbMovie.from(movie)
                        .withDirector(director)
                        .build();
                return Optional.of(withDirector);
            }
        } catch (IOException e) {
            log.error("Unable to get details for {}", movie.getTitle());
        }
        return Optional.ofNullable(movie);
    }

    private String findDirector(List<Job> jobs) {
        for (Job job : jobs) {
            if (job.getJob().equals("Director")) {
                return job.getName();
            }
        }
        return null;
    }

    @Override
    protected void validateConfig(Properties config) {
        validateUrlProperty("themoviedb.api.url");
    }

    @Override
    protected List<Movie> extractMovieList(byte[] bytes) throws IOException {
        Results results = om.readValue(bytes, Results.class);
        return Collections.unmodifiableList(results.getResults());
    }

    @Override
    protected String formatUrl(String title) {
        return String.format("%s/search/movie?api_key=%s&query=%s", apiUrl, token, title);
    }

    private String formatDetailsUrl(Movie movie) {
        return String.format("%s/movie/%s/credits?api_key=%s", apiUrl, movie.getId(), token);
    }
}
