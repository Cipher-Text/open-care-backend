package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.home.*;
import com.ciphertext.opencarebackend.dto.response.MedicalSpecialityResponse;
import com.ciphertext.opencarebackend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeApiController {
    private final HomeService homeService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getHomeData() {
        return ResponseEntity.ok(homeService.getHomeData());
    }

    @GetMapping("/featured")
    public ResponseEntity<FeaturedData> getFeaturedDataEndpoint() {
        return ResponseEntity.ok(homeService.getFeaturedData());
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogPost>> getBlogPostsEndpoint() {
        return ResponseEntity.ok(homeService.getBlogPosts());
    }

    @GetMapping("/specialties")
    public ResponseEntity<List<MedicalSpecialityResponse>> getSpecialtiesEndpoint() {
        return ResponseEntity.ok(homeService.getPopularSpecialties(10));
    }

    @GetMapping("/testimonials")
    public ResponseEntity<List<Testimonial>> getTestimonials() {
        return ResponseEntity.ok(homeService.getTestimonials());
    }

    @GetMapping("/health-tips")
    public ResponseEntity<List<HealthTip>> getHealthTips() {
        return ResponseEntity.ok(homeService.getHealthTips());
    }
}