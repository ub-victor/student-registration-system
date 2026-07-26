package com.auca.student_registration_system.service;

import com.auca.student_registration_system.entity.Student;
import com.auca.student_registration_system.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDate;
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

class StudentServiceTest {

    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentRepository = new InMemoryStudentRepository();
        studentService = new StudentService(studentRepository);
    }

    @Test
    void shouldSaveStudentAndReturnSavedStudent() {
        StudentRequest request = new StudentRequest();
        request.setRegNo("REG-001");
        request.setFirstName("Alice");
        request.setLastName("Uwase");
        request.setDateOfBirth(LocalDate.of(1999, 5, 20));
        request.setEmail("alice@example.com");
        request.setPhone("0788888888");
        request.setGender("FEMALE");
        request.setStatus("ACTIVE");

        StudentResponse response = studentService.saveStudent(request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("REG-001", response.getRegNo());
        assertEquals("Alice", response.getFirstName());
        assertEquals("Uwase", response.getLastName());
        assertEquals("alice@example.com", response.getEmail());
        assertEquals("ACTIVE", response.getStatus());
    }

    @Test
    void shouldRejectDuplicateRegistrationNumber() {
        StudentRequest request = new StudentRequest();
        request.setRegNo("REG-001");
        request.setFirstName("Alice");
        request.setLastName("Uwase");
        request.setDateOfBirth(LocalDate.of(1999, 5, 20));
        request.setEmail("alice@example.com");
        request.setStatus("ACTIVE");

        studentService.saveStudent(request);

        StudentRequest duplicate = new StudentRequest();
        duplicate.setRegNo("REG-001");
        duplicate.setFirstName("Bob");
        duplicate.setLastName("Mugisha");
        duplicate.setDateOfBirth(LocalDate.of(2000, 1, 1));
        duplicate.setEmail("bob@example.com");
        duplicate.setStatus("ACTIVE");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.saveStudent(duplicate));

        assertEquals("Student with registration number 'REG-001' already exists", exception.getMessage());
    }

    private static class InMemoryStudentRepository implements StudentRepository {
        private final Map<String, Student> studentsByRegNo = new LinkedHashMap<>();
        private final Map<Long, Student> studentsById = new LinkedHashMap<>();
        private long nextId = 1L;

        @Override
        public boolean existsByRegNo(String regNo) {
            return studentsByRegNo.containsKey(regNo);
        }

        @Override
        public boolean existsByEmail(String email) {
            return studentsByRegNo.values().stream().anyMatch(student -> email.equals(student.getEmail()));
        }

        @Override
        public Optional<Student> findByRegNo(String regNo) {
            return Optional.ofNullable(studentsByRegNo.get(regNo));
        }

        @Override
        public <S extends Student> S save(S student) {
            if (student.getId() == null) {
                student.setId(nextId++);
            }
            studentsById.put(student.getId(), student);
            studentsByRegNo.put(student.getRegNo(), student);
            return student;
        }

        @Override
        public <S extends Student> S saveAndFlush(S entity) {
            return save(entity);
        }

        @Override
        public <S extends Student> List<S> saveAll(Iterable<S> entities) {
            List<S> saved = new ArrayList<>();
            for (S entity : entities) {
                saved.add(save(entity));
            }
            return saved;
        }

        @Override
        public Optional<Student> findById(Long id) {
            return Optional.ofNullable(studentsById.get(id));
        }

        @Override
        public Student getReferenceById(Long id) {
            return studentsById.get(id);
        }

        @Override
        public boolean existsById(Long id) {
            return studentsById.containsKey(id);
        }

        @Override
        public List<Student> findAll() {
            return new ArrayList<>(studentsById.values());
        }

        @Override
        public List<Student> findAllById(Iterable<Long> longs) {
            return Collections.emptyList();
        }

        @Override
        public long count() {
            return studentsById.size();
        }

        @Override
        public void deleteById(Long id) {
            Student removed = studentsById.remove(id);
            if (removed != null) {
                studentsByRegNo.remove(removed.getRegNo());
            }
        }

        @Override
        public void delete(Student entity) {
            deleteById(entity.getId());
        }

        @Override
        public void deleteAllById(Iterable<? extends Long> ids) {
            for (Long id : ids) {
                deleteById(id);
            }
        }

        @Override
        public void deleteAll(Iterable<? extends Student> entities) {
            for (Student entity : entities) {
                delete(entity);
            }
        }

        @Override
        public void deleteAll() {
            studentsById.clear();
            studentsByRegNo.clear();
        }

        @Override
        public void flush() {
        }

        @Override
        public void deleteAllInBatch(Iterable<Student> entities) {
            deleteAll(entities);
        }

        @Override
        public void deleteAllByIdInBatch(Iterable<Long> ids) {
            deleteAllById(ids);
        }

        @Override
        public void deleteAllInBatch() {
            deleteAll();
        }

        @Override
        public List<Student> findAll(Sort sort) {
            return findAll();
        }

        @Override
        public Page<Student> findAll(Pageable pageable) {
            List<Student> students = findAll();
            return new PageImpl<>(students, pageable, students.size());
        }

        @Override
        public <S extends Student> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends Student> List<S> findAll(Example<S> example) {
            return Collections.emptyList();
        }

        @Override
        public <S extends Student> List<S> findAll(Example<S> example, Sort sort) {
            return Collections.emptyList();
        }

        @Override
        public <S extends Student> Page<S> findAll(Example<S> example, Pageable pageable) {
            return Page.empty(pageable);
        }

        @Override
        public <S extends Student> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends Student> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends Student, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }
    }
}
