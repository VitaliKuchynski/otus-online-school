package com.otus.service;

import com.otus.model.Staff;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface DBStaffService {

    Staff saveEmployee(Staff staff);

    Optional<Staff> getEmployeeById(long id);

    List<Staff> findAll();
}
