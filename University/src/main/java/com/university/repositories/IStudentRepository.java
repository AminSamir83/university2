package com.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.university.entities.Student;


@Repository
public interface IStudentRepository extends JpaRepository<Student,Long>{

}
