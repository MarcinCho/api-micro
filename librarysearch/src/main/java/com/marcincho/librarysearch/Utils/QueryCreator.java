package com.marcincho.librarysearch.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcincho.librarysearch.DTOs.CustomQueryDTO;

public class QueryCreator {

    private static final Logger logger = LoggerFactory.getLogger(QueryCreator.class);

    private static final String LIMIT = "&limit=5";

    public static String queryPlus(String query) {
        return query.replace(' ', '+');
    }

    public static String title(String query) {
        logger.info("Converting {} to title query.", queryPlus(query));
        return "search.json?title=" + queryPlus(query) + LIMIT;
    }

    public static String author(String query) {
        logger.info("Converting {} to author query.", queryPlus(query));
        String fields = "&" + "fields=name,work_count,birth_date,death_date,key";
        return "search/authors.json?q=" + queryPlus(query) + LIMIT + fields;
    }

    public static String custom(CustomQueryDTO query) {
        String title = query.title() != null ? "title=" + queryPlus(query.title()) + "&" : "";
        String author = query.author() != null ? "author=" + queryPlus(query.author()) + "&" : "";
        String subject = query.subject() != null ? "subject=" + queryPlus(query.subject()) + "&" : "";
        String person = query.person() != null ? "person=" + queryPlus(query.person()) : "";
        logger.info("Converting {} to custom multi field query.", query);
        return "search.json?" + title + author + subject + person + LIMIT;
    }

    public static String subject(String query) {
        logger.info("Converting {} to subject query.", query);
        return "search.json?subject=" + queryPlus(query) + LIMIT;
    }

    public static String anyField(String query) {
        logger.info("Converting {} to any field query.", query);
        return "search.json?q=" + query + LIMIT;
    }

}
