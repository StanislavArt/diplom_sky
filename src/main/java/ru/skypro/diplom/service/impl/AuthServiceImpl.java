package ru.skypro.diplom.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.RegisterReq;
import ru.skypro.diplom.enums.Role;
import ru.skypro.diplom.repository.UserRepository;
import ru.skypro.diplom.service.AuthService;
import ru.skypro.diplom.service.PostgresUserDetails;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    public AuthServiceImpl(UserDetailsManager manager, UserRepository userRepository) {
        this.manager = manager;
        this.encoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        //String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        //return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
        return encoder.matches(password, encryptedPassword);
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .password(registerReq.getPassword())
//                        .username(registerReq.getUsername())
//                        .roles(role.name())
//                        .build()
                PostgresUserDetails.builderPostgres()
                    .username(registerReq.getUsername())
                    .password(registerReq.getPassword())
                    .role(role.name())
                    .firstName(registerReq.getFirstName())
                    .lastName(registerReq.getLastName())
                    .phone(registerReq.getPhone())
                    .build()
        );
//        UserDetails userDetails = manager.loadUserByUsername(registerReq.getUsername());
//
//        ru.skypro.diplom.model.User user = new ru.skypro.diplom.model.User();
//        user.setUsername(registerReq.getUsername());
//        user.setPassword(userDetails.getPassword());
//        user.setEmail(registerReq.getUsername());
//        user.setFirstName(registerReq.getFirstName());
//        user.setLastName(registerReq.getLastName());
//        user.setPhone(registerReq.getPhone());
//        user.setRole(role);
//        userRepository.save(user);

        return true;
    }
}
