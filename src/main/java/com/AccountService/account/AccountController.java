package com.AccountService.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("api/empl/payment")
    public String test1() {
        return "empl/payment is accessed";
    }

    @GetMapping("api/admin/user")
    public String test2() {
        return "/admin/user is accessed";
    }

//    @PutMapping("api/acct/payments")
//
//
//    @PutMapping("api/admin/user/role")
//
//
//    @PostMapping("api/auth/signup")
//
//
//    @PostMapping("api/auth/changepass")
//
//
//    @PostMapping("api/acct/payments")
//
//
//    @DeleteMapping("api/admin/user")
}
