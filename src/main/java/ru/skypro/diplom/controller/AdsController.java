package ru.skypro.diplom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.*;
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
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseAds> addAds(@RequestPart(value = "properties") CreateAds createAds, @RequestPart(value = "image") MultipartFile file, Authentication auth) {
        ResponseAds ads = adsService.addAds(createAds, file, auth);
        if (ads == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAds(Authentication auth) {
        return ResponseEntity.ok(adsService.getAds(auth));
    }

    @GetMapping("/{adPk}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable int adPk) {
        ResponseWrapperComment responseWrapperComment = commentService.getComments(adPk);
        return ResponseEntity.ok(responseWrapperComment);
    }

    @PostMapping("/{adPk}/comments")
    public ResponseEntity<CommentDTO> addComments(@RequestBody CommentDTO commentDTO, @PathVariable int adPk, Authentication auth) {
        CommentDTO comment = commentService.addComment(commentDTO, adPk, auth);
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
    public ResponseEntity<Void> removeAds(@PathVariable int id, Authentication auth) {
        adsService.removeAds(id, auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseAds> updateAds(@PathVariable int id, @RequestBody CreateAds createAds, Authentication auth) {
        ResponseAds responseAds = adsService.updateAds(id, createAds, auth);
        if (responseAds == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(responseAds);
    }

    @DeleteMapping("/{adPk}/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable int id, @PathVariable int adPk, Authentication auth) {
        if (!commentService.deleteComments(id, adPk, auth)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PatchMapping("/{adPk}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComments(@PathVariable int id, @PathVariable int adPk, @RequestBody CommentDTO commentDTO, Authentication auth) {
        CommentDTO comment = commentService.updateComments(id, adPk, commentDTO, auth);
        if (comment == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(comment);
    }

    @PatchMapping(value = "/{adPk}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> updateAdsImage(@PathVariable int adPk, @RequestPart(value = "image") MultipartFile file) {
        List<String> images = adsService.updateAdsImage(adPk, file);
        return ResponseEntity.ok(images);
    }

    @GetMapping(value = "/{adPk}/image")
    public ResponseEntity<byte[]> getUser(@PathVariable int adPk) {
        byte[] image = adsService.getAdsImage(adPk);
        return ResponseEntity.ok(image);
    }
}
