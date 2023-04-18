package ru.skypro.diplom.enums;

public enum Role {
    USER("user"),
    ADMIN("admin");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
