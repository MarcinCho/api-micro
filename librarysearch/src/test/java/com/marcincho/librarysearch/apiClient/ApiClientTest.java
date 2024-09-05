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
        String authorName = "test";
        String response = ApiClient.requestAuthor(authorName);
        assertEquals(true, response.contains("numFound"));
    }

    @DisplayName("Get String with title")
    @Test
    @Order(2)
    void testRequestTitle() {
        String title = "Testing";
        String response = ApiClient.requestTitle(title);
        assertEquals(true, response.contains("isbn"));
    }

    @DisplayName("Get String with Custom Query")
    @Test
    void testRequestQuery() {
        String query = "title=pan+tadeusz&author=adam+mickiewicz";
        String response = ApiClient.requestQuery(query);
        assertEquals(true, response.contains("OL14814249M"));
    }

}
