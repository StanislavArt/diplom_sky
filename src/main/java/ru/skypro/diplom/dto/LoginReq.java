package ru.skypro.diplom.dto;


import java.util.Objects;

public class LoginReq {
    private String password;
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginReq loginReq = (LoginReq) o;
        return getPassword().equals(loginReq.getPassword()) && getUsername().equals(loginReq.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword(), getUsername());
    }

    @Override
    public String toString() {
        return "LoginReq{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
