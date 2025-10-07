package org.bank.authservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bank.authservice.enums.Status;
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

    @Column(name = "registered")
    @CreationTimestamp
    private LocalDateTime registered;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

}
