package org.bank.authservice.common.account.dto;

import lombok.Data;

@Data
public class AccountCredentialsDTO {
    private String email;
    private String password;
}
