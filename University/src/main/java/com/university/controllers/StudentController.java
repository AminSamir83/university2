package com.university.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.university.entities.Student;
import com.university.repositories.IStudentRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.university.exceptions.ResourceNotFoundException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Api(description="API pour les opérations CRUD sur les étudiants.")
@RestController
@RequestMapping(value="/v1/student")
public class StudentController {

	
	@Autowired
	private IStudentRepository studentRepo;
	
	@ApiOperation(value = "Crée un étudiant")
	@PostMapping(value="/create")
	public Student createStudent(@RequestBody Student student) {
		return studentRepo.save(student);
		}
	
	@ApiOperation(value = "Met à jour un étudiant grâce à son ID à condition que celui-ci soit dans la liste!")
	@PutMapping(value="/update/{id}")
	public Student updateStudent(@PathVariable(value = "id") Long idStudent,  @RequestBody Student student) throws ResourceNotFoundException{
		
		
			Student s = studentRepo.findById(idStudent)
					.orElseThrow(() -> new ResourceNotFoundException("Student not found :: " + idStudent));
			if (s!=null) {
				student.setIdStudent(idStudent);
				student = studentRepo.save(student);
			}
		
		return student;
	}
	
	@ApiOperation(value = "Supprime un étudiant grâce à son ID à condition que celui-ci soit dans la liste!")
	@DeleteMapping(value="/delete/{id}")
	public Map<String, Boolean> deleteStudent (@PathVariable(value="id") Long idStudent) throws ResourceNotFoundException{
		
			Student s = studentRepo.findById(idStudent)
					.orElseThrow(() -> new ResourceNotFoundException("Student not found :: " + idStudent));
			if (s!=null) {
				studentRepo.deleteById(idStudent);
				Map<String, Boolean> response = new HashMap<>();
				  response.put("deleted", Boolean.TRUE);
				  return response;
			}
			else return null;
			
	}
	
	@ApiOperation(value = "Récupère la liste des étudiants!")
	@GetMapping(value="/all")
	public List<Student> getAll(){
		return studentRepo.findAll();
		
	}
	
	@ApiOperation(value = "Récupère un étudiant grâce à son ID à condition que celui-ci soit dans la liste!")
	 @GetMapping(value="/get/{id}")
	 public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Long idStudent) throws ResourceNotFoundException {
		 if (idStudent!=null) {
				Student student = studentRepo.findById(idStudent)
						.orElseThrow(() -> new ResourceNotFoundException("Student not found :: " + idStudent));
				
			
	  return ResponseEntity.ok().body(student);
	 }
		 else return null;
	 }
 
}

