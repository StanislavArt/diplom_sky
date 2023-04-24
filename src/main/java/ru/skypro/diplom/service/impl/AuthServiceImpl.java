package ru.skypro.diplom.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.RegisterReq;
import ru.skypro.diplom.enums.Role;
import ru.skypro.diplom.service.AuthService;
import ru.skypro.diplom.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;

    private final UserService userService;

    public AuthServiceImpl(UserDetailsManager manager, UserService userService) {
        this.manager = manager;
        this.encoder = new BCryptPasswordEncoder();
        this.userService = userService;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }

        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);

        ru.skypro.diplom.model.User user = userService.findByCredentials(userName, password);
        if (user == null) {
            user = new ru.skypro.diplom.model.User();
            user.setUsername(userName);
            user.setPassword(password);
        }
        userService.setCurrentUser(user);

        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(role.name())
                        .build()
        );
        return true;
    }
}
