package ru.skypro.diplom.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private int price;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ads", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;


}
