package ru.skypro.diplom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.CreateAds;
import ru.skypro.diplom.dto.ResponseWrapperAds;
import ru.skypro.diplom.model.Comment;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.service.AdsService;

@RestController
@RequestMapping("ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {
    private final AdsService adsService;

    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        ResponseWrapperAds responseWrapperAds = adsService.getAllAds();
        return ResponseEntity.ok(responseWrapperAds);
    }

    @PostMapping
    public ResponseEntity<?> addAds(@RequestPart(value = "properties") CreateAds createAds, @RequestPart(value = "image") MultipartFile file) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAds() {
        ResponseWrapperAds responseWrapperAds = adsService.getAds();
        if (responseWrapperAds == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(responseWrapperAds);
    }

    @GetMapping("/{adPK}/comments")
    public ResponseEntity<?> getComments(@PathVariable String adPK) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{adPK}/comments")
    public ResponseEntity<?> addComments(@RequestBody Comment comment, @PathVariable String adPK) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAds(@PathVariable int id) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAds(@PathVariable int id) {

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAds(@PathVariable int id, @RequestBody CreateAds createAds) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{adPK}/comments/{id}")
    public ResponseEntity<?> getComments(@PathVariable int id, @PathVariable String adPK) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{adPK}/comments/{id}")
    public ResponseEntity<?> deleteComments(@PathVariable int id, @PathVariable String adPK) {

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adPK}/comments/{id}")
    public ResponseEntity<?> updateComments(@PathVariable int id, @PathVariable String adPK, @RequestBody Comment comment) {

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/image/{id}")
    public ResponseEntity<?> updateImage(@PathVariable int id, @RequestPart(value = "image") MultipartFile file) {

        return ResponseEntity.ok().build();
    }

}
