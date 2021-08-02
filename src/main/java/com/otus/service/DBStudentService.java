package com.otus.service;

import com.otus.model.Student;

import java.util.List;
import java.util.Optional;

public interface DBStudentService {

    Student saveStudent(Student student);

    Optional<Student> getStudent(long id);

    List<Student> findAll();

    Student assignStudentToCourse(Long studentId, Long courseId);

    Student updateStudent(Student student, Long id);

}
