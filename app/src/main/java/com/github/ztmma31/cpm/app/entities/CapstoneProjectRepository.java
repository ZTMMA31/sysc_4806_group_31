package com.github.ztmma31.cpm.app.entities;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

//extends crud repo to inherits
public interface CapstoneProjectRepository
    extends CrudRepository<CapstoneProject, Long> {
    
    // use entity graph to pull down students with their project
    @EntityGraph(attributePaths = "students")
    List<CapstoneProject> findByProfessor(Professor professor);
}
