package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.dto.filter.DoctorFilter;
import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.entity.DoctorDegree;
import com.ciphertext.opencarebackend.entity.DoctorWorkplace;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.DoctorRepository;
import com.ciphertext.opencarebackend.respository.specification.Filter;
import com.ciphertext.opencarebackend.service.DoctorService;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ciphertext.opencarebackend.respository.specification.QueryFilterUtils.generateIndividualFilter;
import static com.ciphertext.opencarebackend.respository.specification.QueryFilterUtils.generateJoinTableFilter;
import static com.ciphertext.opencarebackend.respository.specification.QueryOperator.JOIN;
import static com.ciphertext.opencarebackend.respository.specification.QueryOperator.LIKE;
import static com.ciphertext.opencarebackend.respository.specification.SpecificationBuilder.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        log.info("Fetching all doctors");
        List<Doctor> doctors = doctorRepository.findAll();
        log.info("Retrieved {} doctors", doctors.size());
        return doctors;
    }

    @Override
    public Page<Doctor> getPaginatedDataWithFilters(DoctorFilter doctorFilter, Pageable pagingSort) {
        log.info("Fetching doctors with filters: {}", doctorFilter);
        List<Filter> filterList = generateQueryFilters(doctorFilter);
        Specification<Doctor> specification = where(null);
        if(!filterList.isEmpty()) {
            specification = where(createSpecification(filterList.removeFirst()));
            for (Filter input : filterList) {
                specification = specification.and(createSpecification(input));
            }
        }
        if (doctorFilter.getDegreeId() != null) {
            specification = specification.and(hasDegree(doctorFilter.getDegreeId()));
        }
        if (doctorFilter.getHospitalId() != null) {
            specification = specification.and(worksAtHospital(doctorFilter.getHospitalId()));
        }
        if( doctorFilter.getWorkInstitutionId() != null) {
            specification = specification.and(worksAtInstitution(doctorFilter.getWorkInstitutionId()));
        }
        if (doctorFilter.getStudyInstitutionId() != null) {
            specification = specification.and(studyAtInstitution(doctorFilter.getStudyInstitutionId()));
        }
        if (doctorFilter.getSpecialityId() != null) {
            Specification<Doctor> specialitySpec =
                    hasDegreeSpeciality(doctorFilter.getSpecialityId())
                            .or(hasHospitalSpeciality(doctorFilter.getSpecialityId()));

            specification = specification.and(specialitySpec);
        }
        log.info("Fetching doctors with filters: {}", doctorFilter);
        return doctorRepository.findAll(specification, pagingSort);
    }

    @Override
    public Doctor getDoctorById(Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid doctor ID: {}", id);
            throw new BadRequestException("Doctor ID must be positive");
        }

        log.info("Fetching doctor with id: {}", id);
        return doctorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Doctor not found with id: {}", id);
                    return new ResourceNotFoundException("Doctor not found with id: " + id);
                });
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        log.info("Creating doctor: {}", doctor);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Doctor newDoctor, Long doctorId) {
        log.info("Updating doctor: {}", newDoctor);
        return doctorRepository.findById(doctorId)
                .map(doctor -> {
                    doctor.setStartDate(newDoctor.getStartDate());
                    doctor.setBmdcNo(newDoctor.getBmdcNo());
                    doctor.setDescription(newDoctor.getDescription());
                    doctor.setDegrees(newDoctor.getDegrees());
                    return doctorRepository.save(doctor);
                })
                .orElseGet(() -> {
                    newDoctor.setId(doctorId);
                    return doctorRepository.save(newDoctor);
                });
    }

    @Override
    public ResponseEntity<Object> deleteDoctorById(Long doctorId) {
        log.info("Deleting doctor with id: {}", doctorId);
        doctorRepository.deleteById(doctorId);
        if (doctorRepository.findById(doctorId).isPresent()) {
            return ResponseEntity.unprocessableEntity().body("Failed to delete the specified record");
        } else return ResponseEntity.ok().body("Doctor is Deleted Successfully");
    }

    public static Specification<Doctor> hasDegree(Integer degreeId) {
        return (root, query, cb) -> {
            // Prevent duplicate results
            query.distinct(true);

            // Join Doctor -> DoctorDegree
            Root<DoctorDegree> degreeRoot = query.from(DoctorDegree.class);
            Predicate doctorJoin = cb.equal(degreeRoot.get("doctor").get("id"), root.get("id"));
            Predicate degreeMatch = cb.equal(degreeRoot.get("degree").get("id"), degreeId);

            return cb.and(doctorJoin, degreeMatch);
        };
    }

    public static Specification<Doctor> hasDegreeSpeciality(Integer specialityId) {
        return (root, query, cb) -> {
            // Prevent duplicate results
            query.distinct(true);

            // Join Doctor -> DoctorDegree
            Root<DoctorDegree> degreeRoot = query.from(DoctorDegree.class);
            Predicate doctorJoin = cb.equal(degreeRoot.get("doctor").get("id"), root.get("id"));
            Predicate specialityMatch = cb.equal(degreeRoot.get("medicalSpeciality").get("id"), specialityId);

            return cb.and(doctorJoin, specialityMatch);
        };
    }

    public static Specification<Doctor> hasHospitalSpeciality(Integer specialityId) {
        return (root, query, cb) -> {
            // Prevent duplicate results
            query.distinct(true);

            // Join Doctor -> DoctorWorkplace
            Root<DoctorWorkplace> workplaceRoot = query.from(DoctorWorkplace.class);
            Predicate doctorJoin = cb.equal(workplaceRoot.get("doctor").get("id"), root.get("id"));
            Predicate specialityMatch = cb.equal(workplaceRoot.get("medicalSpeciality").get("id"), specialityId);

            return cb.and(doctorJoin, specialityMatch);
        };
    }

    public static Specification<Doctor> worksAtHospital(Integer hospitalId) {
        return (root, query, cb) -> {
            // Prevent duplicate results
            query.distinct(true);

            // Join Doctor -> DoctorWorkplace
            Root<DoctorWorkplace> workplaceRoot = query.from(DoctorWorkplace.class);
            Predicate doctorJoin = cb.equal(workplaceRoot.get("doctor").get("id"), root.get("id"));
            Predicate hospitalMatch = cb.equal(workplaceRoot.get("hospital").get("id"), hospitalId);

            return cb.and(doctorJoin, hospitalMatch);
        };
    }

    public static Specification<Doctor> worksAtInstitution(Integer institutionId) {
        return (root, query, cb) -> {
            // Prevent duplicate results
            query.distinct(true);

            // Join Doctor -> DoctorWorkplace
            Root<DoctorWorkplace> workplaceRoot = query.from(DoctorWorkplace.class);
            Predicate doctorJoin = cb.equal(workplaceRoot.get("doctor").get("id"), root.get("id"));
            Predicate institutionMatch = cb.equal(workplaceRoot.get("institution").get("id"), institutionId);

            return cb.and(doctorJoin, institutionMatch);
        };
    }

    public static Specification<Doctor> studyAtInstitution(Integer institutionId) {
        return (root, query, cb) -> {
            // Prevent duplicate results
            query.distinct(true);

            // Join Doctor -> DoctorDegree
            Root<DoctorDegree> degreeRoot = query.from(DoctorDegree.class);
            Predicate doctorJoin = cb.equal(degreeRoot.get("doctor").get("id"), root.get("id"));
            Predicate institutionMatch = cb.equal(degreeRoot.get("institution").get("id"), institutionId);

            return cb.and(doctorJoin, institutionMatch);
        };
    }

    private List<Filter> generateQueryFilters(DoctorFilter doctorFilter) {

        List<Filter> filters = new ArrayList<>();

        if (doctorFilter.getName() != null)
            filters.add(generateIndividualFilter("profile.name", LIKE, doctorFilter.getName()));

        if (doctorFilter.getBnName() != null)
            filters.add(generateIndividualFilter("profile.bnName", LIKE, doctorFilter.getBnName()));

        if (doctorFilter.getBmdcNo() != null)
            filters.add(generateIndividualFilter("bmdcNo", LIKE, doctorFilter.getBmdcNo()));

        if (doctorFilter.getDistrictId() != null)
            filters.add(generateJoinTableFilter("id", "profile.district", JOIN, doctorFilter.getDistrictId()));

        if (doctorFilter.getUpazilaId() != null)
            filters.add(generateJoinTableFilter("id", "profile.upazila", JOIN, doctorFilter.getUpazilaId()));

        if (doctorFilter.getUnionId() != null)
            filters.add(generateJoinTableFilter("id", "profile.union", JOIN, doctorFilter.getUnionId()));

        return filters;
    }
}
