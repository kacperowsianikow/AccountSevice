package com.AccountService.registration.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final IConfirmationTokenRepository IConfirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        IConfirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return IConfirmationTokenRepository.findByToken(token);
    }

    public Optional<ConfirmationToken> getTokenByAccountId(Long accountId) {
        return IConfirmationTokenRepository.findByAccountId(accountId);
    }

    public void setConfirmedAt(String token) {
        IConfirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public void updateToken(Long accountId,
                            LocalDateTime createdAt,
                            LocalDateTime expiresAt,
                            String token) {
        IConfirmationTokenRepository.updateToken(accountId, createdAt, expiresAt, token);
    }

}
