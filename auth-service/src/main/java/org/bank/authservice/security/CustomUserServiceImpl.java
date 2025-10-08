package org.bank.authservice.security;

import lombok.RequiredArgsConstructor;
import org.bank.authservice.exception.GlobalExceptionHandler;
import org.bank.authservice.model.entity.Account;
import org.bank.authservice.repository.AccountRepository;
import org.bank.authservice.service.RoleService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final RoleService roleService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByEmail(username);
        if  (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (!account.isEnabled()) {
                throw new GlobalExceptionHandler.AccountStatusException("Account is not enabled");
            }
            String role = roleService.getRoleForUser(username).toString();
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));
            return new User(account.getEmail(), account.getPassword(), authorities);
        }
        throw new UsernameNotFoundException(username);
    }
}
