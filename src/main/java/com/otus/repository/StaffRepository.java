package com.otus.repository;

import com.otus.model.Staff;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<Staff, Long> {
}
