package org.bank.authservice.model.dto;

import lombok.Data;


@Data
public class AccountDto {

    private String email;
    private String role;
    private CardDto cards;
    private boolean enabled;
}
