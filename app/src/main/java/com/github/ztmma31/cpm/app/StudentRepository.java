package com.github.ztmma31.cpm.app;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    Student findById(int id);

    Optional<Student> findByUsername(String username);
}
