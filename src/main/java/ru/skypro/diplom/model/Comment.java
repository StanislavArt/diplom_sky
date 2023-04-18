package ru.skypro.diplom.model;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int author;

    private int pk;

    private String createdAt;

    private String text;
}
