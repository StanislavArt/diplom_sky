package ru.skypro.diplom.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private String authorImage;

    @ManyToOne
    @JoinColumn(name = "ads_id")
    private Ads ads;

    private int createdAt;

    private String authorFirstName;
    private String text;

    public Comment() {}

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return pk == comment.pk && author.equals(comment.author) && ads.equals(comment.ads) && Objects.equals(createdAt, comment.createdAt) && Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "pk=" + pk +
                ", author=" + author +
                ", ads=" + ads +
                ", createdAt='" + createdAt + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
