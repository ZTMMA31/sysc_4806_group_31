package com.github.ztmma31.cpm.app.entities;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfessorRepository extends CrudRepository<Professor, Integer>{
    Professor findById(int id);

    Optional<Professor> findByUsername(String username);
}
