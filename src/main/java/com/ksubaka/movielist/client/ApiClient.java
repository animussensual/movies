package com.ksubaka.movielist.client;

/**
 * All http requests should go through this interface
 * so they don't depend on a specific http library.
 */
public interface ApiClient {

    ApiResponse sendRequest(GET url);
}
