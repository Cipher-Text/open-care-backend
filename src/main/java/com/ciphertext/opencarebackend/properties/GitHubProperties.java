package com.ciphertext.opencarebackend.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "github")
public class GitHubProperties {
    private String token;
    private List<String> repositories;

    // getters and setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public List<String> getRepositories() { return repositories; }
    public void setRepositories(List<String> repositories) { this.repositories = repositories; }
}
