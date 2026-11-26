package com.github.ztmma31.cpm.app.entities;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface StudentRepository extends ListCrudRepository<Student, Integer> {

    Optional<Student> findByUsername(String username);
}
