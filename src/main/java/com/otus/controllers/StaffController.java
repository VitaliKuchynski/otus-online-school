package com.otus.controllers;

import com.otus.model.Course;
import com.otus.model.Staff;
import com.otus.service.DBStaffService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class StaffController {

    private DBStaffService dbStaffService;

    public StaffController(DBStaffService dbStaffService) {
        this.dbStaffService = dbStaffService;
    }

    @PostMapping(value = "save",consumes = "application/json", produces = "application/json")
    public Staff saveEmployee(@RequestBody Staff employee) {
        dbStaffService.saveEmployee(employee);
        return employee;
    }

    @GetMapping("/{id}")
    public Staff getEmployee(@PathVariable(value = "id") long id) {
        return dbStaffService.getEmployeeById(id).
                orElseThrow(()-> new RuntimeException("employee not found " + id));
    }

    @GetMapping("/all")
    public List<Staff> getEmployee() {
        return dbStaffService.findAll();
    }


}
