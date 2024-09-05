package com.marcincho.librarysearch.apiClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    private static final String BASE_URL = "https://openlibrary.org/search";

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    public static String requestAuthor(String authorName) {
        logger.info("Getting " + authorName + " info from the OpenLibrary.");
        String url = BASE_URL + "/authors.json?q=" + authorName.replace(' ', '+');
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 ? response.body() : "";
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    public static String requestTitle(String title) {
        logger.info("Getting book by the title: " + title);
        String url = BASE_URL + ".json?title=" + title.replace(' ', '+');
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 ? response.body() : "";
        } catch (IOException | InterruptedException e) {
            return "";
        }
    }

    public static String requestQuery(String query) {
        logger.info("Getting results for custom query: " + query);
        String url = BASE_URL + ".json?" + query;
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 ? response.body() : "";
        } catch (IOException | InterruptedException e) {
            return " ";
        }
    }

    public static String requestSingleBook(String seed) {
        return " ";
    }

}
