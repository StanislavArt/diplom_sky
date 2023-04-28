package ru.skypro.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.repository.UserRepository;

public class PostgresUserDetailsManager implements UserDetailsManager {
    private final Logger logger = LoggerFactory.getLogger(PostgresUserDetailsManager.class);
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    public PostgresUserDetailsManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserDetails user) {
        saveUser(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        saveUser(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) { return; }
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            logger.warn("Текущий пользователь не аутентифицирован");
            return;
        }
        String username = currentUser.getName();
        User userBD = userRepository.findUserByUsername(username).orElse(null);
        if (userBD == null) {
            logger.error("Пользователь '{}' не найден в базе данных", username);
            return;
        }
        String encryptedOldPassword = encoder.encode(oldPassword);
        if (!encryptedOldPassword.equals(userBD.getPassword())) {
            logger.error("Пользователь неверно указал старый пароль");
            return;
        }
        userBD.setPassword(encoder.encode(newPassword));
        userRepository.save(userBD);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existUsername(username) > 0;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userBD = userRepository.findUserByUsername(username).orElse(null);
        if (userBD == null) { throw new UsernameNotFoundException("Пользователь не найден в базе. Имя пользователя: " + username); }
        return new PostgresUserDetails(userBD);
    }

    private void saveUser(UserDetails user) {
        if (user == null) {
            logger.error("Ошибка: переданный в функцию пользователь равен null");
            return;
        }

        User userBD = ((PostgresUserDetails) user).getUser();
        if (userExists(userBD.getUsername()) && userBD.getId() == 0) {
            logger.warn("Пользователь с ником '{}' уже существует в базе", userBD.getUsername());
            return;
        }
        userRepository.save(userBD);
    }
}
