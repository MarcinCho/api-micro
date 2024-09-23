package com.marcincho.librarysearch.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.marcincho.librarysearch.DTOs.CustomQueryDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class QueryCreatorTest {
    @Test
    void testAnyField() {
        String query = "trolololo";
        String expected = "search.json?q=" + query + "&limit=5";
        String actual = QueryCreator.anyField(query);
        assertEquals(expected, actual);

    }

    @Test
    void testAuthor() {
        String query = "tadeusz";
        String expected = "search/authors.json?q=tadeusz&limit=5&fields=name,work_count,birth_date,death_date,key";
        String actual = QueryCreator.author(query);
        assertEquals(expected, actual);
    }


    @Test
    void queryPlus() {
        String query = "testing replacing of spaces with plus";
        String expected = "testing+replacing+of+spaces+with+plus";
        String actual = QueryCreator.queryPlus(query);
        assertEquals(expected, actual);
    }

    @Test
    void title() {
        String query = "Marcin Marcinowski testing";
        String expected = "search.json?title=" + QueryCreator.queryPlus(query) + "&limit=5";
        String actual = QueryCreator.title(query);
        assertEquals(expected, actual);
    }

    @Test
    void custom() {
        CustomQueryDTO query = new CustomQueryDTO("Blackout", "Adam Mickiewicz", "Ending", "Someone");
        String expected = "search.json?title=Blackout&author=Adam+Mickiewicz&subject=Ending&person=Someone&limit=5";
        assertEquals(expected, QueryCreator.custom(query));
    }

    @Test
    void subject() {
        String query = "web testing";
        String expected = "search.json?title=" + QueryCreator.queryPlus(query) + "&limit=5";
        String actual = QueryCreator.title(query);
        assertEquals(expected, actual);
    }
}
