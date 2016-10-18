package com.ksubaka.movielist.service.imdb;

import com.fasterxml.jackson.databind.ObjectMapper;
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

public class ImdbMovieService extends AbstractMovieService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String apiUrl;

    private static final ObjectMapper om = new ObjectMapper();

    public ImdbMovieService(ApiClient apiClient, Properties config) {
        super(apiClient, config);
        apiUrl = config.getProperty("imdb.api.url");
    }

    @Override
    protected void validateConfig(Properties config) {
        super.validateUrlProperty("imdb.api.url");
    }

    protected List<Movie> extractMovieList(byte[] bytes) throws IOException {
        Results results = om.readValue(bytes, Results.class);
        return Collections.unmodifiableList(results.getResults());
    }

    protected Optional<Movie> queryDetails(Movie movie) {
        try {
            GET<Object> get = GET.of(formatDetailsUrl(movie));

            Optional<byte[]> body = apiClient.sendRequest(get).getBody();

            if (body.isPresent()) {
                ImdbMovie details = extractMovie(body.get());
                ImdbMovie withDirector = ImdbMovie.from(movie)
                        .withDirector(details.getDirector())
                        .build();
                return Optional.of(withDirector);

            }
        } catch (IOException e) {
            log.error("Unable to get details for {}", movie.getTitle());
        }
        return Optional.ofNullable(movie);
    }

    private ImdbMovie extractMovie(byte[] bytes) throws IOException {
        return om.readValue(bytes, ImdbMovie.class);
    }

    protected String formatUrl(String title) {
        return String.format("%s?s=%s", apiUrl, title);
    }

    private String formatDetailsUrl(Movie movie) {
        return String.format("%s?t=%s", apiUrl, movie.getTitle());
    }
}
