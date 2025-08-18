package org.example.accountservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "firstname")
    @Size(min = 2, max = 16, message = "Firstname size must be between 2 and 16 symbols")
    private String firstname;

    @Column(name = "lastname")
    @Size(min = 2, max = 16, message = "Lastname size must be between 2 and 16 symbols")
    private String lastname;

    @Column(name = "password")
    @Size(min = 3, max = 16, message = "Password size must be between 3 and 16 symbols")
    private String password;

    @Column(name = "email")
    @Email(message = "Email syntax is not correct")
    @NotBlank(message = "Enter your email")
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(name = "reg_date")
    @CreationTimestamp
    private LocalDateTime accRegDate;

}
