package com.github.ztmma31.cpm.app.entities;

import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor, Integer>{
    Professor findById(int id);
}
