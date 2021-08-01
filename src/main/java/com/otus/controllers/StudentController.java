package com.otus.controllers;

import com.otus.model.Student;
import com.otus.service.DBCourseService;
import com.otus.service.DBStudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private DBStudentService dbStudentService;
    private DBCourseService dbCourseService;


    public StudentController(DBStudentService dbStudentService, DBCourseService dbCourseService) {
        this.dbStudentService = dbStudentService;
        this.dbCourseService = dbCourseService;
    }


    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public Student saveStudent(@RequestBody Student student) {
        dbStudentService.saveStudent(student);
        return student;
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable(name = "id") long id) {
    return dbStudentService.getStudent(id)
            .orElseThrow(() -> new RuntimeException("Student not found, id:" + id));
    }

    @GetMapping("/all")
    public List<Student> getStudents() {
        return dbStudentService.findAll();
    }


    @PostMapping(value = "/assign/course", consumes = "application/json", produces = "application/json")
    public Student assignCourse(@RequestParam (name = "studentId") Long studentId, @RequestParam (name = "courseId") Long courseId) {
        return dbStudentService.assignStudentToCourse(studentId, courseId);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public Student saveStudent(@RequestBody Student student,@PathVariable(name = "id") Long id) {
        return dbStudentService.updateStudent(student, id);
    }
}