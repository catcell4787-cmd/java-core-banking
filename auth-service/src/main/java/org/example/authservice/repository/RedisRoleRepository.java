package org.example.authservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RedisRoleRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    // Сохранение списка ролей для конкретного пользователя
    public void setRolesForUser(String username, Set<Object> roles) {
        redisTemplate.opsForSet().add(getRoleKey(username), roles.toArray());
    }

    // Получаем список ролей пользователя
    public Set<Object> getRolesForUser(String username) {
        return redisTemplate.opsForSet().members(getRoleKey(username));
    }

    // Удаляем записи о ролях пользователя
    public void deleteRolesForUser(String username) {
        redisTemplate.delete(getRoleKey(username));
    }

    private String getRoleKey(String username) {
        return username;
    }

    public Set<Object> roles(String role) {
        Set<Object> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }

}
