package com.otus.service;

import com.otus.model.Staff;

import java.util.List;
import java.util.Optional;

public interface DBStaffService {

    Staff saveEmployee(Staff staff, Long id);

    Optional<Staff> getEmployeeById(long id);

    List<Staff> findAll();

    Staff assignRole(Long employeeID, Long RoleId);

    void assignCourse(String employeeUsername, String courseName);
}
