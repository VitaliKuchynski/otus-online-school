package com.otus.repository;

import com.otus.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface StudentRepository extends CrudRepository<Student, Long> {

    Optional<Student> findByEmail(String email);
}
