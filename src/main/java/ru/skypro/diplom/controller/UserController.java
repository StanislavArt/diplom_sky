package ru.skypro.diplom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.*;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.service.UserService;

@RestController
@RequestMapping("users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword ) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser() {
        User user = userService.getCurrentUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(user);
    }

    @PatchMapping ("/me")
    public ResponseEntity<User> updateUser(@RequestBody UserUpd userUpd ) {
        User user = userService.getCurrentUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (userUpd.getFirstName().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        user = userService.updateUser(userUpd);
        if(user == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.ok().build();
    }

    @PatchMapping ("/me/image")
    public ResponseEntity<?> updateUserImage(@RequestPart(value = "image") MultipartFile file) {

        return ResponseEntity.ok().build();
    }
}
