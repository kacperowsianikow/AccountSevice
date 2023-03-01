package com.AccountService.registration;

import com.AccountService.account.Account;
import com.AccountService.account.AccountRepository;
import com.AccountService.account.AccountRole;
import com.AccountService.email.EmailSender;
import com.AccountService.registration.token.ConfirmationToken;
import com.AccountService.registration.token.ConfirmationTokenService;
import com.AccountService.security.PasswordConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final EmailSender emailSender;
    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordConfig passwordConfig;
    private final AccountRepository accountRepository;
    private static final String CONFIRMATION_LINK = "http://localhost:8080/api/auth/signup/confirm?token=";
    private static final int EXPIRATION_TIME = 15;

    public String register(RegistrationRequest request) {
        Optional<Account> accountByEmail =
                accountRepository.findByEmail(request.getEmail());

        if (accountByEmail.isEmpty()) {
            boolean isEmailValid = emailValidator.test(request.getEmail());
            if (!isEmailValid) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST
                );
            }

            String encodedPassword =
                    passwordConfig.bCryptPasswordEncoder().encode(request.getPassword());

            Account account = new Account(
                    request.getFirstname(),
                    request.getLastname(),
                    request.getEmail(),
                    encodedPassword,
                    Collections.singletonList(AccountRole.ROLE_USER)
            );

            accountRepository.save(account);

            String token = generateToken(account);

            String link = CONFIRMATION_LINK + token;

            emailSender.send(request.getEmail(), Email.createEmail(request.getFirstname(), link));

            return token;
        }

        ConfirmationToken confirmationToken = confirmationTokenService
                .getTokenByAccountId(accountByEmail.get().getId())
                .orElseThrow(
                        () -> new IllegalStateException("token not found")
                );

        if (confirmationToken.getConfirmedAt() == null) {
            String token = updateToken(accountByEmail.get().getId());

            String link = CONFIRMATION_LINK + token;

            emailSender.send(request.getEmail(), Email.createEmail(request.getFirstname(), link));

            return token;
        }

        return "email already exists";

    }

    private String updateToken(Long id) {
        String generatedToken = UUID.randomUUID().toString();

        confirmationTokenService.updateToken(
                id,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(EXPIRATION_TIME),
                generatedToken
        );

        return generatedToken;
    }

    private String generateToken(Account account) {
        String generatedToken = UUID.randomUUID().toString();

        ConfirmationToken token = new ConfirmationToken(
                generatedToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(EXPIRATION_TIME),
                account
        );

        confirmationTokenService.saveConfirmationToken(token);

        return generatedToken;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(
                        () -> new IllegalStateException("token not found")
                );

        if (confirmationToken.getConfirmedAt() != null) {
            return "email already confirmed";
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            return "validation link has expired";
        }

        confirmationTokenService.setConfirmedAt(token);

        accountRepository.enableAccount(
                confirmationToken.getAccount().getEmail()
        );

        return "confirmed";
    }

}
