package ru.skypro.diplom.controller;

import org.springframework.http.HttpStatus;
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
//@CrossOrigin(value = "http://192.168.99.100:3000")
//@CrossOrigin(value = "http://192.168.0.152:3000")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    private final UserService userService;

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

    @RolesAllowed("USER")
    @GetMapping("/me")
    //@Secured({"ROLE_USER", "USER"})
    public ResponseEntity<UserDTO> getUser(Authentication auth) {
        return ResponseEntity.ok(userService.getUser());
    }

    @RolesAllowed("ROLE_USER")
    @PatchMapping ("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userUpd ) {
        User user = userService.getCurrentUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        UserDTO userDTO = userService.updateUser(userUpd);
        return ResponseEntity.ok(userDTO);
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
