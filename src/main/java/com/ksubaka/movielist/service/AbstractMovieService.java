package com.ksubaka.movielist.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksubaka.movielist.client.ApiClient;
import com.ksubaka.movielist.client.ApiResponse;
import com.ksubaka.movielist.client.GET;
import com.ksubaka.movielist.service.exception.MoviesUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.*;

public abstract class AbstractMovieService implements MovieService {

    protected static final ObjectMapper om = new ObjectMapper();
    protected final ApiClient apiClient;
    private final Properties config;

    protected AbstractMovieService(ApiClient apiClient, Properties config) {
        this.apiClient = apiClient;
        this.config = config;
        Objects.requireNonNull(apiClient, "ApiClient is null");
        Objects.requireNonNull(config, "Config is null");

        validateConfig(config);
    }

    protected abstract void validateConfig(Properties config);

    protected abstract Optional<Movie> queryDetails(Movie movie);

    protected abstract List<Movie> extractMovieList(byte[] bytes) throws IOException;

    protected abstract String formatUrl(String url);

    @Override
    public List<Movie> find(String title) {
        try {
            String url = formatUrl(title);

            ApiResponse apiResponse = apiClient.sendRequest(GET.of(url));
            Optional<byte[]> body = apiResponse.getBody();

            List<Movie> movies = Collections.emptyList();
            if (body.isPresent()) {
                movies = extractMovieList(body.get());
            }

            List<Movie> moviesWithDetails = new ArrayList<>();
            for (Movie movie : movies) {
                Optional<Movie> details = queryDetails(movie);
                if (details.isPresent()) {
                    moviesWithDetails.add(details.get());
                }
            }
            return moviesWithDetails;

        } catch (IOException e) {
            throw new MoviesUnavailableException(e);
        }
    }

    protected void validateUrlProperty(String key) {
        String apiUrl = config.getProperty(key);
        try {
            URI.create(apiUrl);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Invalid value %s for key %s", apiUrl, key));
        }
    }

}
