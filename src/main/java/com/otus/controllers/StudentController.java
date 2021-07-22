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


    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public Student createStudent(@RequestBody Student student) {
        dbStudentService.saveStudent(student);
        return student; //new RedirectView("/", true);
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


    @PostMapping(value = "/createStudent/{id}", consumes = "application/json", produces = "application/json")
    public Student assignCourse(@RequestBody Student student, @PathVariable(name = "id") long id) {

        var getCourse = dbCourseService.getCourse(id)
                .orElseThrow(()-> new RuntimeException("course not found " + id));
            student.getCourses().add(getCourse);
            return dbStudentService.saveStudent(student);
}
}