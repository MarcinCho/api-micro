package com.marcincho.librarysearch.DTOs;

import java.util.Date;

public record ResponseDTO(int statusCode, String message, Date date) {
}
