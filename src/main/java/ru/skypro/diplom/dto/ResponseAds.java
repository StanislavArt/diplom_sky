package ru.skypro.diplom.dto;

/**
 * Объект DTO для работы с объявлениями. Взаимодействует с классом-моделью {@link ru.skypro.diplom.model.Ads}
 *
 * {@code pk}: идентификатор объявления
 * {@code author}: идентификатор автора объявления
 * {@code image}: имя файла картинки объявления
 * {@code price}: цена товара
 * {@code title}: название объявления
 */
public class ResponseAds {
    private int pk;
    private int author;
    private String image;
    private int price;
    private String title;

    public ResponseAds() {}

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
