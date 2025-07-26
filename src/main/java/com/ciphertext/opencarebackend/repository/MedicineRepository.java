package com.ciphertext.opencarebackend.repository;

import com.ciphertext.opencarebackend.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

    // Basic search with pagination
    @Query("SELECT m FROM Medicine m WHERE " +
            "LOWER(m.brandName) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(m.generic) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(m.manufacturer) LIKE LOWER(CONCAT('%', :term, '%'))")
    Page<Medicine> basicSearch(@Param("term") String term, Pageable pageable);

    // Full-text search
    @Query(value = """
        SELECT m.* FROM medicine m 
        WHERE m.search_vector @@ plainto_tsquery('english', :term)
        ORDER BY ts_rank(m.search_vector, plainto_tsquery('english', :term)) DESC
        """,
            countQuery = "SELECT COUNT(*) FROM medicine m WHERE m.search_vector @@ plainto_tsquery('english', :term)",
            nativeQuery = true)
    Page<Medicine> fullTextSearch(@Param("term") String term, Pageable pageable);

    // Advanced search with multiple parameters
    @Query(value = """
        SELECT m.* FROM medicine m 
        WHERE (:brandName IS NULL OR m.brand_name ILIKE CONCAT('%', :brandName, '%'))
        AND (:generic IS NULL OR m.generic ILIKE CONCAT('%', :generic, '%'))
        AND (:manufacturer IS NULL OR m.manufacturer ILIKE CONCAT('%', :manufacturer, '%'))
        AND (:type IS NULL OR m.type ILIKE CONCAT('%', :type, '%'))
        """,
            countQuery = """
        SELECT COUNT(*) FROM medicine m 
        WHERE (:brandName IS NULL OR m.brand_name ILIKE CONCAT('%', :brandName, '%'))
        AND (:generic IS NULL OR m.generic ILIKE CONCAT('%', :generic, '%'))
        AND (:manufacturer IS NULL OR m.manufacturer ILIKE CONCAT('%', :manufacturer, '%'))
        AND (:type IS NULL OR m.type ILIKE CONCAT('%', :type, '%'))
        """,
            nativeQuery = true)
    Page<Medicine> advancedSearch(
            @Param("brandName") String brandName,
            @Param("generic") String generic,
            @Param("manufacturer") String manufacturer,
            @Param("type") String type,
            Pageable pageable);
}