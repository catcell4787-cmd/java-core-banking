package org.bank.authservice.model.dto;

import lombok.Data;

@Data
public class AuthTokenDto {
    private String token;
    private String refreshToken;
}
