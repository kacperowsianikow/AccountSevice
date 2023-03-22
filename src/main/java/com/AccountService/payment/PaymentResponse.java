package com.AccountService.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentResponse {
    private final String firstname;
    private final String lastname;
    private final LocalDate period;
    private final String salary;

}
