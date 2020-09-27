package com.exam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.exam.model.Course;

public interface CourseService {

	public Optional<Course> findById(Long coursetId);
	
	public List<Course> findAll();
	
	public Page<Course> findAll(Pageable pageable);
	
	public void deleteById(Long coursetId);
	
	public Course save(Course courset);

	public <T> ResponseEntity<Course> updateCourse(Long courseId, Course course);
}
