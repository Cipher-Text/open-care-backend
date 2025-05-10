package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.home.BlogPost;
import com.ciphertext.opencarebackend.dto.home.FeaturedData;
import com.ciphertext.opencarebackend.dto.home.HealthTip;
import com.ciphertext.opencarebackend.dto.home.Testimonial;
import com.ciphertext.opencarebackend.dto.response.MedicalSpecialityResponse;

import java.util.List;
import java.util.Map;

public interface HomeService {
    Map<String, Object> getHomeData();
    FeaturedData getFeaturedData();
    List<BlogPost> getBlogPosts();
    List<MedicalSpecialityResponse> getPopularSpecialties(int limit);
    List<Testimonial> getTestimonials();
    List<HealthTip> getHealthTips();
}
