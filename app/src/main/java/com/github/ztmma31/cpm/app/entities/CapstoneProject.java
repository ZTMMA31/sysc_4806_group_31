package com.github.ztmma31.cpm.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

/* A 4th year project topic (capstone project) managed by a professor.
The professor can give a title and description and select program restrictions
for students. Students can search for and join projects that are not full.
Professors can create, delete, and archive project topics.
 */
@Getter
@Setter
@ToString
@Entity
public class CapstoneProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// The title of the 4th year project. Likely only a few words.
    private String title;

    /// The description of the 4th year project topic, containing the bulk of the text.
    private String description;

    /// Students must be in one of these programs to apply for the project.
    @Transient
    private Set<Program> restrictions;

    /// The students that are registered to this project.
    @OneToMany(mappedBy = "project")
    private Set<Student> students;

    @OneToMany(mappedBy = "appliedProject")
    private Set<Student> appliedStudents;

    /// The professor managing the project.
    @ManyToOne(optional = false)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    public void addRestrictions(Set<Program> newRestrictions)
    {
        restrictions.addAll(newRestrictions);
    }

    public void removeRestrictions(List<Program> toRemove)
    {
        for(Program restriction  :toRemove){
            restrictions.remove(restriction);
        }
    }

    public void addStudent(Student student)
    {
        students.add(student);
    }

    public void removeStudent(Student student)
    {   
        students.remove(student);
    }
}
