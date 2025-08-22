package org.example.authservice.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.authservice.role.AccountRole;
import org.example.authservice.role.AccountStatus;

import java.util.UUID;

@Data
public class AccountDto {

    private UUID id;

    @Email(message = "Email syntax is not correct")
    @NotBlank(message = "Enter your email")
    private String email;

    @NotBlank(message = "Enter your firstname")
    private String firstname;

    @NotBlank(message = "Enter your lastname")
    private String lastname;

    @NotBlank(message = "Enter your password")
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
