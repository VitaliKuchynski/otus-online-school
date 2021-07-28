package com.otus.repository;

import com.otus.model.Staff;
import com.otus.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StaffRepository extends CrudRepository<Staff, Long> {

    Optional<Staff> findByEmail(String email);

    Optional<Staff> findByName(String username);
}
