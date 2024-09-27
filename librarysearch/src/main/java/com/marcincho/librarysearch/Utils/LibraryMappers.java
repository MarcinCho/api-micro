package com.marcincho.librarysearch.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcincho.librarysearch.entity.Author;
import com.marcincho.librarysearch.entity.Book;

import java.util.List;

public class LibraryMappers {

    public static List<Book> mapToBookList(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonNode = mapper.readTree(response);
        String dock = jsonNode.get("docs").toString();
        return mapper.readValue(dock, new TypeReference<>() {
        });
    }

    public static Author mapToAuthor(String response, Boolean key) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonNode = mapper.readTree(response);
        if (!key) {
            String dock = jsonNode.get("docs").toString();
            return mapper.readValue(dock, new TypeReference<List<Author>>() {
            }).getFirst();
        } else {
            return mapper.readValue(jsonNode.toString(), Author.class);
        }


    }
}
