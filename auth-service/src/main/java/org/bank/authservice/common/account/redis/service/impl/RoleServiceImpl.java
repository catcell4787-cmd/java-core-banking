package org.bank.authservice.common.account.redis.service.impl;

import lombok.RequiredArgsConstructor;
import org.bank.authservice.common.account.redis.service.RoleService;
import org.bank.authservice.enums.Role;
import org.bank.authservice.common.account.entity.Account;
import org.bank.authservice.common.account.repository.AccountRepository;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final AccountRepository accountRepository;


    @Override
    public void saveRole(String userName, Role role) {
        redisTemplate.opsForValue().set(userName, role);
    }

    @Override
    public String getRoleForUser(String username) {
        return Objects.requireNonNull(redisTemplate.opsForValue().get(username)).toString();
    }

    @Override
    public List<Account> findByRole(String role) {
        List<Account> accounts = new ArrayList<>();
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match("*")
                .count(1000)
                .build(); // count определяет количество возвращаемых элементов за итерацию
        try (Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            while (cursor.hasNext()) {
                String key = cursor.next();
                Object value = redisTemplate.opsForValue().get(key); // получаем значение ключа
                if (value != null && value.toString().contains(role)) {
                    Optional<Account> accountOptional = accountRepository.findByEmail(key);
                    accountOptional.ifPresent(accounts::add);
                }
            }
        } // начинаем перебор всех ключей
        return accounts;
    }

    @Override
    public void deleteRole(String role) {
        redisTemplate.delete(role);
    }
}
