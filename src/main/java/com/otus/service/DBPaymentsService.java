package com.otus.service;

import com.otus.model.Payment;
import com.otus.model.Student;

import java.util.List;
import java.util.Optional;

public interface DBPaymentsService {

    Payment savePayment (Payment payment);

    Optional<Payment> getPaymentById(Long id);

    List<Payment> findAll();


}
