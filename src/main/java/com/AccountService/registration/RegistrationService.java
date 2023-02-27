package com.AccountService.registration;

import com.AccountService.account.Account;
import com.AccountService.account.AccountRepository;
import com.AccountService.account.AccountRole;
import com.AccountService.email.EmailSender;
import com.AccountService.registration.token.ConfirmationToken;
import com.AccountService.registration.token.ConfirmationTokenRepository;
import com.AccountService.registration.token.ConfirmationTokenService;
import com.AccountService.security.PasswordConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
                accountRepository.findByEmailIgnoreCase(request.getEmail());

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
                    AccountRole.USER
            );

            accountRepository.save(account);

            String token = generateToken(account);

            String link = CONFIRMATION_LINK + token;

            emailSender.send(request.getEmail(), buildEmail(request.getFirstname(), link));

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

            emailSender.send(request.getEmail(), buildEmail(request.getFirstname(), link));

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

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in " + EXPIRATION_TIME + " minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}