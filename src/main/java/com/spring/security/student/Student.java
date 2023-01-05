package com.spring.security.student;

public class Student {

	private final String studentName;
	private final Integer studentId;

	public Student(Integer studentId, String studentName) {
		super();
		this.studentName = studentName;
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public Integer getStudentId() {
		return studentId;
	}

}
