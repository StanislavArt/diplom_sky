package ru.skypro.diplom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.*;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("users")
@CrossOrigin(value = "http://192.168.99.100:3000")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<String> setPassword(@RequestBody NewPassword newPassword ) {
        User user = userService.getCurrentUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (!userService.changePassword(newPassword)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        return ResponseEntity.ok("User's password is changed");
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
    public ResponseEntity<String> updateUserImage(@RequestPart(value = "image") MultipartFile file) throws IOException {
        User user = userService.getCurrentUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (!userService.updateUserImage(file.getBytes())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok("User's image is changed!");
    }
}
