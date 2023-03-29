package ru.skypro.diplom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.*;
import ru.skypro.diplom.model.User;

@RestController
@RequestMapping("users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword ) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser() {

        return ResponseEntity.ok().build();
    }

    @PatchMapping ("/me")
    public ResponseEntity<?> updateUser(@RequestBody User user ) {

        return ResponseEntity.ok().build();
    }

    @PatchMapping ("/me/image")
    public ResponseEntity<?> updateUserImage(@RequestPart(value = "image") MultipartFile file) {

        return ResponseEntity.ok().build();
    }
}
