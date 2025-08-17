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
    private UUID id;

    @Column(name = "username")
    @Size(min = 3, max = 16, message = "Username size must be between 3 and 16 symbols")
    private String username;

    @Column(name = "password")
    @Size(min = 3, max = 16, message = "Password size must be between 3 and 16 symbols")
    private String password;

    @Column(name = "email")
    @Email(message = "Email syntax is not correct")
    @NotBlank(message = "Enter your email")
    private String email;

    @Column(name = "acc_role")
    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @Column(name = "acc_status")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(name = "acc_reg_date")
    @CreationTimestamp
    private LocalDateTime accRegDate;

}
