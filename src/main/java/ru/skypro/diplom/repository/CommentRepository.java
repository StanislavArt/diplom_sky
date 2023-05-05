package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.diplom.model.Ads;
import ru.skypro.diplom.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findAllByAds(Ads ads);

    @Modifying
    @Query(value = "delete from comment c where c.pk=:id", nativeQuery = true)
    void deleteComment(@Param("id") int id);

    @Modifying
    @Query(value = "delete from comment c where c.ads_id=:ads_id", nativeQuery = true)
    void deleteAllCommentsByAds(@Param("ads_id") int adsId);

}
