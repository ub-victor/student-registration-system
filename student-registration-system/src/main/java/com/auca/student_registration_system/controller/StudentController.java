package com.auca.student_registration_system.controller;

import com.auca.student_registration_system.service.StudentRequest;
import com.auca.student_registration_system.service.StudentResponse;
import com.auca.student_registration_system.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> saveStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse response = studentService.saveStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
