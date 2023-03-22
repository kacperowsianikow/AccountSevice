package com.AccountService.service;

import com.AccountService.payment.IPaymentRepository;
import com.AccountService.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final IPaymentRepository iPaymentRepository;

    @Transactional
    public String addPayrolls(List<Payment> payments) {
//        Optional<Payment> findPayment =
//                iPaymentRepository.findByEmail(payments.get().getEmail());

        Set<Payment> verifiedPayments = new HashSet<>();
        for (Payment payment : payments) {
            if (!verifiedPayments.add(payment)) {
                return "There cannot be two identical payments!";
            }
        }

//        iPaymentRepository.save(payments);

        return "Added successfully";
    }

    public String editSalary(Payment payment) {
        return null;
    }

    public List<Payment> viewPayment() {
        return null;
    }

}
