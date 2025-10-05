package org.bank.authservice.model.dto;

import lombok.Data;

@Data
public class AccountCredentialsDto {
    private String email;
    private String password;
}
