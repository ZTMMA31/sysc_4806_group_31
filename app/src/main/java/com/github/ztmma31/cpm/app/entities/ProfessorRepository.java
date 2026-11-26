package com.github.ztmma31.cpm.app.entities;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface ProfessorRepository extends ListCrudRepository<Professor, Integer> {

    Optional<Professor> findByUsername(String username);
}
