package com.marcincho.librarysearch.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcincho.librarysearch.DTOs.CustomQueryDTO;

public class QueryCreator {

    private static final Logger logger = LoggerFactory.getLogger(QueryCreator.class);

    private static final String LIMIT = "&limit=5";

    public static String title(String query) {
        logger.info("Converting " + query + " to title query.");
        return LIMIT;
    }

    public static String author(String query) {
        logger.info("Converting " + query + " to author query.");
        String fields = "&" + "fields=name,work_count,birth_date,death_date,key";
        return "/authors.json?q=" + query + LIMIT + fields;
    }

    public static String custom(CustomQueryDTO query) {
        logger.info("Converting " + query + " to custom multi field query.");
        return " ";
    }

    public static String subject(String query) {
        logger.info("Converting " + query + " to subject query.");
        return " ";
    }

    public static String anyField(String query) {
        logger.info("Converting " + query + " to any field query.");
        return ".json?q=" + query + LIMIT;
    }

}
