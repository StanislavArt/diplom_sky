package ru.skypro.diplom.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.RegisterReq;
import ru.skypro.diplom.enums.Role;
import ru.skypro.diplom.service.AuthService;
import ru.skypro.diplom.service.PostgresUserDetails;
import ru.skypro.diplom.service.PostgresUserDetailsManager;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserDetailsManager manager;

    public AuthServiceImpl(UserDetailsManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return ((PostgresUserDetailsManager) manager).getEncoder().matches(password, encryptedPasswordWithoutEncryptionType);
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(
                PostgresUserDetails.builderPostgres()
                    .username(registerReq.getUsername())
                    .password(registerReq.getPassword())
                    .role(role.name())
                    .firstName(registerReq.getFirstName())
                    .lastName(registerReq.getLastName())
                    .phone(registerReq.getPhone())
                    .build()
        );

        return true;
    }
}
