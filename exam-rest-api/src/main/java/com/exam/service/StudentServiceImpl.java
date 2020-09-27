package com.exam.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.model.Course;
import com.exam.model.Student;
import com.exam.repository.CourseRepository;
import com.exam.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{
	

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Student> findById(Long studentId) {
		return this.studentRepository.findById(studentId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> findAll() {
		return this.studentRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteById(Long studentId) {
		this.studentRepository.deleteById(studentId);
	}

	@Override
	@Transactional
	public Student save(Student student) {
		return this.studentRepository.save(student);
	}

	@Override
	@Transactional
	public <T> ResponseEntity<Student> updateStudent(Long studentId, Student student) {
		Optional<Student> optionalStudent = this.studentRepository.findById(studentId);
		if(optionalStudent.isPresent()) {
			Student studentFromDB = optionalStudent.get();
			if(student.getAge()!=studentFromDB.getAge()) {
				studentFromDB.setAge(student.getAge());
			}
			if(!student.getLastName().equals(studentFromDB.getLastName())) {
				studentFromDB.setLastName(student.getLastName());
			}
			if(!student.getName().equals(studentFromDB.getName())) {
				studentFromDB.setName(student.getName());
			}
			if(!student.getRut().equals(studentFromDB.getRut())) {
				studentFromDB.setRut(student.getRut());
			}
			if(!student.getCourse().getCourseId().equals(studentFromDB.getCourse().getCourseId())) {
				Optional<Course> optionalCourse = this.courseRepository.findById(student.getCourse().getCourseId());
				if(optionalCourse.isPresent()) {
					studentFromDB.setCourse(optionalCourse.get());
				}
			}
			this.studentRepository.save(studentFromDB);
			return ResponseEntity.ok(studentFromDB);
		}else {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public Page<Student> findAll(Pageable pageable) {
		return this.studentRepository.findAll(pageable);
	}

}
