package com.spring.security.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students")
public class SttudentManagementController {

	private static final List<Student> STUDENTS = Arrays.asList(new Student(1, "Hemant"), new Student(2, "Shivangi"),
			new Student(3, "VIjay"));

	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_TRAINEE')")
	public List<Student> getAllStudent() {
		System.out.println("getAllStudent");
		return STUDENTS;
	}

	@PostMapping
	@PreAuthorize("hasAuthority('student_write')")
	public void registerNewStudent(@RequestBody Student student) {
		System.out.println("registerNewStudent");
		System.out.println(student);
	}

	@DeleteMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('student_write')")
	public void deleteStudent(@PathVariable("studentId") Integer studentId) {
		System.out.println("deleteStudent");
		System.out.println(studentId);
	}

	@PutMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('student_write')")
	public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
		System.out.println("updateStudent");
		System.out.println(String.format("%s %s", studentId, student));
	}

}
