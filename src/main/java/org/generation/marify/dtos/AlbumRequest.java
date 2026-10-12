package org.generation.marify.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AlbumRequest(
        String name,
        LocalDate releaseYear) {
}
