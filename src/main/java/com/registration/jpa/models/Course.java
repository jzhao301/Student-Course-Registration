package com.registration.jpa.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Course {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private List<Student> students;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "course_prerequisites",
        joinColumns = {
            @JoinColumn(name = "course_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "prerequisite_id")
        }
    )
    private List<Course> prerequisites;

    // CourseHistory repository ommitted in favor of using cascade in Course and Student
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CourseHistory> courseHistory;

    public String toString() { 
        return this.name;
    } 
}
