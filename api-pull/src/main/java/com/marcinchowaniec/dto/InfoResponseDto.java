package com.marcinchowaniec.dto;

import java.util.Date;

public record InfoResponseDto(
                int status_code,
                String info,
                Date time) {

}
