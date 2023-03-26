package com.AccountService.controller;

import com.AccountService.service.ChangePasswordService;
import com.AccountService.changepassword.ChangePasswordRequest;
import com.AccountService.payment.Payment;
import com.AccountService.service.PaymentService;
import com.AccountService.registration.RegistrationRequest;
import com.AccountService.service.RegistrationService;
import com.AccountService.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppController {
    private final RegistrationService registrationService;
    private final ChangePasswordService changePasswordService;
    private final PaymentService paymentService;

    @PostMapping("/api/auth/signup")
    public String register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return registrationService.register(registrationRequest);
    }

    @PostMapping("/api/auth/changepass")
    public String changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        return changePasswordService.changePassword(changePasswordRequest);
    }

    @PostMapping("api/auth/accountant/payments")
    public String addPayrolls(@Valid @RequestBody List<Payment> payments) {
        return paymentService.addPayrolls(payments);
    }

    @PutMapping("api/auth/accountant/payments")
    public String editSalary(@Valid @RequestBody Payment payment) {
        return paymentService.editSalary(payment);
    }

    @GetMapping("/api/auth/signup/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping("api/auth/user/payment")
    public List<Payment> viewPayment() {
        return paymentService.viewPayment();
    }

    ///////// For testing roles
    @GetMapping("/api/auth/admin/hello")
    public String helloAdmin() {
        return "Hello Admin";
    }

    @GetMapping("/api/auth/user/hello")
    public String helloUser() {
        return "Hello User";
    }

}
