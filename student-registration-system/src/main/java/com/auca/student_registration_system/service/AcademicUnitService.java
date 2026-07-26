package com.auca.student_registration_system.service;

import com.auca.student_registration_system.constants.EAcademicUnitType;
import com.auca.student_registration_system.entity.AcademicUnit;
import com.auca.student_registration_system.repository.AcademicUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AcademicUnitService {

    private final AcademicUnitRepository academicUnitRepository;

    public AcademicUnitResponse saveAcademicUnit(AcademicUnitRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Academic unit request cannot be null");
        }

        if (academicUnitRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Academic unit with code '" + request.getCode() + "' already exists");
        }

        EAcademicUnitType type = resolveType(request.getType());

        AcademicUnit academicUnit = AcademicUnit.builder()
                .code(request.getCode())
                .name(request.getName())
                .type(type)
                .build();

        if (type != EAcademicUnitType.FACULTY) {
            if (request.getParentCode() == null || request.getParentCode().isBlank()) {
                throw new IllegalArgumentException("Parent code is required for " + type.getDisplayName());
            }

            AcademicUnit parent = academicUnitRepository.findByCode(request.getParentCode())
                    .orElseThrow(() -> new IllegalArgumentException("Parent academic unit with code '" + request.getParentCode() + "' was not found"));

            validateParentType(type, parent);
            academicUnit.setParent(parent);
        }

        AcademicUnit savedUnit = academicUnitRepository.save(academicUnit);
        return toResponse(savedUnit);
    }

    private EAcademicUnitType resolveType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Academic unit type cannot be null");
        }

        String normalizedType = type.trim().toUpperCase(Locale.ROOT);

        return switch (normalizedType) {
            case "FACULTY" -> EAcademicUnitType.FACULTY;
            case "PROGRAM", "PROGRAMME" -> EAcademicUnitType.PROGRAMME;
            case "DEPARTMENT" -> EAcademicUnitType.DEPARTMENT;
            default -> throw new IllegalArgumentException("Unsupported academic unit type: " + type);
        };
    }

    private void validateParentType(EAcademicUnitType type, AcademicUnit parent) {
        if (type == EAcademicUnitType.PROGRAMME && parent.getType() != EAcademicUnitType.FACULTY) {
            throw new IllegalArgumentException("Parent academic unit for PROGRAMME must be a FACULTY");
        }

        if (type == EAcademicUnitType.DEPARTMENT && parent.getType() != EAcademicUnitType.PROGRAMME) {
            throw new IllegalArgumentException("Parent academic unit for DEPARTMENT must be a PROGRAMME");
        }
    }

    private AcademicUnitResponse toResponse(AcademicUnit academicUnit) {
        AcademicUnitResponse response = new AcademicUnitResponse();
        response.setCode(academicUnit.getCode());
        response.setName(academicUnit.getName());
        response.setType(academicUnit.getType().getValue());
        response.setParentCode(academicUnit.getParent() != null ? academicUnit.getParent().getCode() : null);
        return response;
    }
}
