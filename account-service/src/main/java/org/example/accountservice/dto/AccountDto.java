package org.example.accountservice.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
