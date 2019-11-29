package com.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.university.entities.Professor;


@Repository
public interface IProfessorRepository extends JpaRepository<Professor, Long> {

}
