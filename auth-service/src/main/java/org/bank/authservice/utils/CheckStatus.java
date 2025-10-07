package org.bank.authservice.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.bank.authservice.enums.Status;
import org.bank.authservice.model.entity.Account;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckStatus {
    @Before("execution(* *(org.bank.authservice.model.entity.Account+)) && args(account, ..)")
    public void checkAccountStatus(JoinPoint joinPoint, Account account) {
        if (account.getStatus() != Status.ACTIVE) {
            throw new IllegalStateException("Account must be in ACTIVE state to proceed with operation.");
        }
    }
}
