package com.otus.service;

import com.otus.model.Role;

import java.util.List;
import java.util.Optional;

public interface DBRoleService {

    Role saveRole(Role role);

    Optional<Role> getRole(long id);

    List<Role> findAll();
}
