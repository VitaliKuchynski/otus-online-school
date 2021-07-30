package com.otus.service;

import com.otus.model.Role;
import com.otus.model.Staff;
import com.otus.model.Student;
import com.otus.repository.CourseRepository;
import com.otus.repository.RoleRepository;
import com.otus.repository.StaffRepository;
import com.otus.repository.StudentRepository;
import com.otus.sessionManager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DBStaffServiceImpl implements DBStaffService, UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(DBStaffServiceImpl.class);


    private final TransactionManager transactionManager;
    private final CourseRepository courseRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    public DBStaffServiceImpl(TransactionManager transactionManager, CourseRepository courseRepository, StaffRepository staffRepository,
                              RoleRepository roleRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.transactionManager = transactionManager;
        this.courseRepository = courseRepository;
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override//need to be refactored one entity will e created for user
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        boolean isEmployeePresent = staffRepository.findEmployeeByUsername(username).isPresent();
        boolean isStudentPresent = studentRepository.findStudentByUsername(username).isPresent();

        if (isEmployeePresent && !isStudentPresent) {
            Staff employee = staffRepository.findEmployeeByUsername(username)
                    .orElseThrow(() -> new RuntimeException("employee not found " + username));
            log.info("employee retrieved : {} " + username);

            Collection<SimpleGrantedAuthority> authorityCollections = new ArrayList<>();
            var employeeRoles = employee.getRoles();

            for (Role role : employeeRoles) {
                var roleById = roleRepository.findById(role.getId())
                        .orElseThrow(() -> new RuntimeException("role not found " + role.getId()));
                authorityCollections.add(new SimpleGrantedAuthority(roleById.getName()));
            }
            return new org.springframework.security.core.userdetails.User(employee.getUsername(),
                    employee.getPassword(), authorityCollections);

        } else if (!isEmployeePresent && isStudentPresent) {
            Student student = studentRepository.findStudentByUsername(username)
                    .orElseThrow(() -> new RuntimeException("student not found " + username));
            log.info("student retrieved : {} " + username);

            Collection<SimpleGrantedAuthority> authorityCollections = new ArrayList<>();
            var studentRoles = student.getRoles();

            for (Role role : studentRoles) {
                var roleById = roleRepository.findById(role.getId())
                        .orElseThrow(() -> new RuntimeException("role not found " + role.getId()));
                authorityCollections.add(new SimpleGrantedAuthority(roleById.getName()));
            }
            return new org.springframework.security.core.userdetails.User(student.getUsername(),
                    student.getPassword(), authorityCollections);

        } else {
            log.info("student username: {} " + username);
            log.info("employee username: {} " + username);
            throw new RuntimeException("No user found or username matched for employee and student");
        }
    }


    @Override
    public Staff saveEmployee(Staff staff, Long roleId) {

       var role = roleRepository.findById(roleId)
               .orElseThrow(()-> new RuntimeException("role not found " + roleId));

        return transactionManager.doInTransaction(() -> {
            if (staffRepository.findEmployeeByUsername(staff.getUsername()).isPresent()) {
                throw new RuntimeException("Employee with the same username already registered");
            }
            staff.setPassword(passwordEncoder.encode(staff.getPassword()));
            staff.setRoles(Collections.singleton(role));

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
            var employee = staffRepository.findById(employeeID)
                    .orElseThrow(()-> new RuntimeException("role not found " + employeeID));
            var role = roleRepository.findById(roleId)
                    .orElseThrow(()-> new RuntimeException("role not found " + roleId));

            var roles =  employee.getRoles();
            roles.add(role);
            employee.setRoles(roles);
            var savedEmployee = staffRepository.save(employee);
            log.info("updated employee role: {} ", savedEmployee);
            return savedEmployee;
    }

    @Override
    public void assignCourse(String employeeUsername, String courseName) {
       var employee = staffRepository.findEmployeeByUsername(employeeUsername)
                .orElseThrow(()-> new RuntimeException("Employee not found " + employeeUsername));

       var course = courseRepository.findCourseByName(courseName)
                .orElseThrow(()-> new RuntimeException("Course not found " + courseName));

       employee.getCourses().add(course);
       staffRepository.save(employee);
    }
}
