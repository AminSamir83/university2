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

import com.university.entities.Course;
import com.university.repositories.ICourseRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.university.exceptions.ResourceNotFoundException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Api(description="API pour les opérations CRUD sur les cours.")
@RestController
@RequestMapping(value="/v1/course")
public class CourseController {

	
	@Autowired
	private ICourseRepository courseRepo;
	
	@ApiOperation(value = "Crée un cours")
	@PostMapping(value="/create")
	public Course createCourse(@RequestBody Course course) {
		return courseRepo.save(course);
		}
	
	@ApiOperation(value = "Met à jour un cours grâce à son ID à condition que celui-ci soit dans la liste!")
	@PutMapping(value="/update/{id}")
	public Course updateCourse(@PathVariable(value = "id") Long idCourse,  @RequestBody Course course) throws ResourceNotFoundException{
		
		
			Course s = courseRepo.findById(idCourse)
					.orElseThrow(() -> new ResourceNotFoundException("Course not found :: " + idCourse));
			if (s!=null) {
				course.setIdCourse(idCourse);
				course = courseRepo.save(course);
			}
		
		return course;
	}
	
	@ApiOperation(value = "Supprime un cours grâce à son ID à condition que celui-ci soit dans la liste!")
	@DeleteMapping(value="/delete/{id}")
	public Map<String, Boolean> deleteCourse (@PathVariable(value="id") Long idCourse) throws ResourceNotFoundException{
		
			Course s = courseRepo.findById(idCourse)
					.orElseThrow(() -> new ResourceNotFoundException("Course not found :: " + idCourse));
			if (s!=null) {
				courseRepo.deleteById(idCourse);
				Map<String, Boolean> response = new HashMap<>();
				  response.put("deleted", Boolean.TRUE);
				  return response;
			}
			else return null;
			
	}
	
	@ApiOperation(value = "Récupère la liste des cours!")
	@GetMapping(value="/all")
	public List<Course> getAll(){
		return courseRepo.findAll();
		
	}
	
	@ApiOperation(value = "Récupère un cours grâce à son ID à condition que celui-ci soit dans la liste!")
	 @GetMapping(value="/get/{id}")
	 public ResponseEntity<Course> getCourseById(@PathVariable(value = "id") Long idCourse) throws ResourceNotFoundException {
		 if (idCourse!=null) {
				Course course = courseRepo.findById(idCourse)
						.orElseThrow(() -> new ResourceNotFoundException("Course not found :: " + idCourse));
				
			
	  return ResponseEntity.ok().body(course);
	 }
		 else return null;
	 }
 
}

