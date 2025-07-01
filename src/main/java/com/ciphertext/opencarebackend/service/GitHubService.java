package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.properties.GitHubProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
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

//    @Cacheable("githubContributors")
//    public String fetchContributors() {
//        ObjectMapper mapper = new ObjectMapper();
//        ArrayNode allContributors = mapper.createArrayNode();
//        List<String> repositoryUrls = gitHubProperties.getRepositories().stream()
//                .map(repo -> "https://api.github.com/repos/" + repo + "/contributors")
//                .toList();
//
//        for (String url : repositoryUrls) {
//            String response = fetchContributorsFromUrl(url);
//            try {
//                JsonNode array = mapper.readTree(response);
//                if (array.isArray()) {
//                    array.forEach(allContributors::add);
//                }
//            } catch (Exception e) {
//                // Optionally log or handle error
//            }
//        }
//
//        ObjectNode result = mapper.createObjectNode();
//        result.set("contributors", allContributors);
//        return result.toString();
//    }

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

    @Cacheable("githubContributors")
    public String fetchContributors() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode allContributors = mapper.createArrayNode();

        List<String> repositoryUrls = gitHubProperties.getRepositories().stream()
                .map(repo -> "https://api.github.com/repos/" + repo + "/contributors")
                .toList();

        for (String url : repositoryUrls) {
            String response = fetchContributorsFromUrl(url);
            try {
                JsonNode array = mapper.readTree(response);
                if (array.isArray()) {
                    array.forEach(allContributors::add);
                }
            } catch (Exception e) {
                // Optionally log or handle error
            }
        }

        ObjectNode result = mapper.createObjectNode();
        result.set("contributors", allContributors);
        return result.toString();
    }
}
