package com.marcinchowaniec.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDto(
        @NotBlank Long id,
        @NotBlank String login,
        @NotBlank String name) {

}
