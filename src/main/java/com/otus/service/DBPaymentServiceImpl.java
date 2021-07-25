package com.otus.service;

import com.otus.model.Payment;
import com.otus.repository.PaymentRepository;
import com.otus.sessionManager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DBPaymentServiceImpl implements DBPaymentsService {

    private static final Logger log = LoggerFactory.getLogger(DBPaymentServiceImpl.class);

    private final TransactionManager transactionManager;
    private final PaymentRepository paymentRepository;

    public DBPaymentServiceImpl(TransactionManager transactionManager, PaymentRepository paymentRepository) {
        this.transactionManager = transactionManager;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment savePayment(Payment payment) {
        return transactionManager.doInTransaction(() -> {
            var savedPayment = paymentRepository.save(payment);
            log.info("saved payment: {}", savedPayment);
            return savedPayment;
        });
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        var payment = paymentRepository.findById(id);
        log.info("payment: {}", payment);
        return payment;
    }
}
