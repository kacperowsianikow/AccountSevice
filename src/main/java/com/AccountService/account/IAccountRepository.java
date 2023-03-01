package com.AccountService.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(
            "UPDATE Account a " +
                    "SET a.isEnabled = TRUE " +
                    "WHERE a.email =?1"
    )
    void enableAccount(String email);

}
