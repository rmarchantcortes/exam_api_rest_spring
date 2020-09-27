package com.exam.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="course")
public class Course {
	
	@Id
	@Column(name="course_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long courseId;
	
	@Column(name="name", nullable = false, length = 45)
	private String name;
	
	@Column(name="code", nullable = false, length = 4)
	private char[] code;
	
	@JsonBackReference
	@OneToMany (fetch = FetchType.LAZY, mappedBy="course")
	private Set<Student> students;
	
	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char[] getCode() {
		return code;
	}

	public void setCode(char[] code) {
		this.code = code;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
}
