package org.example.authservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.authservice.enums.AccountStatus;
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

    @NotBlank(message = "Enter your password")
    @Column(name = "password")
    private String password;

    @Email(message = "Email syntax is not correct")
    @NotBlank(message = "Enter your email")
    @Column(name = "email")
    private String email;

    @Column(name = "reg_date")
    @CreationTimestamp
    private LocalDateTime regDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role")
    private Role role;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

}
