package ru.skypro.diplom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.*;
import ru.skypro.diplom.model.Ads;
import ru.skypro.diplom.model.Comment;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.service.AdsService;
import ru.skypro.diplom.service.CommentService;

import java.io.IOException;

@RestController
@RequestMapping("ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {
    private final AdsService adsService;
    private final CommentService commentService;

    public AdsController(AdsService adsService, CommentService commentService) {
        this.adsService = adsService;
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        ResponseWrapperAds responseWrapperAds = adsService.getAllAds();
        return ResponseEntity.ok(responseWrapperAds);
    }

    @PostMapping
    public ResponseEntity<Ads> addAds(@RequestPart(value = "properties") CreateAds createAds, @RequestPart(value = "image") MultipartFile file) throws IOException {
        Ads ads = adsService.addAds(createAds, file.getBytes());
        if (ads == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAds() {
        ResponseWrapperAds responseWrapperAds = adsService.getAds();
        if (responseWrapperAds == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(responseWrapperAds);
    }

    @GetMapping("/{adPk}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable Integer adPk) {
        ResponseWrapperComment responseWrapperComment = commentService.getComments(adPk);
        if (responseWrapperComment == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(responseWrapperComment);
    }

    @PostMapping("/{adPk}/comments")
    public ResponseEntity<Comment> addComments(@RequestBody CreateComment createComment, @PathVariable int adPk) {
        Comment comment = commentService.addComment(createComment, adPk);
        if (comment == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAds> getFullAd(@PathVariable int id) {
        FullAds fullAds = adsService.getFullAd(id);
        if (fullAds == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(fullAds);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds(@PathVariable int id) {
        adsService.removeAds(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ads> updateAds(@PathVariable int id, @RequestBody CreateAds createAds) {
        Ads ads = adsService.updateAds(id, createAds);
        if (ads == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/{adPK}/comments/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable int id, @PathVariable int adPk) {
        Comment comment = commentService.getComment(id, adPk);
        if (comment == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{adPk}/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable int id, @PathVariable int adPk) {
        commentService.deleteComments(id, adPk);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adPk}/comments/{id}")
    public ResponseEntity<Comment> updateComments(@PathVariable int id, @PathVariable int adPk, @RequestBody CreateComment createComment) {
        Comment comment = commentService.updateComments(id, adPk, createComment);
        if (comment == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(comment);
    }

    @PatchMapping("/image/{id}")
    public ResponseEntity<?> updateImage(@PathVariable int id, @RequestPart(value = "image") MultipartFile file) {

        return ResponseEntity.ok().build();
    }

}
