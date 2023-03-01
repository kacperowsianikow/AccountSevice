package com.AccountService.edit;

import com.AccountService.account.Account;
import com.AccountService.account.IAccountRepository;
import com.AccountService.security.PasswordConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EditService {
    private final IAccountRepository IAccountRepository;
    private final PasswordConfig passwordConfig;

    public String changePassword(PasswordRequest passwordRequest) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Account> accountByEmail =
                IAccountRepository.findByEmail(userEmail);
        if (accountByEmail.isEmpty()) {
            return "wrong user";
        }

        String hashOfTheOldPassword = accountByEmail.get().getPassword();
        String newPassword = passwordRequest.getNewPassword();

        boolean isNewPasswordTheSame = passwordConfig.bCryptPasswordEncoder().matches(
                newPassword,
                hashOfTheOldPassword
        );

        if (isNewPasswordTheSame) {
            return "New password cannot be the same as the old one!";
        }

        String encodedPassword = passwordConfig.bCryptPasswordEncoder().encode(newPassword);

        IAccountRepository.changePassword(userEmail, encodedPassword);

        return "Changed password of the user: " + userEmail;
    }

}
