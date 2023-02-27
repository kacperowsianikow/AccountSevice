package com.AccountService.registration.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public Optional<ConfirmationToken> getTokenByAccountId(Long accountId) {
        return confirmationTokenRepository.findByAccountId(accountId);
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public void updateToken(Long accountId,
                            LocalDateTime createdAt,
                            LocalDateTime expiresAt,
                            String token) {
        confirmationTokenRepository.updateToken(accountId, createdAt, expiresAt, token);
    }

}
