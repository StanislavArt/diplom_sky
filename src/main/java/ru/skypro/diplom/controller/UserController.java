package ru.skypro.diplom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.*;
import ru.skypro.diplom.service.UserService;

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

    /**
     * Изменяет пароль пользователя, авторизованного в системе.
     *
     * @param newPassword объект DTO, в который приходит информация с фронт-части для изменения пароля.
     * @param auth объект, который хранит аутентификационные данные пользователя, выполняющего вызов функции.
     * @return статус HTTP запроса
     * @see NewPassword
     */
    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword, Authentication auth) {
        if (!userService.changePassword(newPassword, auth)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Возвращает информацию по авторизованному пользователю.
     *
     * @param auth объект, который хранит аутентификационные данные пользователя, выполняющего вызов функции.
     * @return информация о пользователе
     * @see UserDTO
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(Authentication auth) {
        UserDTO userDTO = userService.getUser(auth);
        if (userDTO == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Обновляет информацию по авторизованному пользователю.
     *
     * @param userUpd объект DTO, в который приходит информация с фронт-части для изменения информации о пользователе.
     * @param auth объект, который хранит аутентификационные данные пользователя, выполняющего вызов функции.
     * @return обновленная информация о пользователе.
     * @see UserDTO
     */
    @PatchMapping ("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userUpd, Authentication auth) {
        UserDTO userDTO = userService.updateUser(userUpd, auth);
        if (userDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Изменяет аватар авторизованного пользователя. Прежний аватар удаляется из файловой системы.
     *
     * @param file новый аватар пользователя.
     * @param auth объект, который хранит аутентификационные данные пользователя, выполняющего вызов функции.
     * @return статус HTTP запроса.
     */
    @PatchMapping (value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestPart(value = "image") MultipartFile file, Authentication auth) {
        if (!userService.updateUserImage(file, auth)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Получает аватар выбранного пользователя.
     *
     * @param userId идентификатор пользователя.
     * @return аватар пользователя
     */
    @GetMapping("/{userId}/image")
    public ResponseEntity<byte[]> getUser(@PathVariable int userId) {
        byte[] image = userService.getUserImage(userId);
        return ResponseEntity.ok(image);
    }
}
