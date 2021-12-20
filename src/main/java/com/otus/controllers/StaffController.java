package com.otus.controllers;

import com.otus.model.Staff;
import com.otus.service.DBStaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class StaffController {

    private DBStaffService dbStaffService;

    public StaffController(DBStaffService dbStaffService) {
        this.dbStaffService = dbStaffService;
    }

    @PostMapping(value = "save", consumes = "application/json", produces = "application/json")
    public Staff saveEmployee(@RequestBody Staff employee, @RequestParam(name = "roleID") Long roleId) {
        dbStaffService.saveEmployee(employee, roleId);
        return employee;
    }

    @GetMapping("/{id}")
    public Staff getEmployee(@PathVariable(value = "id") long id) {
        return dbStaffService.getEmployeeById(id).
                orElseThrow(() -> new RuntimeException("employee not found " + id));
    }

    @GetMapping("/all")
    public List<Staff> getEmployee() {
        return dbStaffService.findAll();
    }

    @PostMapping(value = "assign/role", consumes = "application/json", produces = "application/json")
    public Staff assignRoleToEmployee(@RequestParam(name = "employeeId") Long employeeId, @RequestParam(name = "roleID") Long roleId) {
        return dbStaffService.assignRole(employeeId, roleId);
    }

    @PostMapping(value = "assign/course", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> assignCourseToEmployee(@RequestParam(name = "employeeUsername") String employeeUsername, @RequestParam(name = "courseName") String courseName) {
        dbStaffService.assignCourse(employeeUsername, courseName);
        return ResponseEntity.ok().build();
    }
}
