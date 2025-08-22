package org.example.authservice.dto;

import lombok.Data;

@Data
public class AccountCredentialsDto {
    private String email;
    private String password;
}
