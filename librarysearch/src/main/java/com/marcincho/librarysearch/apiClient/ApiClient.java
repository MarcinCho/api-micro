package com.marcincho.librarysearch.apiClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    private static final String BASE_URL = "https://openlibrary.org/search";

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    public static void keyGrabber(String path) throws IOException {
        // This function is here just for a sake of exercise
        List<String> lines = Files.readAllLines(Path.of(path));
        lines.forEach(System.out::println);

    }

    public static String requestQuery(String query) {
        // Note that Query will be handled in service layer
        logger.info("Getting " + query + " from the OpenLibrary.");
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
                case 200 -> response.body();
                case 404 -> response.body();
                default -> " ";
            };

        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }

    public static String requestSingleBook(String seed) {
        // https://openlibrary.org/works/OL4696590A.json
        return " ";
    }

    public static String requestSingleAuthor(String seed) {
        // https://openlibrary.org/authors/OL4696590A.json
        return " ";
    }

}
