package ru.skypro.diplom.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.UserDetailsManager;
import ru.skypro.diplom.service.PostgresUserDetails;

import javax.annotation.PostConstruct;

@Configuration
public class InitConfig {

    private final UserDetailsManager manager;

    public InitConfig(UserDetailsManager manager) {
        this.manager = manager;
    }

    @PostConstruct
    void init() {
        manager.createUser(PostgresUserDetails.builderPostgres()
                .username("user@gmail.com")
                .password("password")
                .role("USER")
                .build());

        manager.createUser(PostgresUserDetails.builderPostgres()
                .username("admin@gmail.com")
                .password("admin")
                .role("ADMIN")
                .firstName("Николя")
                .build());
    }

}
