package com.auca.student_registration_system.entity;

import com.auca.student_registration_system.constants.EAcademicUnitType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "academic_units",
    indexes = {
        @Index(name = "idx_au_code", columnList = "code", unique = true),
        @Index(name = "idx_au_parent_id", columnList = "parent_id"),
        @Index(name = "idx_au_type", columnList = "type")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"parent", "children", "createdAt", "updatedAt"})
@ToString(exclude = {"parent", "children", "createdAt", "updatedAt"})
public class AcademicUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", unique = true, nullable = false, length = 20)
    @NotBlank(message = "Academic unit code cannot be blank")
    @Size(min = 3, max = 20, message = "Academic unit code must be between 3 and 20 characters")
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    @NotBlank(message = "Academic unit name cannot be blank")
    @Size(max = 255, message = "Academic unit name must not exceed 255 characters")
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Academic unit type cannot be null")
    private EAcademicUnitType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private AcademicUnit parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<AcademicUnit> children = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
