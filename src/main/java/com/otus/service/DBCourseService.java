package com.otus.service;

import com.otus.model.Course;

import java.util.List;
import java.util.Optional;

public interface DBCourseService {

    Course saveCourse(Course course);

    Optional<Course> getCourse(long id);

    List<Course> findAll();
}
