package ru.skypro.diplom.dto;

/**
 * Объект DTO для работы с комментариями. Взаимодействует с классом-моделью {@link ru.skypro.diplom.model.Comment}.
 *
 * {@code pk}: идентификатор комментария
 * {@code author}: идентификатор автора
 * {@code authorImage}: имя файла аватара
 * {@code authorFirstName}: имя автора
 * {@code createdAt}: время создания/изменения комментария
 * {@code text}: текст комментария
 */
public class CommentDTO {
    private int pk;
    private int author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;
    private String text;

    public CommentDTO() {}

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

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
