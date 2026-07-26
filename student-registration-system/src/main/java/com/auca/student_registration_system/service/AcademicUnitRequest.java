package com.auca.student_registration_system.service;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicUnitRequest {

    @NotBlank(message = "Academic unit code cannot be blank")
    private String code;

    @NotBlank(message = "Academic unit name cannot be blank")
    private String name;

    @NotBlank(message = "Academic unit type cannot be blank")
    private String type;

    private String parentCode;
}
