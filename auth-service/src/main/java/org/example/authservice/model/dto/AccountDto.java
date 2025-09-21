package org.example.authservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.authservice.enums.AccountRole;
import org.example.authservice.enums.AccountStatus;

import java.util.UUID;

@Data
public class AccountDto {

    private UUID id;

    @Email(message = "Email syntax is not correct")
    @NotBlank(message = "Enter your email")
    private String email;

    @NotBlank(message = "Enter your password")
    private String password;

    private AccountRole role;

    private AccountStatus status;

}
