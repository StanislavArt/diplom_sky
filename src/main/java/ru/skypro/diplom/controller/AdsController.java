package ru.skypro.diplom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.*;
import ru.skypro.diplom.model.Comment;
import ru.skypro.diplom.service.AdsService;
import ru.skypro.diplom.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("ads")
@CrossOrigin(value = "http://192.168.99.100:3000")
//@CrossOrigin(value = "http://192.168.0.152:3000")
//@CrossOrigin(value = "http://localhost:3000")
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
    public ResponseEntity<ResponseAds> addAds(@RequestPart(value = "properties") CreateAds createAds, @RequestPart(value = "image") MultipartFile file) {
        ResponseAds ads = adsService.addAds(createAds, file);
        if (ads == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAds() {
        ResponseWrapperAds responseWrapperAds = adsService.getAds();
        return ResponseEntity.ok(responseWrapperAds);
    }

    @GetMapping("/{adPk}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable int adPk) {
        ResponseWrapperComment responseWrapperComment = commentService.getComments(adPk);
        if (responseWrapperComment == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(responseWrapperComment);
    }

    @PostMapping("/{adPk}/comments")
    public ResponseEntity<CommentDTO> addComments(@RequestBody CommentDTO commentDTO, @PathVariable int adPk) {
        CommentDTO comment = commentService.addComment(commentDTO, adPk);
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
    public ResponseEntity<ResponseAds> updateAds(@PathVariable int id, @RequestBody CreateAds createAds) {
        ResponseAds responseAds = adsService.updateAds(id, createAds);
        if (responseAds == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(responseAds);
    }

    @GetMapping("/{adPk}/comments/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable int id, @PathVariable int adPk) {
        Comment comment = commentService.getComment(id, adPk);
        if (comment == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{adPk}/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable int id, @PathVariable int adPk) {
        if (!commentService.deleteComments(id, adPk)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PatchMapping("/{adPk}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComments(@PathVariable int id, @PathVariable int adPk, @RequestBody CommentDTO commentDTO) {
        CommentDTO comment = commentService.updateComments(id, adPk, commentDTO);
        if (comment == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(comment);
    }

    @PatchMapping("/{adPk}/image")
    public ResponseEntity<List<String>> updateAdsImage(@PathVariable int adPk, @RequestPart(value = "image") MultipartFile file) {
        List<String> images = adsService.updateAdsImage(adPk, file);
        return ResponseEntity.ok(images);
    }
}
