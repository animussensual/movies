package com.ksubaka.movielist;

import com.ksubaka.movielist.client.ApiClient;
import com.ksubaka.movielist.client.ApiClientConfig;
import com.ksubaka.movielist.client.okhttp3.OkApiClient;
import com.ksubaka.movielist.display.StringMoviesFormatter;
import com.ksubaka.movielist.service.Movie;
import com.ksubaka.movielist.service.MovieService;
import com.ksubaka.movielist.service.imdb.ImdbMovieService;
import com.ksubaka.movielist.service.themoviedb.TheMovieDbService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Optional<CommandLine> parsedArgs = parseCommandLine(args);
        if (!parsedArgs.isPresent()) {
            printHelp();
            return;
        }

        CommandLine commandLine = parsedArgs.get();

        String title = commandLine.getOptionValue("movie", "");
        if (title.isEmpty()) {
            printHelp();
            return;
        }

        Optional<API> api = API.parse(commandLine.getOptionValue("api", "imdb"));
        if (!api.isPresent()) {
            printHelp();
            return;
        }

        MovieService movieService = getMovieService(api.get());
        List<Movie> movies = movieService.find(title);

        StringMoviesFormatter display = new StringMoviesFormatter();
        String formattedMovies = display.formatMovies(movies);

        System.out.println(formattedMovies);
    }

    private static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar query.jar", getOptions());
    }

    private static MovieService getMovieService(API api) {
        Properties config = getConfig();
        ApiClient apiClient = new OkApiClient(ApiClientConfig.create(config));
        switch (api) {
            case IMDB:
                return new ImdbMovieService(apiClient, config);
            case THEMOVIEDB:
                return new TheMovieDbService(apiClient, config);
            default:
                throw new IllegalArgumentException("Invalid api");
        }
    }

    private static Properties getConfig() {
        Properties config = new Properties();
        try {
            config.load(Main.class.getClassLoader().getResourceAsStream("movielist.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return config;
    }

    private static Optional<CommandLine> parseCommandLine(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(getOptions(), args);
        } catch (ParseException e) {
            log.error("Unable to parse command line arguments");
        }

        return Optional.ofNullable(commandLine);
    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption("api", true, "Api name: imdb or themoviedb. Default is imdb");
        options.addOption("movie", true, "Movie title");
        return options;
    }
}
