package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.diplom.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

}
