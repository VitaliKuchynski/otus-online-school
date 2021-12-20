package com.otus.service;

import com.otus.model.Course;
import com.otus.model.Payment;
import com.otus.model.Student;
import com.otus.repository.CourseRepository;
import com.otus.repository.PaymentRepository;
import com.otus.sessionManager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DBPaymentServiceImpl implements DBPaymentsService {

    private static final Logger log = LoggerFactory.getLogger(DBPaymentServiceImpl.class);

    private final TransactionManager transactionManager;
    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;
    private final DBStudentService dbStudentService;

    public DBPaymentServiceImpl(TransactionManager transactionManager, PaymentRepository paymentRepository, CourseRepository courseRepository, DBStudentService dbStudentService) {
        this.transactionManager = transactionManager;
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
        this.dbStudentService = dbStudentService;
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

    @Override
    public List<Payment> findAll() {
        var paymentList = new ArrayList<Payment>();
        paymentRepository.findAll().forEach(paymentList::add);
        log.info("paymentList: {}", paymentList);
        return paymentList;
    }

    @Override
    public Payment pay(Long studentId, Long courseId) {
        return transactionManager.doInTransaction(() -> {
            Optional<Student> student = dbStudentService.getStudent(studentId);
            Optional<Course> course = courseRepository.findById(courseId);

            if (student.isPresent() && course.isPresent()) {
                Payment payment = new Payment(course.get(), student.get());
                paymentRepository.save(payment);
                student.get().getCourses().add(course.get());
                dbStudentService.updateStudent(student.get(), studentId);
                log.info("payment processed: {} ", payment.getId());
                return payment;
            }

            log.info("payment failed:");
            throw new RuntimeException("Payment failed");
        });

    }
}
