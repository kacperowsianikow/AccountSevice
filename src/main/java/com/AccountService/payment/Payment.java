package com.AccountService.payment;

import com.AccountService.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table
@NoArgsConstructor
public class Payment {
    @Id
    @SequenceGenerator(
            name = "payment_sequence",
            sequenceName = "payment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_sequence"
    )
    private Long id;
    @NotBlank(message = "Field 'employee' cannot be empty")
    private String email;
    @NotBlank(message = "Field 'employee' cannot be empty")
    private String period;
    @PositiveOrZero
    @NotBlank(message = "Field 'employee' cannot be empty")
    private Long salary;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "account_id"
    )
    private Account account;

    public Payment(String email,
                   String period,
                   Long salary,
                   Account account) {
        this.email = email;
        this.period = period;
        this.salary = salary;
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payment payment = (Payment) o;
        return id != null && Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
