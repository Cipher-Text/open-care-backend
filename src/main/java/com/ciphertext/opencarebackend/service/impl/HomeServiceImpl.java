package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.dto.filter.DoctorFilter;
import com.ciphertext.opencarebackend.dto.filter.HospitalFilter;
import com.ciphertext.opencarebackend.dto.filter.InstitutionFilter;
import com.ciphertext.opencarebackend.dto.home.*;
import com.ciphertext.opencarebackend.dto.response.MedicalSpecialityResponse;
import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.entity.Hospital;
import com.ciphertext.opencarebackend.entity.Institution;
import com.ciphertext.opencarebackend.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final InstitutionService institutionService;
    private final ProfileService profileService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final DoctorWorkplaceService doctorWorkplaceService;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getHomeData() {
        Map<String, Object> homeData = new HashMap<>();
        // Execute these calls in parallel using CompletableFuture for better performance
        CompletableFuture<FeaturedData> featuredDataFuture = CompletableFuture.supplyAsync(this::getFeaturedData);
        CompletableFuture<List<BlogPost>> blogPostsFuture = CompletableFuture.supplyAsync(this::getBlogPosts);
        CompletableFuture<List<MedicalSpecialityResponse>> specialtiesFuture =
                CompletableFuture.supplyAsync(() -> getPopularSpecialties(10));

        try {
            homeData.put("featuredData", featuredDataFuture.get());
            homeData.put("blogPosts", blogPostsFuture.get());
            homeData.put("popularSpecialties", specialtiesFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            log.error("Error retrieving home data", e);
            throw new RuntimeException("Error retrieving home data", e);
        }

        return homeData;
    }

    @Override
    @Transactional(readOnly = true)
    public FeaturedData getFeaturedData() {
        // Create a common Pageable object for all queries
        Pageable firstFiveItems = PageRequest.of(0, 5);

        // Run these database queries in parallel
        CompletableFuture<Page<Doctor>> doctorsFuture = CompletableFuture.supplyAsync(() ->
                doctorService.getPaginatedDataWithFilters(DoctorFilter.builder().build(), firstFiveItems));

        CompletableFuture<Page<Hospital>> hospitalsFuture = CompletableFuture.supplyAsync(() ->
                hospitalService.getPaginatedDataWithFilters(HospitalFilter.builder().build(), firstFiveItems));

        CompletableFuture<Page<Institution>> institutionsFuture = CompletableFuture.supplyAsync(() ->
                institutionService.getPaginatedDataWithFilters(InstitutionFilter.builder().build(), firstFiveItems));

        CompletableFuture<Stats> statsFuture = CompletableFuture.supplyAsync(() ->
                Stats.builder()
                        .totalDoctors(doctorService.getDoctorCount())
                        .totalHospitals(hospitalService.getHospitalCount())
                        .totalInstitutes(institutionService.getInstitutionCount())
                        .totalUsers(profileService.getProfileCount())
                        .build());

        try {
            FeaturedData featuredData = new FeaturedData();
            featuredData.setDoctors(doctorsFuture.get().getContent());
            featuredData.setHospitals(hospitalsFuture.get().getContent());
            featuredData.setInstitutes(institutionsFuture.get().getContent());
            featuredData.setStats(statsFuture.get());
            return featuredData;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            log.error("Error retrieving featured data", e);
            throw new RuntimeException("Error retrieving featured data", e);
        }
    }

    @Override
    public List<BlogPost> getBlogPosts() {
        // In a real application, these would come from a database
        return List.of(
                new BlogPost(1L, "Understanding Hypertension: Causes and Treatment",
                        "https://via.placeholder.com/300x200?text=Hypertension+Article", "Health Tips",
                        "Learn about the causes, symptoms, and treatment options for high blood pressure."),

                new BlogPost(2L, "Vaccination Schedule for Children",
                        "https://via.placeholder.com/300x200?text=Vaccination+Article", "Pediatrics",
                        "A complete guide to essential vaccines for children from birth to 12 years."),

                new BlogPost(3L, "Stress Management Techniques",
                        "https://via.placeholder.com/300x200?text=Stress+Management", "Mental Health",
                        "Effective strategies to manage stress and improve your mental wellbeing."),

                new BlogPost(4L, "New Cancer Treatment Breakthrough",
                        "https://via.placeholder.com/300x200?text=Cancer+Research", "Medical News",
                        "Researchers discover a promising new approach to treating advanced cancer."),

                new BlogPost(5L, "Diabetes Prevention: Diet and Exercise",
                        "https://via.placeholder.com/300x200?text=Diabetes+Prevention", "Nutrition",
                        "How proper diet and regular exercise can help prevent type 2 diabetes."),

                new BlogPost(6L, "COVID-19: Latest Updates and Guidelines",
                        "https://via.placeholder.com/300x200?text=COVID+Updates", "Public Health",
                        "Stay informed with the most recent information about COVID-19 prevention and treatment.")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalSpecialityResponse> getPopularSpecialties(int limit) {
        return doctorWorkplaceService.getTopMedicalSpecialities(limit);
    }

    @Override
    public List<Testimonial> getTestimonials() {
        return List.of(
                new Testimonial(1L, "Sarah Johnson", "/images/user1.jpg", 5,
                        "This portal made it so easy to find a specialist for my condition. I was able to read reviews, check credentials, and book an appointment all in one place!"),

                new Testimonial(2L, "Michael Brown", "/images/user2.jpg", 5,
                        "Finding the right hospital for my procedure was stressful until I found this website. The detailed information and user reviews helped me make an informed decision."),

                new Testimonial(3L, "Emily Davis", "/images/user3.jpg", 5,
                        "The blog articles are informative and well-written. I've learned so much about managing my health conditions through this portal."),

                new Testimonial(4L, "Robert Wilson", "/images/user4.jpg", 5,
                        "I was able to compare different hospitals and their specialties before making my decision. This portal saved me so much time and stress.")
        );
    }

    @Override
    public List<HealthTip> getHealthTips() {
        return List.of(
                new HealthTip(1L, "Preventive Health Measures",
                        "/images/prevention.jpg",
                        "Learn about key preventive health measures everyone should know about."),

                new HealthTip(2L, "Medical Insurance Guide",
                        "/images/insurance.jpg",
                        "Understanding your medical insurance options and coverage."),

                new HealthTip(3L, "Nutrition & Diet",
                        "/images/diet.jpg",
                        "Healthy eating habits and nutrition advice from medical professionals.")
        );
    }
}