package ru.skypro.diplom.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.*;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.service.UserService;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;

@RestController
@RequestMapping("users")
@CrossOrigin(value = "http://192.168.99.100:3000")
//@CrossOrigin(value = "http://192.168.0.152:3000")
//@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword, Authentication auth) {
        if (!userService.changePassword(newPassword, auth)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(Authentication auth) {
        UserDTO userDTO = userService.getUser(auth);
        if (userDTO == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping ("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userUpd, Authentication auth) {
        UserDTO userDTO = userService.updateUser(userUpd, auth);
        if (userDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping (value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestPart(value = "image") MultipartFile file, Authentication auth) throws IOException {
        if (!userService.updateUserImage(file, auth)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/image")
    public ResponseEntity<byte[]> getUser(@PathVariable int userId) {
        byte[] image = userService.getUserImage(userId);
        return ResponseEntity.ok(image);
    }
}
