package com.auca.student_registration_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA and data persistence configuration.
 * Enables auditing for entity creation/modification tracking.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.auca.student_registration_system.repository")
@EnableJpaAuditing
public class JpaConfig {

}
