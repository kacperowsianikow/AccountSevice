package com.AccountService.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;

import java.util.Set;

import static com.AccountService.security.AccountPermission.*;

@AllArgsConstructor
public enum AccountRole {
    ANONYMOUS(Sets.newHashSet(
            SIGNUP)
    ),
    USER(Sets.newHashSet(
            SIGNUP,
            CHANGE_PASSWORD,
            PAYMENT_READ)
    ),
    ACCOUNTANT(Sets.newHashSet(
            SIGNUP,
            CHANGE_PASSWORD,
            PAYMENT_READ,
            PAYMENT_POST,
            PAYMENT_PUT)
    ),
    ADMINISTRATOR(Sets.newHashSet(
            SIGNUP,
            CHANGE_PASSWORD,
            ACCOUNT_USER_ROLE_PUT,
            ACCOUNT_USER_INFO_READ,
            ACCOUNT_USER_INFO_DELETE)
    );

    private final Set<AccountPermission> permissions;
}
