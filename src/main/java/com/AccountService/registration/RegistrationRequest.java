package com.AccountService.registration;

import com.AccountService.registration.password.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

//@Data
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    @NotBlank(message = "Field 'firstname' cannot be empty")
    private final String firstname;
    @NotBlank(message = "Field 'lastname' cannot be empty")
    private final String lastname;
    @Email(message = "invalid email provided")
    private final String email;
    @ValidPassword
    private final String password;

}
