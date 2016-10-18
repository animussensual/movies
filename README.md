## Synopsis

Search movies from public movie databases

## Code Example

Simply run `java -jar query.jar -api <api name> -movie <"movie title">`. 
Supported apis are `imdb` (http://www.omdbapi.com/) and `themoviedb` (https://themoviedb.org)

In order to use the `themoviedb` you have to first aquire their API key. You can get it from https://themoviedb.org.
Then set its value in `src/main/resources/movielist.properties` for `themoviedb.api.token`.

## License

MIT license