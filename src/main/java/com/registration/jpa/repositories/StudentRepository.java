package com.registration.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registration.jpa.models.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
    List<Student> findAllByFirstName(String fn); // Find all students with the first name
    List<Student> findAllByLastName(String ln); // Find all students with the last name
    List<Student> findAllByFirstNameAndLastName(String fn, String ln); // Find all students with the first name and last name
    List<Student> findAllByFirstNameOrLastName(String fn, String ln); // Find all students with the first name or last name
    List<Student> findAllByCoursesId(Integer id); // Find all students enrolled in a course
    List<Student> findAllByCoursesIdNot(Integer id); // Find all students not enrolled in a course
    List<Student> findAllByOrderByFirstNameAsc(); // Find all students ordered by first name
    Student findStudentByUin(Integer uin); // Find a student by UIN
}
