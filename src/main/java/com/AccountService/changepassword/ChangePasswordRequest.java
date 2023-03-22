package com.AccountService.changepassword;

import com.AccountService.registration.password.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ChangePasswordRequest {
    @ValidPassword
    private final String newPassword;
    private final String oldPassword;
    private final String email;

}
