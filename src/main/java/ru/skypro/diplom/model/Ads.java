package ru.skypro.diplom.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Описывает модель: объявление.
 */
@Entity
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private int price;

    private String image;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ads", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    public Ads() {}

    public Ads(String title, String description, int price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ads ads = (Ads) o;
        return pk == ads.pk && price == ads.price && Objects.equals(title, ads.title) && author.equals(ads.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }

    @Override
    public String toString() {
        return "Ads{" +
                "pk=" + pk +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", price=" + price +
                '}';
    }
}
