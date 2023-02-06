package com.AccountService.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountPermission {
    SIGNUP("auth/signup"),
    CHANGE_PASSWORD("auth/changepass"),
    PAYMENT_READ("empl/payment"),
    PAYMENT_POST("acct/payments"),
    PAYMENT_PUT("acct/payments"),
    ACCOUNT_USER_ROLE_PUT("admin/user/role"),
    ACCOUNT_USER_INFO_DELETE("admin/user"),
    ACCOUNT_USER_INFO_READ("admin/user");

    private final String permission;
}
