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

import com.university.entities.Professor;
import com.university.repositories.IProfessorRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.university.exceptions.ResourceNotFoundException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Api(description="API pour les opérations CRUD sur les professeurs.")
@RestController
@RequestMapping(value="/v1/professor")
public class ProfessorController {

	
	@Autowired
	private IProfessorRepository professorRepo;
	
	@ApiOperation(value = "Crée un professeur")
	@PostMapping(value="/create")
	public Professor createProfessor(@RequestBody Professor professor) {
		return professorRepo.save(professor);
		}
	
	@ApiOperation(value = "Met à jour un professeur grâce à son ID à condition que celui-ci soit dans la liste!")
	@PutMapping(value="/update/{id}")
	public Professor updateProfessor(@PathVariable(value = "id") Long idProfessor,  @RequestBody Professor professor) throws ResourceNotFoundException{
		
		
			Professor s = professorRepo.findById(idProfessor)
					.orElseThrow(() -> new ResourceNotFoundException("Professor not found :: " + idProfessor));
			if (s!=null) {
				professor.setIdProfessor(idProfessor);
				professor = professorRepo.save(professor);
			}
		
		return professor;
	}
	
	@ApiOperation(value = "Supprime un professeur grâce à son ID à condition que celui-ci soit dans la liste!")
	@DeleteMapping(value="/delete/{id}")
	public Map<String, Boolean> deleteProfessor (@PathVariable(value="id") Long idProfessor) throws ResourceNotFoundException{
		
			Professor s = professorRepo.findById(idProfessor)
					.orElseThrow(() -> new ResourceNotFoundException("Professor not found :: " + idProfessor));
			if (s!=null) {
				professorRepo.deleteById(idProfessor);
				Map<String, Boolean> response = new HashMap<>();
				  response.put("deleted", Boolean.TRUE);
				  return response;
			}
			else return null;
			
	}
	
	@ApiOperation(value = "Récupère la liste des professeurs!")
	@GetMapping(value="/all")
	public List<Professor> getAll(){
		return professorRepo.findAll();
		
	}
	
	@ApiOperation(value = "Récupère un professeur grâce à son ID à condition que celui-ci soit dans la liste!")
	 @GetMapping(value="/get/{id}")
	 public ResponseEntity<Professor> getProfessorById(@PathVariable(value = "id") Long idProfessor) throws ResourceNotFoundException {
		 if (idProfessor!=null) {
				Professor professor = professorRepo.findById(idProfessor)
						.orElseThrow(() -> new ResourceNotFoundException("Professor not found :: " + idProfessor));
				
			
	  return ResponseEntity.ok().body(professor);
	 }
		 else return null;
	 }
 
}


