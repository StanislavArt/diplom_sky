package ru.skypro.diplom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.CreateAds;
import ru.skypro.diplom.model.Comment;

@RestController
@RequestMapping("Ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    @GetMapping
    public ResponseEntity<?> getAllAds() {

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> addAds(@RequestPart(value = "properties") CreateAds createAds, @RequestPart(value = "image") MultipartFile file) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAds() {

        return ResponseEntity.ok().build();
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
