package ru.skypro.diplom.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.CommentDTO;
import ru.skypro.diplom.dto.ResponseWrapperComment;
import ru.skypro.diplom.model.Ads;
import ru.skypro.diplom.model.Comment;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.repository.AdsRepository;
import ru.skypro.diplom.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, UserService userService, AdsRepository adsRepository) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.userService = userService;
    }

    public ResponseWrapperComment getComments(int adsPk) {
        Ads ads = adsRepository.findById(adsPk).orElse(null);
        if (ads == null) { return null; }
        List<Comment> comments = commentRepository.findAllByAds(ads);
        return getResponseWrapperCommentDTO(comments);
    }

    public CommentDTO addComment(CommentDTO commentDTO, int adPk, Authentication auth) {
        User user = userService.getUserFromAuthentication(auth);
        if (user == null) { return null; }

        Ads ads = adsRepository.findById(adPk).orElse(null);
        if (ads == null) { return null; }

        Comment comment = new Comment();
        comment.setAds(ads);
        comment.setAuthor(user);
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setText(commentDTO.getText());
        comment = commentRepository.save(comment);
        return getCommentDTO(comment);
    }

    public boolean deleteComments(int id, int adPk) {
        Ads ads = adsRepository.findById(adPk).orElse(null);
        if (ads == null) { return false; }

        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) { return false; }

        commentRepository.deleteById(id);
        return true;
    }

    public CommentDTO updateComments(int id, int adPk, CommentDTO commentDTO) {
        Ads ads = adsRepository.findById(adPk).orElse(null);
        if (ads == null) { return null; }

        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) { return null; }

        comment.setText(commentDTO.getText());
        comment.setCreatedAt(System.currentTimeMillis());
        comment = commentRepository.save(comment);
        return getCommentDTO(comment);
    }

    private ResponseWrapperComment getResponseWrapperCommentDTO(List<Comment> comments) {
        ResponseWrapperComment responseWrapperComment = new ResponseWrapperComment();
        responseWrapperComment.setCount(comments.size());

        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment item : comments) {
            commentDTOList.add(getCommentDTO(item));
        }
        responseWrapperComment.setResults(commentDTOList);
        return responseWrapperComment;
    }

    private CommentDTO getCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        if (comment == null) { return commentDTO; }
        commentDTO.setPk(comment.getPk());
        commentDTO.setAuthor(comment.getAuthor().getId());
        commentDTO.setAuthorImage("/users/" + comment.getAuthor().getId() + "/image");
        commentDTO.setAuthorFirstName(comment.getAuthor().getFirstName());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setText(comment.getText());
        return commentDTO;
    }
}
