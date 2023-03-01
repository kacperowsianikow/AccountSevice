package com.AccountService.controller;

import com.AccountService.edit.EditService;
import com.AccountService.edit.PasswordRequest;
import com.AccountService.registration.RegistrationRequest;
import com.AccountService.registration.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AppController {
    private final RegistrationService registrationService;
    private final EditService editService;

    @PostMapping("/api/auth/signup")
    public String register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return registrationService.register(registrationRequest);
    }

    @PostMapping("/api/auth/changepass")
    public String changePassword(@Valid @RequestBody PasswordRequest passwordRequest) {
        return editService.changePassword(passwordRequest);
    }

    @GetMapping("/api/auth/signup/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping("/api/auth/admin/hello")
    public String helloAdmin() {
        return "Hello Admin";
    }

    @GetMapping("/api/auth/user/hello")
    public String helloUser() {
        return "Hello User";
    }

}
