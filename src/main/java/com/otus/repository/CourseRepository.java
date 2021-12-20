package com.otus.repository;


import com.otus.model.Course;
import com.otus.model.Staff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query(value = "SELECT c FROM courses c WHERE c.name = :name")
    Optional<Course> findCourseByName(@Param("name") String name);
}
