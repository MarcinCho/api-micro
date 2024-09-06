package com.marcincho.librarysearch.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class QueryCreatorTest {
    @Test
    void testAnyField() {
        String query = "trolololo";
        String expected = ".json?q=" + query + "&limit=5";
        String actual = QueryCreator.anyField(query);

        assertEquals(expected, actual);

    }

    @Test
    void testAuthor() {
        String query = "tadeusz";
        String expected = "/authors.json?q=tadeusz&limit=5&fields=name,work_count,birth_date,death_date,key";
        String actual = QueryCreator.author(query);

        assertEquals(expected, actual);
    }

    @Test
    void testCustom() {
        String query = "pan_tadeusz";
        String expeccted = "search.json?q=pan+tadeusz&limit=5&fields=title,author_key,publish_year,subtitle,seed,language";

    }

    @Test
    void testSubject() {

    }

    @Test
    void testTitle() {

    }
}
