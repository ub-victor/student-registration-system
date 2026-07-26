package com.auca.student_registration_system.repository;

import com.auca.student_registration_system.entity.AcademicUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicUnitRepository extends JpaRepository<AcademicUnit, Long> {
    boolean existsByCode(String code);

    Optional<AcademicUnit> findByCode(String code);
}
