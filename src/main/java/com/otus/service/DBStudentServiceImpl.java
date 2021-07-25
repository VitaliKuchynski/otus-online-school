package com.otus.service;

import com.otus.model.Student;
import com.otus.repository.CourseRepository;
import com.otus.repository.StudentRepository;
import com.otus.sessionManager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DBStudentServiceImpl implements DBStudentService {

    private static final Logger log = LoggerFactory.getLogger(DBStudentServiceImpl.class);

    private final TransactionManager transactionManager;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public DBStudentServiceImpl(TransactionManager transactionManager, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.transactionManager = transactionManager;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Student saveStudent(Student student) {
        return transactionManager.doInTransaction(() -> {
            if(studentRepository.findByEmail(student.getEmail()).isPresent()){
                throw new RuntimeException("Student with the same email already registered");
            }
            var savedStudent = studentRepository.save(student);
            log.info("saved client: {}", savedStudent);
            return savedStudent;
        });
    }

    @Override
    public Optional<Student> getStudent(long id) {
        var student = studentRepository.findById(id);
        log.info("student: {}", student);
        return student;
    }

    @Override
    public List<Student> findAll() {
        var studentList = new ArrayList<Student>();
        studentRepository.findAll().forEach(studentList::add);
        log.info("studentList: {}", studentList);
        return studentList;
    }

    @Override
    public Student assignCourse(Long studentId, Long courseId) {

        var getCourse = courseRepository.findById(courseId)
                .orElseThrow(()-> new RuntimeException("course not found " + courseId));

        var student = studentRepository.findById(studentId)
                .orElseThrow(()-> new RuntimeException("student not found " + studentId));

        var course = student.getCourses();
        course.add(getCourse);
        student.setCourses(course);

        return studentRepository.save(student);
    }
}
