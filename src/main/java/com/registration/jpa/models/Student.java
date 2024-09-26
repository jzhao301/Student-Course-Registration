package com.registration.jpa.models;



import java.util.List;

import jakarta.persistence.CascadeType;

// import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Student {
    @Id
    @GeneratedValue
    private Integer uin;

    private String firstName;
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "students_courses",
        joinColumns = {
            @JoinColumn(name = "student_uin")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "course_id")
        }
    )
    private List<Course> courses;

    // @ManyToMany(fetch = FetchType.EAGER)       For simplistic relational tracking only
    // @JoinTable(
    //     name = "course_history",
    //     joinColumns = {
    //         @JoinColumn(name = "student_uin")
    //     },
    //     inverseJoinColumns = {
    //         @JoinColumn(name = "course_id")
    //     }
    // )
    // private List<Course> courseHistory;
    


    // CourseHistory repository ommitted in favor of using cascade in Course and Student
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER) // For relational tracking with extra columns
    private List<CourseHistory> courseHistory;


    public String toString() { 
        return this.firstName + " " + this.lastName;
    } 
}
