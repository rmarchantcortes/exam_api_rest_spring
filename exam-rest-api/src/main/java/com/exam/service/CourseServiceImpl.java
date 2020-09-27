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
import com.exam.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseRepository courseRepository;

	@Override
	@Transactional(readOnly = true)
	public Optional<Course> findById(Long courseId) {
		return this.courseRepository.findById(courseId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Course> findAll() {
		return this.courseRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteById(Long courseId) {
		this.courseRepository.deleteById(courseId);
	}

	@Override
	@Transactional
	public Course save(Course course) {
		return this.courseRepository.save(course);
	}

	@Override
	public Page<Course> findAll(Pageable pageable) {
		return this.courseRepository.findAll(pageable);
	}
	
	@Override
	@Transactional
	public <T> ResponseEntity<Course> updateCourse(Long courseId, Course course){
		Optional<Course> optionalCourse = this.courseRepository.findById(courseId);
		if(optionalCourse.isPresent()) {
			Course courseFromDB = optionalCourse.get();
			if(!course.getName().equals(courseFromDB.getName())) {
				courseFromDB.setName(course.getName());
			}
			if(!course.getCode().equals(courseFromDB.getCode())) {
				courseFromDB.setCode(course.getCode());
			}
			this.courseRepository.save(courseFromDB);
			return ResponseEntity.ok(courseFromDB);
		}else {
			return ResponseEntity.notFound().build();
		}
	}

}
