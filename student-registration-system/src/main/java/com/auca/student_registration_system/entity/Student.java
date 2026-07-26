package com.auca.student_registration_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "students",
        indexes = {
                @Index(name = "idx_student_reg_no", columnList = "reg_no", unique = true),
                @Index(name = "idx_student_email", columnList = "email", unique = true)
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg_no", nullable = false, unique = true, length = 30)
    @NotBlank(message = "Registration number cannot be blank")
    @Size(max = 30, message = "Registration number must not exceed 30 characters")
    private String regNo;

    @Column(name = "first_name", nullable = false, length = 100)
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;

    @Column(name = "date_of_birth")
    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @Column(name = "phone", length = 20)
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "status", nullable = false, length = 30)
    @NotBlank(message = "Status cannot be blank")
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
