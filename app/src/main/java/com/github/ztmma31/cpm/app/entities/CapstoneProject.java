package com.github.ztmma31.cpm.app.entities;

import jakarta.persistence.*;

import java.util.List;

/* A 4th year project topic (capstone project) managed by a professor.
The professor can give a title and description and select program restrictions
for students. Students can search for and join projects that are not full.
Professors can create, delete, and archive project topics.
 */
@Entity
public class CapstoneProject {
    @Id
    @GeneratedValue
    private Long id;
    // The title of the 4th year project. Likely only a few words.
    private String title;
    // The description of the 4th year project topic, containing the bulk of the text.
    private String description;
    // Students must be in one of these programs to apply for the project.
    @Transient
    private List<Program> restrictions;

    // The students that have applied for the 4th year project.
    @OneToMany(cascade = CascadeType.ALL)
    private List<Student> students;
    // The professor managing the project.

    @ManyToOne
    private Professor professor;

    public long getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Program> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Program> restrictions) {
        this.restrictions = restrictions;
    }

    public void addRestrictions(List<Program> newRestrictions)
    {
        for(Program restriction  :newRestrictions){
            if (!restrictions.contains(restriction))
            {
                restrictions.add(restriction);
            }
        }
    }

    public void removeRestrictions(List<Program> toRemove)
    {
        for(Program restriction  :toRemove){
            restrictions.remove(restriction);
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student)
    {
        if (!students.contains(student))
        {
            students.add(student);
        }
    }

    public void removeStudent(Student student)
    {
        students.remove(student);
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
