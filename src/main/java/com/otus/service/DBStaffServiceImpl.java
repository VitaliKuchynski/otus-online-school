package com.otus.service;

import com.otus.model.Staff;
import com.otus.repository.StaffRepository;
import com.otus.sessionManager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DBStaffServiceImpl implements DBStaffService{

    private static final Logger log = LoggerFactory.getLogger(DBStaffServiceImpl.class);


    private final TransactionManager transactionManager;
    private final StaffRepository staffRepository;

    public DBStaffServiceImpl(TransactionManager transactionManager, StaffRepository staffRepository) {
        this.transactionManager = transactionManager;
        this.staffRepository = staffRepository;
    }

    @Override
    public Staff saveEmployee(Staff staff) {
        return transactionManager.doInTransaction(() -> {
            var savedEmployee = staffRepository.save(staff);
            log.info("saved employee: {}", savedEmployee);
            return savedEmployee;
        });
    }

    @Override
    public Optional<Staff> getEmployeeById(long id) {
        var employee = staffRepository.findById(id);
        log.info("role: {}", employee);
        return Optional.empty();
    }

    @Override
    public List<Staff> findAll() {
        var staffList = new ArrayList<Staff>();
        staffRepository.findAll().forEach(staffList::add);
        log.info("staffList: {} ", staffList);
        return staffList;
    }
}
