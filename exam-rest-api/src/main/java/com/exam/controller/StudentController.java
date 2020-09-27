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

import com.exam.model.Student;
import com.exam.service.StudentService;

@RestController
@RequestMapping("students")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public Page<Student> getAllStudentsByPage() {
		PageRequest pageable = PageRequest.of(0, 10);
		return this.studentService.findAll(pageable);
	}
	
	@RequestMapping(value="all", method=RequestMethod.GET)
	public ResponseEntity<List<Student>> getAllStudents() {
		List<Student> students = this.studentService.findAll();
		return ResponseEntity.ok(students);
	}
	
	@RequestMapping(value= "{id}", method=RequestMethod.GET)
	public ResponseEntity<Student> getStudentById(@PathVariable("id") Long studentId) {
		Optional<Student> optionalStudent = this.studentService.findById(studentId);
		return optionalStudent.isPresent()?ResponseEntity.ok(optionalStudent.get()):ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {
		if(student.getAge()>18 && validateRut(student.getRut())) {
			this.studentService.save(student);
			return ResponseEntity.created(null).build();
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	private boolean validateRut(String rut) {
		boolean validacion = false;
	    try {
	        rut = rut.toUpperCase();
	        rut = rut.replace(".", "");
	        rut = rut.replace("-", "");
	        int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

	        char dv = rut.charAt(rut.length() - 1);

	        int m = 0, s = 1;
	        for (; rutAux != 0; rutAux /= 10) {
	            s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
	        }
	        if (dv == (char) (s != 0 ? s + 47 : 75)) {
	            validacion = true;
	        }

	    } catch (NumberFormatException e) {
	    	return false;
	    }
	    return validacion;
	}

	@PutMapping(value= "{id}")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable("id") Long studentId) {
		return this.studentService.updateStudent(studentId, student);
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long studentId) {
		Optional<Student> optionalStudent = this.studentService.findById(studentId);
		if(optionalStudent.isPresent()) {
			studentService.deleteById(studentId);
			return ResponseEntity.ok(null);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
}
