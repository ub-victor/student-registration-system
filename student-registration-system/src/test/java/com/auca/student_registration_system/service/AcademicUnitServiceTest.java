package com.auca.student_registration_system.service;

import com.auca.student_registration_system.entity.AcademicUnit;
import com.auca.student_registration_system.repository.AcademicUnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AcademicUnitServiceTest {

    private AcademicUnitRepository academicUnitRepository;
    private AcademicUnitService academicUnitService;

    @BeforeEach
    void setUp() {
        academicUnitRepository = new InMemoryAcademicUnitRepository();
        academicUnitService = new AcademicUnitService(academicUnitRepository);
    }

    @Test
    void shouldSaveFacultyAcademicUnitWithoutParent() {
        AcademicUnitRequest request = new AcademicUnitRequest();
        request.setCode("ENG");
        request.setName("Faculty of Engineering");
        request.setType("FACULTY");

        AcademicUnitResponse response = academicUnitService.saveAcademicUnit(request);

        assertNotNull(response);
        assertEquals("ENG", response.getCode());
        assertEquals("Faculty of Engineering", response.getName());
        assertEquals("FACULTY", response.getType());
        assertEquals(null, response.getParentCode());
    }

    @Test
    void shouldSaveProgrammeAcademicUnitWithFacultyParent() {
        AcademicUnitRequest facultyRequest = new AcademicUnitRequest();
        facultyRequest.setCode("ENG");
        facultyRequest.setName("Faculty of Engineering");
        facultyRequest.setType("FACULTY");
        academicUnitService.saveAcademicUnit(facultyRequest);

        AcademicUnitRequest request = new AcademicUnitRequest();
        request.setCode("CS");
        request.setName("Computer Science Programme");
        request.setType("PROGRAM");
        request.setParentCode("ENG");

        AcademicUnitResponse response = academicUnitService.saveAcademicUnit(request);

        assertNotNull(response);
        assertEquals("CS", response.getCode());
        assertEquals("Computer Science Programme", response.getName());
        assertEquals("PROGRAMME", response.getType());
        assertEquals("ENG", response.getParentCode());
    }

    @Test
    void shouldSaveDepartmentAcademicUnitWithProgrammeParent() {
        AcademicUnitRequest facultyRequest = new AcademicUnitRequest();
        facultyRequest.setCode("ENG");
        facultyRequest.setName("Faculty of Engineering");
        facultyRequest.setType("FACULTY");
        academicUnitService.saveAcademicUnit(facultyRequest);

        AcademicUnitRequest programmeRequest = new AcademicUnitRequest();
        programmeRequest.setCode("CS");
        programmeRequest.setName("Computer Science Programme");
        programmeRequest.setType("PROGRAM");
        programmeRequest.setParentCode("ENG");
        academicUnitService.saveAcademicUnit(programmeRequest);

        AcademicUnitRequest request = new AcademicUnitRequest();
        request.setCode("SE");
        request.setName("Software Engineering Department");
        request.setType("DEPARTMENT");
        request.setParentCode("CS");

        AcademicUnitResponse response = academicUnitService.saveAcademicUnit(request);

        assertNotNull(response);
        assertEquals("SE", response.getCode());
        assertEquals("Software Engineering Department", response.getName());
        assertEquals("DEPARTMENT", response.getType());
        assertEquals("CS", response.getParentCode());
    }

    @Test
    void shouldSaveProgrammeAcademicUnitUsingProgrammeAlias() {
        AcademicUnitRequest facultyRequest = new AcademicUnitRequest();
        facultyRequest.setCode("ENG");
        facultyRequest.setName("Faculty of Engineering");
        facultyRequest.setType("FACULTY");
        academicUnitService.saveAcademicUnit(facultyRequest);

        AcademicUnitRequest request = new AcademicUnitRequest();
        request.setCode("CS");
        request.setName("Computer Science Programme");
        request.setType("PROGRAMME");
        request.setParentCode("ENG");

        AcademicUnitResponse response = academicUnitService.saveAcademicUnit(request);

        assertNotNull(response);
        assertEquals("PROGRAMME", response.getType());
        assertEquals("ENG", response.getParentCode());
    }

    @Test
    void shouldRejectProgrammeParentThatIsNotFaculty() {
        AcademicUnitRequest facultyRequest = new AcademicUnitRequest();
        facultyRequest.setCode("ENG");
        facultyRequest.setName("Faculty of Engineering");
        facultyRequest.setType("FACULTY");
        academicUnitService.saveAcademicUnit(facultyRequest);

        AcademicUnitRequest departmentRequest = new AcademicUnitRequest();
        departmentRequest.setCode("SE");
        departmentRequest.setName("Software Engineering Department");
        departmentRequest.setType("DEPARTMENT");
        departmentRequest.setParentCode("ENG");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> academicUnitService.saveAcademicUnit(departmentRequest));

        assertEquals("Parent academic unit for DEPARTMENT must be a PROGRAMME", exception.getMessage());
    }

    @Test
    void shouldRejectMissingParentForProgramme() {
        AcademicUnitRequest request = new AcademicUnitRequest();
        request.setCode("CS");
        request.setName("Computer Science Programme");
        request.setType("PROGRAM");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> academicUnitService.saveAcademicUnit(request));
        assertEquals("Parent code is required for Programme", exception.getMessage());
    }

    private static class InMemoryAcademicUnitRepository implements AcademicUnitRepository {
        private final Map<String, AcademicUnit> unitsByCode = new LinkedHashMap<>();

        @Override
        public boolean existsByCode(String code) {
            return unitsByCode.containsKey(code);
        }

        @Override
        public Optional<AcademicUnit> findByCode(String code) {
            return Optional.ofNullable(unitsByCode.get(code));
        }

        @Override
        public AcademicUnit save(AcademicUnit academicUnit) {
            unitsByCode.put(academicUnit.getCode(), academicUnit);
            return academicUnit;
        }

        @Override
        public <S extends AcademicUnit> S saveAndFlush(S entity) {
            return save(entity);
        }

        @Override
        public <S extends AcademicUnit> List<S> saveAll(Iterable<S> entities) {
            List<S> saved = new ArrayList<>();
            for (S entity : entities) {
                saved.add(save(entity));
            }
            return saved;
        }

        @Override
        public <S extends AcademicUnit> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends AcademicUnit> List<S> findAll(Example<S> example) {
            return Collections.emptyList();
        }

        @Override
        public <S extends AcademicUnit> List<S> findAll(Example<S> example, Sort sort) {
            return Collections.emptyList();
        }

        @Override
        public <S extends AcademicUnit> Page<S> findAll(Example<S> example, Pageable pageable) {
            return Page.empty(pageable);
        }

        @Override
        public <S extends AcademicUnit> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends AcademicUnit> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends AcademicUnit, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }

        @Override
        public Optional<AcademicUnit> findById(Long id) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long id) {
            return false;
        }

        @Override
        public List<AcademicUnit> findAll() {
            return new ArrayList<>(unitsByCode.values());
        }

        @Override
        public List<AcademicUnit> findAllById(Iterable<Long> longs) {
            return Collections.emptyList();
        }

        @Override
        public long count() {
            return unitsByCode.size();
        }

        @Override
        public void deleteById(Long id) {
        }

        @Override
        public void delete(AcademicUnit entity) {
        }

        @Override
        public void deleteAllById(Iterable<? extends Long> ids) {
        }

        @Override
        public void deleteAll(Iterable<? extends AcademicUnit> entities) {
        }

        @Override
        public void deleteAll() {
        }

        @Override
        public void flush() {
        }

        @Override
        public void deleteAllInBatch(Iterable<AcademicUnit> entities) {
        }

        @Override
        public void deleteAllByIdInBatch(Iterable<Long> ids) {
        }

        @Override
        public void deleteAllInBatch() {
        }

        @Override
        public AcademicUnit getReferenceById(Long id) {
            return null;
        }

        @Override
        public List<AcademicUnit> findAll(Sort sort) {
            return findAll();
        }

        @Override
        public Page<AcademicUnit> findAll(Pageable pageable) {
            return new PageImpl<>(findAll(), pageable);
        }
    }
}
