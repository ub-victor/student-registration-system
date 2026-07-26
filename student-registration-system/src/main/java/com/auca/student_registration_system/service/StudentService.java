package com.auca.student_registration_system.service;

import com.auca.student_registration_system.entity.Student;
import com.auca.student_registration_system.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentResponse saveStudent(StudentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Student request cannot be null");
        }

        if (studentRepository.existsByRegNo(request.getRegNo())) {
            throw new IllegalArgumentException("Student with registration number '" + request.getRegNo() + "' already exists");
        }

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Student with email '" + request.getEmail() + "' already exists");
        }

        Student student = Student.builder()
                .regNo(request.getRegNo())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .phone(request.getPhone())
                .gender(request.getGender())
                .status(request.getStatus())
                .build();

        Student savedStudent = studentRepository.save(student);
        return toResponse(savedStudent);
    }

    private StudentResponse toResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setRegNo(student.getRegNo());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setEmail(student.getEmail());
        response.setPhone(student.getPhone());
        response.setGender(student.getGender());
        response.setStatus(student.getStatus());
        return response;
    }
}
