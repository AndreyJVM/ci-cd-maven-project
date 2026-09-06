package ru.vorobevaqa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepoDto {

    private String name;

    private String description;

    @JsonProperty("html_url")
    private String htmlUrl;

    private String language;

    @JsonProperty("stargazers_count")
    private int stars;

    @JsonProperty("forks_count")
    private int forks;

    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;

    private boolean fork;
}