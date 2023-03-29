package ru.skypro.diplom.controller;

import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.skypro.diplom.enums.Role;
import ru.skypro.diplom.DTO.*;
import ru.skypro.diplom.service.AuthService;

import static ru.skypro.diplom.enums.Role.*;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        LoggerFactory.getLogger(this.getClass()).info("login " + req.toString());
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq req) {
        LoggerFactory.getLogger(this.getClass()).info("register " + req.toString());
        Role role = req.getRole() == null ? USER : req.getRole();
        if (authService.register(req, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
