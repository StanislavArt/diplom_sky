package ru.skypro.diplom.dto;

/**
 * Объект DTO для работы с пользователями. Взаимодействует с классом-моделью {@link ru.skypro.diplom.model.User}.
 * Используется для изменения пароля пользователя.
 *
 * {@code currentPassword}: текущий пароль пользователя
 * {@code newPassword}: новый пароль пользователя
 */
public class NewPassword {
    public String currentPassword;
    public String newPassword;

    public NewPassword() {}

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
