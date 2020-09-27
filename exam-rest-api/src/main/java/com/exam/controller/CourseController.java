package com.exam.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exam.model.Course;
import com.exam.service.CourseService;

@RestController
@RequestMapping("courses")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public Page<Course> getAllCoursesByPage() {
		PageRequest pageable = PageRequest.of(0, 10);
		return this.courseService.findAll(pageable);
	}

	@RequestMapping(value="all", method=RequestMethod.GET)
	public ResponseEntity<List<Course>> getAllCourses() {
		List<Course> courses = this.courseService.findAll();
		return ResponseEntity.ok(courses);
	}
	
	@RequestMapping(value= "{id}", method=RequestMethod.GET)
	public ResponseEntity<Course> getCourseById(@PathVariable("id") Long courseId) {
		Optional<Course> optionalCourse = this.courseService.findById(courseId);
		return optionalCourse.isPresent()?ResponseEntity.ok(optionalCourse.get()):ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Course> createCourse(@RequestBody Course course) {
		if(course.getCode().length>4) {
			this.courseService.save(course);
			return ResponseEntity.created(null).build();
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping(value= "{id}")
	public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable("id") Long courseId) {
		return this.courseService.updateCourse(courseId, course);
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long courseId) {
		Optional<Course> optionalCourse = this.courseService.findById(courseId);
		if(optionalCourse.isPresent()) {
			this.courseService.deleteById(courseId);
			return ResponseEntity.ok(null);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
