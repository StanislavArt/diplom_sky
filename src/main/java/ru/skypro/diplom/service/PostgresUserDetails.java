package ru.skypro.diplom.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.diplom.enums.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PostgresUserDetails extends User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;
    private final String image;
    private final int id;

    public PostgresUserDetails(ru.skypro.diplom.model.User user) {
        super(user.getUsername(), user.getPassword(), List.of(user.getRole()));
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.image = user.getImage();
        this.id = user.getId();
    }

    public PostgresUserDetails(String username, String password, String firstName, String lastName, String phone, String image, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = username;
        this.image = image;
        this.id = 0;
    }

    public static PostgresUserDetails.PostgresUserDetailsBuilder builderPostgres() {
        return new PostgresUserDetails.PostgresUserDetailsBuilder();
    }

    public static final class PostgresUserDetailsBuilder {
        private String firstName;
        private String lastName;
        private String phone;
        private String image;
        private String username;
        private String password;
        private List<GrantedAuthority> authorities;
        private final PasswordEncoder encoder = new BCryptPasswordEncoder();

        private PostgresUserDetailsBuilder() {}

        public PostgresUserDetails.PostgresUserDetailsBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PostgresUserDetails.PostgresUserDetailsBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PostgresUserDetails.PostgresUserDetailsBuilder username(String username) {
            this.username = username;
            return this;
        }

        public PostgresUserDetails.PostgresUserDetailsBuilder password(String password) {
            this.password = encoder.encode(password);
            return this;
        }

        public PostgresUserDetails.PostgresUserDetailsBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public PostgresUserDetails.PostgresUserDetailsBuilder image(String image) {
            this.image = image;
            return this;
        }

        public PostgresUserDetails.PostgresUserDetailsBuilder role(String role) {
            switch (role) {
                case "ADMIN": this.authorities = List.of(Role.ADMIN); break;
                case "USER": this.authorities = List.of(Role.USER); break;
                default: this.authorities = new ArrayList<>();
            }
            return this;
        }

        public UserDetails build() {
            return new PostgresUserDetails(this.username, this.password, this.firstName, this.lastName, this.phone, this.image, this.authorities);
        }

    }

    public ru.skypro.diplom.model.User getUser() {
        ru.skypro.diplom.model.User userBD = new ru.skypro.diplom.model.User();
        userBD.setUsername(this.getUsername());
        userBD.setPassword(this.getPassword());
        userBD.setFirstName(getFirstName());
        userBD.setLastName(getLastName());
        userBD.setPhone(getPhone());
        userBD.setEmail(getEmail());
        userBD.setImage(getImage());
        userBD.setRole(getRole());
        userBD.setId(getId());
        return userBD;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }

    public Role getRole() {
        return (Role) getAuthorities().iterator().next();
    }

    public int getId() {
        return id;
    }
}
