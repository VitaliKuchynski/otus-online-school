package com.otus.controllers;

import com.otus.model.Course;
import com.otus.service.DBCourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private DBCourseService dbCourseService;

    public CourseController(DBCourseService dbCourseService) {
        this.dbCourseService = dbCourseService;
    }

    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public Course courseSave(@RequestBody Course course) {
        dbCourseService.saveCourse(course);
        return course;
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable(value = "id") long id) {
        return dbCourseService.getCourse(id).
                orElseThrow(() -> new RuntimeException("course not found " + id));
    }

    @GetMapping("/all")
    public List<Course> getCourses() {
        return dbCourseService.findAll();
    }
}
