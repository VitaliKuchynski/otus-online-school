package com.otus.service;

import com.otus.model.Payment;

import java.util.List;
import java.util.Optional;

public interface DBPaymentsService {

    Payment savePayment(Payment payment);

    Optional<Payment> getPaymentById(Long id);

    List<Payment> findAll();

    Payment pay(Long studentId, Long courseId);
}
