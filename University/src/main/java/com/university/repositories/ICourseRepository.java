package com.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.university.entities.Course;


@Repository
public interface ICourseRepository extends JpaRepository<Course, Long>{

}
