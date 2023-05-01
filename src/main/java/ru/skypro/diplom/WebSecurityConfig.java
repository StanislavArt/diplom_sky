package ru.skypro.diplom;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.diplom.service.PostgresUserDetails;
import ru.skypro.diplom.service.PostgresUserDetailsManager;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login", "/register"
    };

    @Bean
    public PostgresUserDetailsManager userDetailsService() {
        PostgresUserDetailsManager manager = PostgresUserDetailsManager.build(new BCryptPasswordEncoder());

//        manager.createUser(PostgresUserDetails.builderPostgres()
//                .username("user@gmail.com")
//                .password("password")
//                .role("USER")
//                .build());
//
//        manager.createUser(PostgresUserDetails.builderPostgres()
//                .username("admin@gmail.com")
//                .password("admin")
//                .role("ADMIN")
//                .firstName("Николя")
//                .build());
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authz) ->
                        authz
                                .mvcMatchers(AUTH_WHITELIST).permitAll()
                                .mvcMatchers("/ads/**", "/users/**").authenticated()

                )
                .cors().and()
                .httpBasic(withDefaults());
        return http.build();
    }
}

