package com.registration.jpa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.registration.jpa.repositories.*;
import com.github.javafaker.Faker;
import com.registration.jpa.models.*;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
		StudentRepository studentRepository,
		CourseRepository courseRepository
	){
		return args -> {


			for (int i = 0; i < 50; i++) { // populates table with 50 students for testing purposes
				Faker faker = new Faker();
				var student = Student.builder()
						.firstName(faker.name().firstName())
						.lastName(faker.name().lastName())
						.build();
				studentRepository.save(student);
			};

			Set<String> courseNames = new HashSet<>();
			while (courseNames.size() < 50) { // populates table with 50 unique courses with prerequisites for testing purposes
				Faker faker = new Faker();
				List<String> degreeName = Arrays.asList(faker.educator().course().split("\\s+"));
				System.out.println(degreeName);
				List<String> subjectName = degreeName.subList(Math.max(degreeName.indexOf("in"), degreeName.indexOf("of"))+1, degreeName.size());
				String courseName = String.join(" ", subjectName.toArray(new String[0])) + " " + faker.number().numberBetween(100, 500);
				if (!courseNames.contains(courseName)) {
					List<Course> similarCourses = courseRepository.findAllByNameStartsWith(courseName.substring(0, courseName.length() - 4));
					System.out.println(similarCourses);
					courseNames.add(courseName);
					Course course = Course.builder()
					.name(courseName)
					.build();
					courseRepository.save(course);
					course = courseRepository.findCourseById(course.getId());
					for (Course similarCourse : similarCourses) {
						if (similarCourse.getName().length() ==  courseName.length()) {
							int temp_length = courseName.length();
							System.out.println(similarCourse.getName().substring(0, temp_length - 2).compareTo(courseName.substring(0, temp_length - 2)) < 0);
							if (similarCourse.getName().substring(0, temp_length - 2).compareTo(courseName.substring(0, temp_length - 2)) < 0) {
								course.getPrerequisites().add(similarCourse);
							}
							else {
								similarCourse.getPrerequisites().add(course);
								courseRepository.save(similarCourse);
							}
						}
					}
					courseRepository.save(course);
				}
			}

			var course = courseRepository.findCourseById(1);
			if (course != null) { // Enrolls all students in course 1
				for (int i = 0; i < 50; i++) {
					var student = studentRepository.findStudentByUin(i + 1);
					if (student != null) {
						student.getCourses().add(course);
						studentRepository.save(student);
					}
				}
			}
			System.out.println(studentRepository.findAllByCoursesId(1)); // prints all students enrolled in course 1

			for(var student: studentRepository.findAll()){ // prints all student schedules
				System.out.println(student.getCourses());
			}

			for (var superCourse : courseRepository.findAll()) { // prints all courses and their prerequisites
				System.out.println(superCourse.getPrerequisites());
			}
			var student = studentRepository.findStudentByUin(1);
			course = courseRepository.findCourseById(1);
			if (student != null && course != null) { // adds a grade of 100 to student 1's course history for course 1
				CourseHistory courseHistory = CourseHistory.builder()
				.student(student)
				.course(course)
				.grade(100)
				.build();
				student.getCourseHistory().add(courseHistory);
				studentRepository.save(student);
			}

			student = studentRepository.findStudentByUin(2);
			studentRepository.delete(student); // deletes student 2
		};

	}

}
