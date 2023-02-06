package com.AccountService.account;

import com.AccountService.security.AccountRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table
public class Account {
    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    public Account(String name,
                   String lastname,
                   String email,
                   String password,
                   AccountRole accountRole) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.accountRole = accountRole;
    }
}
