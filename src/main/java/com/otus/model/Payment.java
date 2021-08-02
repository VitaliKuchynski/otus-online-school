package com.otus.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "local_date_time", insertable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

    @ManyToOne
    private Course course;

    @ManyToOne
    @JsonIgnore
    private Student student;

    public Payment() {
    }

    public Payment(Course course, Student student) {
        this.course = course;
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
