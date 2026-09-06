package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.GitHubRepoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class GitHubService {

    private final RestClient restClient;
    private final String githubUsername;

    public GitHubService(@Value("${github.username:vorobev-qa}") String githubUsername) {
        this.githubUsername = githubUsername;
        this.restClient = RestClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader(HttpHeaders.USER_AGENT, "Spring-Boot-Portfolio-App")
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json")
                .build();
    }

    @Cacheable(value = "githubRepos", unless = "#result.isEmpty()")
    public List<GitHubRepoDto> getRecentRepositories() {
        try {
            log.info("Fetching recent repositories from GitHub API for user: {}", githubUsername);
            List<GitHubRepoDto> repos = restClient.get()
                    .uri("/users/{username}/repos?sort=updated&per_page=6&type=owner", githubUsername)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<GitHubRepoDto>>() {});

            if (repos == null) {
                return Collections.emptyList();
            }

            // Фильтруем форки, чтобы показывать только личные проекты
            return repos.stream()
                    .filter(repo -> !repo.isFork())
                    .limit(6)
                    .toList();
        } catch (Exception e) {
            log.error("Failed to fetch repositories from GitHub API: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}