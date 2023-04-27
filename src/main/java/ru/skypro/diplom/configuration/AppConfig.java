package ru.skypro.diplom.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import ru.skypro.diplom.dto.LoadCred;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class AppConfig {
    private final UserDetailsManager manager;
    private final UserRepository userRepository;

    public AppConfig(UserDetailsManager userDetailsManager, UserRepository userRepository) {
        this.manager = userDetailsManager;
        this.userRepository = userRepository;
    }

    /**
     * Загрузка пользователей из базы данных при запуске приложения
     */
    @PostConstruct
    public void loadCredentials() {
        //List<LoadCred> users = userRepository.getListOfUsernameAndPasswordAndRole();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (manager.userExists(user.getUsername())) { continue; }
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
            manager.createUser(userDetails);
        }

    }
}
