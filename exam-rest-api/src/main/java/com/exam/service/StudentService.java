package com.exam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.exam.model.Student;

public interface StudentService {

	public Optional<Student> findById(Long studentId);
	
	public List<Student> findAll();
	
	public Page<Student> findAll(Pageable pageable);
	
	public void deleteById(Long studentId);
	
	public Student save(Student student);
	
	public <T> ResponseEntity<Student> updateStudent(Long studentId, Student student);
}
