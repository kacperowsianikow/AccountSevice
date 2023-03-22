package com.AccountService.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PaymentRequest {
    @NotBlank(message = "Field 'employee' cannot be empty")
    private String email;
    @NotBlank(message = "Field 'employee' cannot be empty")
    private String period;
    @PositiveOrZero
    @NotBlank(message = "Field 'employee' cannot be empty")
    private Long salary;

}
