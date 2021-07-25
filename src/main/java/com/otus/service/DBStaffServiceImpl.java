package com.otus.service;

import com.otus.model.Staff;
import com.otus.repository.RoleRepository;
import com.otus.repository.StaffRepository;
import com.otus.sessionManager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DBStaffServiceImpl implements DBStaffService{

    private static final Logger log = LoggerFactory.getLogger(DBStaffServiceImpl.class);


    private final TransactionManager transactionManager;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;

    public DBStaffServiceImpl(TransactionManager transactionManager, StaffRepository staffRepository, RoleRepository roleRepository) {
        this.transactionManager = transactionManager;
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Staff saveEmployee(Staff staff, Long roleId) {

       var role = roleRepository.findById(roleId).orElseThrow(()-> new RuntimeException("role not found " + roleId));

        return transactionManager.doInTransaction(() -> {
            if (staffRepository.findByEmail(staff.getEmail()).isPresent()) {
                throw new RuntimeException("Employee with the same email already registered");
            }
            staff.setRoles(Collections.singletonList(role));

            var savedEmployee = staffRepository.save(staff);
            log.info("saved employee: {}", savedEmployee);
            return savedEmployee;
        });
    }

    @Override
    public Optional<Staff> getEmployeeById(long id) {
        var employee = staffRepository.findById(id);
        log.info("employee: {}", employee);
        return employee;
    }

    @Override
    public List<Staff> findAll() {
        var staffList = new ArrayList<Staff>();
        staffRepository.findAll().forEach(staffList::add);
        log.info("staffList: {} ", staffList);
        return staffList;
    }

    @Override
    public Staff assignRole(Long employeeID, Long roleId) {
            var employee = staffRepository.findById(employeeID).orElseThrow(()-> new RuntimeException("role not found " + employeeID));
            var role = roleRepository.findById(roleId).orElseThrow(()-> new RuntimeException("role not found " + roleId));

            var roles =  employee.getRoles();
            roles.add(role);
            employee.setRoles(roles);
            var savedEmployee = staffRepository.save(employee);
            log.info("updated employee role: {} ", savedEmployee);
            return savedEmployee;
    }
}
