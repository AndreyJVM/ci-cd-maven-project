package ru.vorobevaqa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QrRequest {

    @NotBlank(message = "URL is required")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "URL must start with http:// or https://"
    )
    private String url;
}