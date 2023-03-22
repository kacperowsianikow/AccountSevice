package com.AccountService.registration.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final IConfirmationTokenRepository iConfirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        iConfirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return iConfirmationTokenRepository.findByToken(token);
    }

    public Optional<ConfirmationToken> getTokenByAccountId(Long accountId) {
        return iConfirmationTokenRepository.findByAccountId(accountId);
    }

    public void setConfirmedAt(String token) {
        iConfirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public void updateToken(Long accountId,
                            LocalDateTime createdAt,
                            LocalDateTime expiresAt,
                            String token) {
        iConfirmationTokenRepository.updateToken(accountId, createdAt, expiresAt, token);
    }

}
