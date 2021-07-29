package com.otus.repository;

import com.otus.model.Staff;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StaffRepository extends CrudRepository<Staff, Long> {

    Optional<Staff> findEmployeeByUsername(String username);
}
