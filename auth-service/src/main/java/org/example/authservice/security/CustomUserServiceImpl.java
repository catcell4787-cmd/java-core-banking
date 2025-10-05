package org.example.authservice.security;

import lombok.RequiredArgsConstructor;
import org.example.authservice.model.entity.Account;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.repository.RedisRoleRepository;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final RedisRoleRepository redisRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByEmail(username);
        if  (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            Set<Object> roles = redisRoleRepository.getRolesForUser(username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (Object role : roles) {
                authorities.add(new SimpleGrantedAuthority((String) role));
            }
            return new User(account.getEmail(), account.getPassword(), authorities);
        }
        throw new UsernameNotFoundException(username);

//        return accountRepository.findByEmail(username)
//                .map(user -> User.builder()
//                        .username(user.getEmail())
//                        .password(user.getPassword())
//                        .authorities(user.getRole().stream().map(Role::getRole).map(SimpleGrantedAuthority::new)
//                                .toList())
//                        .build())
//                .orElseThrow(() -> new UsernameNotFoundException(username));

    }
}
