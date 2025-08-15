package org.example.accountservice.mapper;

import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);
    Account toEntity(AccountDto accountDto);
}
