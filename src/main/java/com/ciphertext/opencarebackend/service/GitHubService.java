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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitHubService {
    private final GitHubProperties gitHubProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    public GitHubService(GitHubProperties gitHubProperties) {
        this.gitHubProperties = gitHubProperties;
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

    @Cacheable("githubContributors")
    public String fetchContributors() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, ObjectNode> uniqueContributors = new HashMap<>();

        List<String> repositoryUrls = gitHubProperties.getRepositories().stream()
                .map(repo -> "https://api.github.com/repos/" + repo + "/contributors")
                .toList();

        for (String url : repositoryUrls) {
            String response = fetchContributorsFromUrl(url);
            try {
                JsonNode array = mapper.readTree(response);
                if (array.isArray()) {
                    for (JsonNode contributor : array) {
                        String login = contributor.get("login").asText();
                        int contributions = contributor.get("contributions").asInt();

                        if (uniqueContributors.containsKey(login)) {
                            ObjectNode existing = uniqueContributors.get(login);
                            int current = existing.get("contributions").asInt();
                            existing.put("contributions", current + contributions);
                        } else {
                            ObjectNode copy = contributor.deepCopy();
                            uniqueContributors.put(login, copy);
                        }
                    }
                }
            } catch (Exception e) {
                // Optionally log or handle error
            }
        }

        // Sort contributors by contributions descending
        List<ObjectNode> sortedContributors = uniqueContributors.values().stream()
                .sorted((a, b) -> Integer.compare(
                        b.get("contributions").asInt(),
                        a.get("contributions").asInt()))
                .toList();

        ArrayNode resultArray = mapper.createArrayNode();
        sortedContributors.forEach(resultArray::add);

        ObjectNode result = mapper.createObjectNode();
        result.set("contributors", resultArray);
        return result.toString();
    }
}
