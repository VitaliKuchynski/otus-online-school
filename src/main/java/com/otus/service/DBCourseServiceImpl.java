package com.otus.service;

import com.otus.model.Course;
import com.otus.repository.CourseRepository;
import com.otus.sessionManager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DBCourseServiceImpl implements DBCourseService {

    private static final Logger log = LoggerFactory.getLogger(DBCourseServiceImpl.class);

    private final TransactionManager transactionManager;
    private final CourseRepository courseRepository;

    public DBCourseServiceImpl(TransactionManager transactionManager, CourseRepository courseRepository) {
        this.transactionManager = transactionManager;
        this.courseRepository = courseRepository;
    }

    @Override
    public Course saveCourse(Course course) {
        return transactionManager.doInTransaction(() -> {
            var savedLecture = courseRepository.save(course);
            log.info("saved course: {}", savedLecture);
            return savedLecture;
        });
    }

    @Override
    public Optional<Course> getCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        log.info("course: {}", course);
        return course;
    }

    @Override
    public List<Course> findAll() {
        var courseList = new ArrayList<Course>();
        courseRepository.findAll().forEach(courseList::add);
        log.info("courseList:{}", courseList);
        return courseList;
    }

    @Override
    public Optional<Course> findCourseByName(String name) {
        Optional<Course> course = courseRepository.findCourseByName(name);
        log.info("course: {}", course);
        return course;
    }
}
