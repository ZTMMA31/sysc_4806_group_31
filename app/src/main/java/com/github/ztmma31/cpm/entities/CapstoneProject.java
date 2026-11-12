package com.github.ztmma31.cpm.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
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
    private List<Program> restrictions;

    // The students that have applied for the 4th year project.
    private List<Student> students;
    // The professor managing the project.
    private Professor professor;
}
