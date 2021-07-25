package com.otus.controllers;

import com.otus.model.Role;
import com.otus.service.DBRoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/role")
public class RoleController {

    private DBRoleService dbRoleService;

    public RoleController(DBRoleService dbRoleService) {
        this.dbRoleService = dbRoleService;
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public Role createRole(@RequestBody Role role) {
        var savedRole = dbRoleService.saveRole(role);
        return savedRole;
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
