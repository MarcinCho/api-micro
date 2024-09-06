package com.marcincho.librarysearch.apiClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiClientTest {

    // get the author by name
    // get book
    // get multi query

    @DisplayName("Get String with Author")
    @Test
    @Order(2)
    void testRequestAuthor() {
        String authorName = "/authors.json?q=" + "test";
        String response = ApiClient.requestQuery(authorName);
        assertEquals(true, response.contains("numFound"));
    }

    @DisplayName("Get String with title")
    @Test
    @Order(2)
    void testRequestTitle() {
        String title = ".json?title=" + "Testing";
        String response = ApiClient.requestQuery(title);
        assertEquals(true, response.contains("isbn"));
    }

    @DisplayName("Get String with Custom Query")
    @Test
    void testRequestQuery() {
        String query = ".json?" + "title=pan+tadeusz&author=adam+mickiewicz";
        String response = ApiClient.requestQuery(query);
        assertEquals(true, response.contains("OL14814249M"));
    }

}
