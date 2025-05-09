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
    private final InstitutionService institutionService;
    private final ProfileService profileService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final DoctorWorkplaceService doctorWorkplaceService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getHomeData() {
        Map<String, Object> homeData = new HashMap<>();
        homeData.put("featuredData", getFeaturedData());
        homeData.put("blogPosts", getBlogPosts());
        homeData.put("popularSpecialties", doctorWorkplaceService.getTopMedicalSpecialities(10));
        return ResponseEntity.ok(homeData);
    }

    @GetMapping("/featured")
    public ResponseEntity<FeaturedData> getFeaturedDataEndpoint() {
        return ResponseEntity.ok(getFeaturedData());
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogPost>> getBlogPostsEndpoint() {
        return ResponseEntity.ok(getBlogPosts());
    }

    @GetMapping("/specialties")
    public ResponseEntity<List<MedicalSpecialityResponse>> getSpecialtiesEndpoint() {
        return ResponseEntity.ok(doctorWorkplaceService.getTopMedicalSpecialities(10));
    }

    private FeaturedData getFeaturedData() {
        FeaturedData featuredData = new FeaturedData();

        // Doctors
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor(1L, "Dr. John Smith", "Cardiology", 15,
                "https://via.placeholder.com/300x200?text=Dr.+John+Smith", 4.9, 156,
                "MD, Cardiology, Harvard Medical School", "john.smith@example.com"));

        doctors.add(new Doctor(2L, "Dr. Sarah Johnson", "Pediatrics", 12,
                "https://via.placeholder.com/300x200?text=Dr.+Sarah+Johnson", 4.8, 132,
                "MD, Pediatrics, Johns Hopkins University", "sarah.johnson@example.com"));

        doctors.add(new Doctor(3L, "Dr. Michael Chang", "Neurology", 20,
                "https://via.placeholder.com/300x200?text=Dr.+Michael+Chang", 4.7, 94,
                "MD, Neurology, Stanford University", "michael.chang@example.com"));

        doctors.add(new Doctor(4L, "Dr. Emily Rodriguez", "Dermatology", 8,
                "https://via.placeholder.com/300x200?text=Dr.+Emily+Rodriguez", 4.6, 87,
                "MD, Dermatology, UCLA", "emily.rodriguez@example.com"));

        // Hospitals
        List<Hospital> hospitals = new ArrayList<>();
        hospitals.add(new Hospital(1L, "City General Hospital", "123 Main St, Cityville",
                "https://via.placeholder.com/300x200?text=City+General+Hospital", "Multi-Specialty", 500,
                "+1 (555) 123-4567", Arrays.asList("Cardiology", "Neurology", "Oncology"), 4.8));

        hospitals.add(new Hospital(2L, "Riverside Medical Center", "456 River Rd, Riverside",
                "https://via.placeholder.com/300x200?text=Riverside+Medical+Center", "Cardiac Care", 350,
                "+1 (555) 234-5678", Arrays.asList("Cardiology", "Vascular Surgery"), 4.7));

        hospitals.add(new Hospital(3L, "Sunshine Children's Hospital", "789 Sun Ave, Sunnydale",
                "https://via.placeholder.com/300x200?text=Sunshine+Children's+Hospital", "Pediatric", 250,
                "+1 (555) 345-6789", Arrays.asList("Pediatrics", "Neonatology"), 4.9));

        // Institutes
        List<Institute> institutes = new ArrayList<>();
        institutes.add(new Institute(1L, "National Medical College", "Cityville",
                "https://via.placeholder.com/300x200?text=National+Medical+College",
                "123 College Rd, Cityville", "+1 (555) 456-7890",
                Arrays.asList("Medicine", "Surgery", "Pediatrics"), 1950));

        institutes.add(new Institute(2L, "Riverside Institute of Medicine", "Riverside",
                "https://via.placeholder.com/300x200?text=Riverside+Institute",
                "456 Institute Blvd, Riverside", "+1 (555) 567-8901",
                Arrays.asList("Cardiology", "Neurology", "Oncology"), 1965));

        // Stats
        Stats stats = Stats.builder()
                .totalDoctors(doctorService.getDoctorCount())
                .totalHospitals(hospitalService.getHospitalCount())
                .totalInstitutes(institutionService.getInstitutionCount())
                .totalUsers(profileService.getProfileCount())
                .build();

        featuredData.setDoctors(doctors);
        featuredData.setHospitals(hospitals);
        featuredData.setInstitutes(institutes);
        featuredData.setStats(stats);

        return featuredData;
    }

    private List<BlogPost> getBlogPosts() {
        List<BlogPost> blogPosts = new ArrayList<>();

        blogPosts.add(new BlogPost(1L, "Understanding Hypertension: Causes and Treatment",
                "https://via.placeholder.com/300x200?text=Hypertension+Article", "Health Tips",
                "Learn about the causes, symptoms, and treatment options for high blood pressure."));

        blogPosts.add(new BlogPost(2L, "Vaccination Schedule for Children",
                "https://via.placeholder.com/300x200?text=Vaccination+Article", "Pediatrics",
                "A complete guide to essential vaccines for children from birth to 12 years."));

        blogPosts.add(new BlogPost(3L, "Stress Management Techniques",
                "https://via.placeholder.com/300x200?text=Stress+Management", "Mental Health",
                "Effective strategies to manage stress and improve your mental wellbeing."));

        blogPosts.add(new BlogPost(4L, "New Cancer Treatment Breakthrough",
                "https://via.placeholder.com/300x200?text=Cancer+Research", "Medical News",
                "Researchers discover a promising new approach to treating advanced cancer."));

        blogPosts.add(new BlogPost(5L, "Diabetes Prevention: Diet and Exercise",
                "https://via.placeholder.com/300x200?text=Diabetes+Prevention", "Nutrition",
                "How proper diet and regular exercise can help prevent type 2 diabetes."));

        blogPosts.add(new BlogPost(6L, "COVID-19: Latest Updates and Guidelines",
                "https://via.placeholder.com/300x200?text=COVID+Updates", "Public Health",
                "Stay informed with the most recent information about COVID-19 prevention and treatment."));

        return blogPosts;
    }

    @GetMapping("/testimonials")
    public ResponseEntity<List<Testimonial>> getTestimonials() {
        List<Testimonial> testimonials = new ArrayList<>();

        testimonials.add(new Testimonial(1L, "Sarah Johnson", "/images/user1.jpg", 5,
                "This portal made it so easy to find a specialist for my condition. I was able to read reviews, check credentials, and book an appointment all in one place!"));

        testimonials.add(new Testimonial(2L, "Michael Brown", "/images/user2.jpg", 5,
                "Finding the right hospital for my procedure was stressful until I found this website. The detailed information and user reviews helped me make an informed decision."));

        testimonials.add(new Testimonial(3L, "Emily Davis", "/images/user3.jpg", 5,
                "The blog articles are informative and well-written. I've learned so much about managing my health conditions through this portal."));

        testimonials.add(new Testimonial(4L, "Robert Wilson", "/images/user4.jpg", 5,
                "I was able to compare different hospitals and their specialties before making my decision. This portal saved me so much time and stress."));

        return ResponseEntity.ok(testimonials);
    }

    @GetMapping("/health-tips")
    public ResponseEntity<List<HealthTip>> getHealthTips() {
        List<HealthTip> healthTips = new ArrayList<>();

        healthTips.add(new HealthTip(1L, "Preventive Health Measures",
                "/images/prevention.jpg",
                "Learn about key preventive health measures everyone should know about."));

        healthTips.add(new HealthTip(2L, "Medical Insurance Guide",
                "/images/insurance.jpg",
                "Understanding your medical insurance options and coverage."));

        healthTips.add(new HealthTip(3L, "Nutrition & Diet",
                "/images/diet.jpg",
                "Healthy eating habits and nutrition advice from medical professionals."));

        return ResponseEntity.ok(healthTips);
    }
}