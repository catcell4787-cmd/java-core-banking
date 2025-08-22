package org.example.authservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.authservice.role.AccountRole;
import org.example.authservice.role.AccountStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "Enter your firstname")
    private String firstname;
    @NotBlank(message = "Enter your lastname")
    private String lastname;
    @NotBlank(message = "Enter your password")
    private String password;

    @Email(message = "Email syntax is not correct")
    @NotBlank(message = "Enter your email")
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(name = "reg_date")
    @CreationTimestamp
    private LocalDateTime regDate;

}
