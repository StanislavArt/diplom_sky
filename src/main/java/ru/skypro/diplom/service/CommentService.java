package ru.skypro.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.skypro.diplom.dto.CreateComment;
import ru.skypro.diplom.dto.ResponseWrapperComment;
import ru.skypro.diplom.model.Ads;
import ru.skypro.diplom.model.Comment;
import ru.skypro.diplom.repository.AdsRepository;
import ru.skypro.diplom.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@SessionScope
public class CommentService {

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(CommentService.class);

    public CommentService(CommentRepository commentRepository, UserService userService, AdsRepository adsRepository) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.userService = userService;
    }

    public ResponseWrapperComment getComments(int adsPk) {
        Ads ads = adsRepository.findById(adsPk).orElse(null);
        if (ads == null) return null;

        List<Comment> comments = commentRepository.findAllByAds(ads);
        if (comments.isEmpty()) return null;
        return getResponseWrapperCommentDTO(comments);
    }

    public Comment addComment(CreateComment createComment, int adPk) {
        // проверка авторизации
        // ...

        Ads ads = adsRepository.findById(adPk).orElse(null);
        if (ads == null) return null;

        Comment comment = createCommentDTO(createComment);
        comment.setAds(ads);
        comment.setAuthor(userService.getCurrentUser());
        comment = commentRepository.save(comment);
        if (comment == null) logger.error("Write error into database (function 'addComment()'");
        return comment;
    }

    public Comment getComment(int id, int adPk) {
        Ads ads = adsRepository.findById(adPk).orElse(null);
        if (ads == null) return null;
        Comment comment = commentRepository.findById(id).orElse(null);
        return comment;
    }

    public void deleteComments(int id, int adPk) {
        // проверка авторизации
        // ...

        Ads ads = adsRepository.findById(adPk).orElse(null);
        // NOT_FOUND
        // if (ads == null) return null;

        Comment comment = commentRepository.findById(id).orElse(null);
        // NOT_FOUND
        //if (comment == null)

        commentRepository.deleteById(id);
    }

    public Comment updateComments(int id, int adPk, CreateComment createComment) {
        // проверка авторизации
        // ...

        Ads ads = adsRepository.findById(adPk).orElse(null);
        if (ads == null) return null;

        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) return null;

        comment.setText(createComment.getText());
        comment.setCreatedAt(System.currentTimeMillis());
        comment = commentRepository.save(comment);
        return comment;
    }

    private ResponseWrapperComment getResponseWrapperCommentDTO(List<Comment> comments) {
        ResponseWrapperComment responseWrapperComment = new ResponseWrapperComment();
        responseWrapperComment.setCount(comments.size());
        responseWrapperComment.setResults(comments);
        return responseWrapperComment;
    }

    private Comment createCommentDTO(CreateComment createComment) {
        Comment comment = new Comment();
        comment.setText(createComment.getText());
        comment.setCreatedAt(System.currentTimeMillis());
        return comment;
    }
}
