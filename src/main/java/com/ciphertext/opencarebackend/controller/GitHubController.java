package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/public/github")
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping(value = "/contributors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getContributors() {
        try {
            String jsonResponse = gitHubService.fetchContributors();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Failed to fetch contributors\"}");
        }
    }

    @Scheduled(fixedRate = 30 * 60 * 1000) // every 30 mins
    @CacheEvict(value = "githubContributors", allEntries = true)
    public void evictContributorsCache() { }

    @Scheduled(fixedRate = 10 * 60 * 1000) // every 10 mins
    @CacheEvict(value = "latestBlogPosts", allEntries = true)
    public void evictBlogCache() { }

    @Scheduled(fixedRate = 60 * 60 * 1000) // every 1 hour
    @CacheEvict(value = "topDoctors", allEntries = true)
    public void evictDoctorCache() { }


}
