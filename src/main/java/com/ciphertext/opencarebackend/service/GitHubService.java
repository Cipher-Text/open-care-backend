package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.properties.GitHubProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GitHubService {
    private final GitHubProperties gitHubProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    public GitHubService(GitHubProperties gitHubProperties) {
        this.gitHubProperties = gitHubProperties;
    }

    @Cacheable("githubContributors")
    public String fetchContributors() {
        List<String> repositoryUrls = gitHubProperties.getRepositories().stream()
                .map(repo -> "https://api.github.com/repos/" + repo + "/contributors")
                .toList();

        StringBuilder contributors = new StringBuilder();
        for (String url : repositoryUrls) {
            contributors.append(fetchContributorsFromUrl(url)).append("\n");
        }
        return contributors.toString();
    }

    private String fetchContributorsFromUrl(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(gitHubProperties.getToken());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return "Failed to fetch contributors from " + url + ": " + response.getStatusCode();
        }
    }
}
