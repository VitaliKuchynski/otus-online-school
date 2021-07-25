package com.otus.service;

import com.otus.model.Payment;

import java.util.Optional;

public interface DBPaymentsService {

    Payment savePayment (Payment payment);
    Optional<Payment> getPaymentById(Long id);


}
