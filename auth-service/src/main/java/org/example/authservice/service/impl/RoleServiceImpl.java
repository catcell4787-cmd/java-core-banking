package org.example.authservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.authservice.enums.Role;
import org.example.authservice.model.dto.AccountDto;
import org.example.authservice.model.entity.Account;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.service.RoleService;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public Object getRoleForUser(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @Override
    public List<Account> findByRole(String role) {
        List<Account> accounts = new ArrayList<>();
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match("*")
                .count(1000)
                .build(); // count определяет количество возвращаемых элементов за итерацию
        Cursor<String> cursor = redisTemplate.scan(scanOptions); // начинаем перебор всех ключей
        while(cursor.hasNext()) {
            String key = cursor.next();
            Object value = redisTemplate.opsForValue().get(key); // получаем значение ключа
            if(value != null && value.toString().contains(role)) {
                Optional<Account> accountOptional = accountRepository.findByEmail(key);
                accountOptional.ifPresent(accounts::add);
            }
        }
        return accounts;
    }
}
