package com.auca.student_registration_system.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicUnitResponse {
    private String code;
    private String name;
    private String type;
    private String parentCode;
}
