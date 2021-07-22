package com.otus.controllers;

import com.otus.model.Role;
import com.otus.model.Student;
import com.otus.service.DBRoleServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/role")
public class RoleController {

    private DBRoleServiceImpl dbRoleService;

    public RoleController(DBRoleServiceImpl dbRoleService) {
        this.dbRoleService = dbRoleService;
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public Role createRole(@RequestBody Role role) {
        dbRoleService.saveRole(role);
        return role;
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable(name = "id") long id) {
        return dbRoleService.getRole(id)
                .orElseThrow(() -> new RuntimeException("Role not found, id:" + id));
    }

    @GetMapping("/getAll")
    public List<Role> getRole() {
        return dbRoleService.findAll();
    }
}
