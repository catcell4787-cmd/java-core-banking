package org.bank.authservice.common.account.dto;

import lombok.Data;

@Data
public class AuthTokenDTO {
    private String token;
    private String refreshToken;
}
