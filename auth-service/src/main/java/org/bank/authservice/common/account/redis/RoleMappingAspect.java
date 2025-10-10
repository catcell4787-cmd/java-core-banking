package org.bank.authservice.common.account.redis;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.bank.authservice.common.account.dto.AccountDTO;
import org.bank.authservice.common.account.redis.service.RoleService;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleMappingAspect {

    private final RoleService roleService;

    @AfterReturning(
            pointcut = "execution(* *(..)) && args(accountDTO)",
            returning = "accountDTO",
            argNames = "joinPoint,accountDTO")
    public void setRoleInUserDto(JoinPoint joinPoint, AccountDTO accountDTO) {
        if (accountDTO != null) {
            // Получаем роль из Redis по ID пользователя
            String role = roleService.getRoleForUser(accountDTO.getEmail()).toString();
            accountDTO.setRole(role); // Устанавливаем роль в DTO
        }
        System.out.println("Mapped role for object returned by method: "
                + joinPoint.getSignature().getDeclaringType()
                + "." + joinPoint.getSignature().getName());
    }

}
