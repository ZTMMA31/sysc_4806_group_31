package com.github.ztmma31.cpm.app.entities;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

//extends crud repo to inherits
public interface CapstoneProjectRepository
    extends CrudRepository<CapstoneProject, Long> {

    List<CapstoneProject> findByProfessor(Professor professor);
}
