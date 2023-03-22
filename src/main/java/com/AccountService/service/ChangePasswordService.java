package com.AccountService.service;

import com.AccountService.account.Account;
import com.AccountService.account.IAccountRepository;
import com.AccountService.changepassword.ChangePasswordRequest;
import com.AccountService.security.PasswordConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {
    private final IAccountRepository iAccountRepository;
    private final PasswordConfig passwordConfig;

    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Account> accountByEmail =
                iAccountRepository.findByEmail(userEmail);
        if (accountByEmail.isEmpty()) {
            return "wrong user";
        }

        String hashOfTheOldPassword = accountByEmail.get().getPassword();
        String newPassword = changePasswordRequest.getNewPassword();

        boolean isNewPasswordTheSame = passwordConfig.bCryptPasswordEncoder().matches(
                newPassword,
                hashOfTheOldPassword
        );

        if (isNewPasswordTheSame) {
            return "New password cannot be the same as the old one!";
        }

        String encodedPassword = passwordConfig.bCryptPasswordEncoder().encode(newPassword);

        iAccountRepository.changePassword(userEmail, encodedPassword);

        return "Changed password of the user: " + userEmail;
    }

}
