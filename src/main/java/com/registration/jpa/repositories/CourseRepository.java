package com.registration.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registration.jpa.models.Course;

public interface CourseRepository extends JpaRepository<Course, Integer>{
    List<Course> findAllByName(String name); // Find all courses with the name
    List<Course> findAllByStudentsUin(Integer uin); // Find all courses with a student
    List<Course> findAllByPrerequisitesId(Integer id); // Find all courses with a prerequisite
    List<Course> findAllByNameStartsWith(String name); // Find all courses with a name prefix
    Course findCourseById(Integer id); // Find a course by id
}
