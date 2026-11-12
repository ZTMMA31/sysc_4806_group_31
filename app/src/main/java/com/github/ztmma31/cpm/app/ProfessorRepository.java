package com.github.ztmma31.cpm.app;

import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor, Integer>{
    Professor findById(int id);
}
