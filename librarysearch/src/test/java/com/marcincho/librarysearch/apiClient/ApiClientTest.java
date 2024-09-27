package com.marcincho.librarysearch.apiClient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcincho.librarysearch.DTOs.CustomQueryDTO;
import com.marcincho.librarysearch.Utils.QueryCreator;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiClientTest {

    @DisplayName("Get String with Author")
    @Test
    @Order(2)
    void testRequestAuthor() {
        String authorName = "mickiewicz";
        String response = ApiClient.requestQuery(QueryCreator.author(authorName));
        assertTrue(response.contains("numFound"));
    }

    @DisplayName("Get String with title")
    @Test
    @Order(2)
    void testRequestTitle() {
        String title = "Testing";
        String response = ApiClient.requestQuery(QueryCreator.title(title));
        assertTrue(response.contains("isbn"));
    }

    @DisplayName("Get String with Custom Query")
    @Test
    void testRequestQuery() {
        CustomQueryDTO query = new CustomQueryDTO("Blackout", "Willis", "Fiction");
        String response = ApiClient.requestQuery(QueryCreator.custom(query));
        assertTrue(response.contains("OL32214255M"));
    }

    @DisplayName("Get String with Custom Query")
    @Test
    void testRequestQueryFalse() {
        CustomQueryDTO query = new CustomQueryDTO("Blackout", "Adam Mickiewicz", "Fiction");
        String response = ApiClient.requestQuery(QueryCreator.custom(query));
        assertFalse(response.contains("OL14814249M"));
    }

    @Test
    void testRequestQueryGiberish() {
        String query = "wdvdwadasdcxsaccwda";
        String response = ApiClient.requestQuery(QueryCreator.anyField(query));
        assertTrue(response.contains("numFound"));
    }

    @Test
    void testRequestQueryShortTimeout() {
        String query = "w";
        String response = ApiClient.requestQuery(QueryCreator.anyField(query));
        assertTrue(response.contains("timed out"));
    }

    @Test
    @DisplayName("Test works with key")
    void requestByKeyWorks() {

        String key = "works/OL14914265W";
        String response = ApiClient.requestByKey(key);
        assert response != null;
        assertTrue(response.contains("Blackout"));

    }

    @Test
    @DisplayName("Author with key")
    void requestByKeyAuthor() {
        String key = "authors/OL114808A";
        String response = ApiClient.requestByKey(key);
        assert response != null;
        assertTrue(response.contains("Adam Mickiewicz"));

    }

    // @Test
    // testThrow
}
