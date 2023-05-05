package ru.skypro.diplom.dto;

/**
 * Объект DTO для работы с пользователями. Взаимодействует с классом-моделью {@link ru.skypro.diplom.model.User}.
 *
 * {@code id}: идентификатор пользователя
 * {@code firstName}: имя пользователя
 * {@code lastName}: фамилия пользователя
 * {@code email}: электронная почта пользователя
 * {@code phone}: телефон пользователя
 * {@code image}: имя файла аватара пользователя
 */
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String image;

    public UserDTO() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
