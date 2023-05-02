package ru.skypro.diplom.dto;

import ru.skypro.diplom.enums.Role;

public class LoadCred {
    private String username;
    private String password;
    private Role role;

    public LoadCred() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
