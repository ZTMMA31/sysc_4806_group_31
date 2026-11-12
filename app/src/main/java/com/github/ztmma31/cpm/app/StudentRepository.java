package com.github.ztmma31.cpm.app;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    Student findById(int id);
}
