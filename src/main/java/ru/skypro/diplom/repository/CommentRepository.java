package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.diplom.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

}
