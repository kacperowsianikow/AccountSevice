package com.AccountService.registration;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/api/auth/signup")
    public String register(@Valid @RequestBody RegistrationRequest request) {
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
    public String helloUser() {
        return "Hello User";
    }

}
