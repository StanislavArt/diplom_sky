package ru.skypro.diplom.dto;

/**
 * Объект DTO для работы с объявлениями.
 *
 * {@code title}: название объявления
 * {@code authorFirstName}: имя автора
 * {@code authorLastName}: фамилия автора
 * {@code description}: текст объявления
 * {@code email}: электронная почта автора
 * {@code phone}: телефон автора
 * {@code pk}: идентификатор объявления
 * {@code price}: цена товара
 * {@code image}: имя файла картинки
 */
public class FullAds {
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String phone;
    private int pk;
    private int price;
    private String image;

    public FullAds() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
