package org.example.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class AccountDto {

    private UUID id;

    @Email(message = "Email syntax is not correct")
    @NotBlank(message = "Enter your email")
    private String email;

    @NotBlank(message = "Enter your password")
    private String password;

}
