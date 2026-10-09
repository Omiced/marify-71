package org.generation.marify.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArtistRequest(
        @NotBlank(message = "No puede estar en blanco el nombre")
        @Size(min = 2, max = 100)
        String name,
        @NotBlank
        String genere,
        String country
) {
}
