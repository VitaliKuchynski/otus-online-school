package com.otus.service;

import com.otus.model.Student;
import com.otus.repository.CourseRepository;
import com.otus.repository.RoleRepository;
import com.otus.repository.StudentRepository;
import com.otus.sessionManager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DBStudentServiceImpl implements DBStudentService {

    private static final Logger log = LoggerFactory.getLogger(DBStudentServiceImpl.class);

    private final TransactionManager transactionManager;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DBStudentServiceImpl(TransactionManager transactionManager, StudentRepository studentRepository, CourseRepository courseRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.transactionManager = transactionManager;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Student saveStudent(Student student) {
        return transactionManager.doInTransaction(() -> {
            if (studentRepository.findStudentByUsername(student.getUsername()).isPresent()) {
                throw new RuntimeException("Student with the same username already registered");
            }
            var defaultRole = roleRepository.findById(2L).orElseThrow(() -> new RuntimeException("Role not found, id:" + 2));

            student.setPassword(passwordEncoder.encode(student.getPassword()));
            student.setRoles(Collections.singleton(defaultRole));

            var savedStudent = studentRepository.save(student);
            log.info("saved student: {}", savedStudent);
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
    public Student assignStudentToCourse(Long studentId, Long courseId) {

        var getCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("course not found " + courseId));

        var student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("student not found " + studentId));

        var course = student.getCourses();
        course.add(getCourse);
        student.setCourses(course);
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student, Long id) {

        return transactionManager.doInTransaction(() -> {

            if (studentRepository.findById(id).isPresent()) {

                var newStudent = studentRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Student not found, id:" + id));
                newStudent.setUsername(student.getUsername());
                newStudent.setName(student.getName());
                newStudent.setEmail(student.getEmail());
                newStudent.setAddress(student.getAddress());
                newStudent.setPhone(student.getPhone());
                newStudent.setCardNumber(student.getCardNumber());
                newStudent.setCourses(student.getCourses());
                newStudent.setRoles(student.getRoles());
                newStudent.setPayments(student.getPayments());
                var savedStudent = studentRepository.save(newStudent);

                if (studentRepository.findById(savedStudent.getId()).isPresent()) {
                    log.info("updated student: {}", savedStudent);
                    return savedStudent;
                } else
                    throw new RuntimeException("Student not updated, name:" + savedStudent);
            }
            throw new RuntimeException("Student not found, id:" + id);
        });
    }
}
