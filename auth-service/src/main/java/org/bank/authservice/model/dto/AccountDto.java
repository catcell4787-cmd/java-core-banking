package org.bank.authservice.model.dto;

import lombok.Data;
import org.bank.authservice.enums.Status;


@Data
public class AccountDto {

    private String email;
    private String role;
    private Status status;
    private CardsDto cardsDto;

}
