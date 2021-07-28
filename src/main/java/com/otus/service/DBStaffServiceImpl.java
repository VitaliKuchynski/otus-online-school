package com.otus.service;

import com.otus.model.Role;
import com.otus.model.Staff;
import com.otus.repository.RoleRepository;
import com.otus.repository.StaffRepository;
import com.otus.sessionManager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DBStaffServiceImpl implements DBStaffService, UserDetailsService {

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Staff employee = staffRepository.findByName(username).orElseThrow(() -> new RuntimeException("employee not found " + username));
       // var role = roleRepository.findById().orElseThrow(()-> new RuntimeException("role not found " + roleId));

        Collection<SimpleGrantedAuthority> authorityCollections = new ArrayList<>();
        var roles = employee.getRoles();

        for (Role role: roles) {
            var roleById = roleRepository.findById(role.getId()).orElseThrow(()-> new RuntimeException("role not found " + role.getId()));
            authorityCollections.add(new SimpleGrantedAuthority(roleById.getName()));
        }
        return new org.springframework.security.core.userdetails.User(employee.getEmail(), employee.getPassword(), authorityCollections);
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
