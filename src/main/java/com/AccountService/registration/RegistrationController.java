package com.AccountService.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/api/auth/signup")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
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
    @PreAuthorize("hasRole('USER')")
    public String helloUser() {
        return "Hello User";
    }

}
