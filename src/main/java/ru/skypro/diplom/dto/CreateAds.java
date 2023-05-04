package ru.skypro.diplom.dto;

/**
 * Объект DTO для работы с объявлениями. Взаимодействует с классом-моделью {@link ru.skypro.diplom.model.Ads}
 *
 * {@code title}: название объявления
 * {@code description}: текст объявления
 * {@code price}: цена товара, представленного в объявлении
 */
public class CreateAds {

    private String title;
    private String description;
    private int price;

    public CreateAds() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
