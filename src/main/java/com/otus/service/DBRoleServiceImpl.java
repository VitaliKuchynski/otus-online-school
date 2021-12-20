package com.otus.service;

import com.otus.model.Role;
import com.otus.repository.RoleRepository;
import com.otus.sessionManager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DBRoleServiceImpl implements DBRoleService {
    private static final Logger log = LoggerFactory.getLogger(DBRoleServiceImpl.class);


    private final RoleRepository roleRepository;
    private final TransactionManager transactionManager;

    public DBRoleServiceImpl(RoleRepository roleRepository, TransactionManager transactionManager) {
        this.roleRepository = roleRepository;
        this.transactionManager = transactionManager;
    }


    @Override
    public Role saveRole(Role role) {
        return transactionManager.doInTransaction(() -> {
            var savedRole = roleRepository.save(role);
            log.info("saved role: {}", savedRole);
            return savedRole;
        });
    }

    @Override
    public Optional<Role> getRole(long id) {
        var role = roleRepository.findById(id);
        log.info("role: {}", role);
        return role;
    }

    @Override
    public List<Role> findAll() {
        var roleList = new ArrayList<Role>();
        roleRepository.findAll().forEach(roleList::add);
        log.info("roleList: {}", roleList);
        return roleList;
    }
}
