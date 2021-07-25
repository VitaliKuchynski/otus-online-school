package com.otus.controllers;

import com.otus.model.Payment;
import com.otus.model.Role;
import com.otus.service.DBPaymentsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private DBPaymentsService dbPaymentsService;

    public PaymentController(DBPaymentsService dbPaymentsService) {
        this.dbPaymentsService = dbPaymentsService;
    }

    @PostMapping(value = "save")
    //returns timestamp as a null
    public Payment savePayment(@RequestBody Payment payment) {
       var savedPayment  = dbPaymentsService.savePayment(payment);
        return savedPayment;
    }
    @GetMapping("/{id}")
    public Payment getRole(@PathVariable(name = "id") long id) {
        return dbPaymentsService.getPaymentById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found, id:" + id));
    }
}
