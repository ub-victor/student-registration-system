package com.auca.student_registration_system.repository;

import com.auca.student_registration_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByRegNo(String regNo);

    boolean existsByEmail(String email);

    Optional<Student> findByRegNo(String regNo);
}
