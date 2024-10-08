package com.marcincho.librarysearch.apiClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    private static final String BASE_URL = "https://openlibrary.org/";

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();


    public static String requestQuery(String query) {
        // Note that Query will be handled in service layer
        logger.info("Getting {} from the OpenLibrary.", query);
        String url = BASE_URL + query.replace(' ', '+');
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();
            HttpResponse<String> response = HTTP_CLIENT
                    .send(request, HttpResponse.BodyHandlers
                            .ofString());
            return switch (response.statusCode()) {
                case 200, 404 ->
                    response.body();
                default ->
                    " ";
            };

        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }

    public static String requestByKey(String key) {
        logger.info("Getting info for book with {} from OpenLibrary", key);
        URI url = URI.create(BASE_URL + "works/"  + key + ".json"); // Not pretty but works
        try {
            logger.info("Requesting {}", url.toString());
            HttpRequest request = HttpRequest.newBuilder(url).GET().build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

}
