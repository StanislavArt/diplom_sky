package ru.skypro.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.diplom.dto.NewPassword;
import ru.skypro.diplom.dto.UserDTO;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDetailsManager manager;

    @Value("${diplom.storage}")
    private String storagePath;

    public UserService(UserRepository userRepository, UserDetailsManager manager) {
        this.userRepository = userRepository;
        this.manager = manager;
    }

    public UserDTO getUser(Authentication auth) {
        User user = getUserFromAuthentication(auth);
        return getUserDTO(user);
    }

    public UserDTO updateUser(UserDTO userUpd, Authentication auth) {
        User user = getUserFromAuthentication(auth);
        if (user == null) { return null; }

        user.setFirstName(userUpd.getFirstName());
        user.setLastName(userUpd.getLastName());
        user.setPhone(userUpd.getPhone());
        user = userRepository.save(user);
        return getUserDTO(user);
    }

    public boolean updateUserImage(MultipartFile file, Authentication auth) {
        User user = getUserFromAuthentication(auth);
        if (user == null) { return false; }

        String fileName = prepareFileName(file);
        if (fileName == null) { return false; }

        if (!writeFile(file, fileName)) { return false; }
        user.setImage(fileName);
        user = userRepository.save(user);
        return true;
    }

    public boolean changePassword(NewPassword newPassword, Authentication auth) {
        User user = getUserFromAuthentication(auth);
        if (user == null) { return false; }

        try {
            manager.changePassword(newPassword.getCurrentPassword(), newPassword.getNewPassword());
        } catch (Exception e) {
            logger.error("Ошибка при изменении пароля");
            logger.error(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }

    public String prepareFileName(MultipartFile file) {
        Set<String> allowedContent = Set.of("image/jpeg", "image/png");
        if (!allowedContent.contains(file.getContentType())) {
            logger.error("Тип содержимого не поддерживается (файл: {})", file.getOriginalFilename());
            return null;
        }

        int indexExtensionFile = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        if (indexExtensionFile == -1) {
            logger.error("Не найдено расширение для выбранного файла {}", file.getOriginalFilename());
            return null;
        }
        String extensionFile = file.getOriginalFilename().substring(indexExtensionFile);
        return UUID.randomUUID().toString() + extensionFile;
    }

    private UserDTO getUserDTO(User user) {
        if (user == null ) { return null; }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setImage("/users/" + user.getId() + "/image");
        return userDTO;
    }

    public User getUserFromAuthentication(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userRepository.findUserByUsername(username).orElse(null);
    }

    public boolean writeFile(MultipartFile file, String fileName) {
        try {
            File pathFile = new File(storagePath + fileName);
            file.transferTo(pathFile);
            return true;
        } catch (IOException e) {
            logger.error("Error writing file in function 'updateAdsImage()'");
            logger.error(Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public byte[] transferFileToByteArray(String fileName) {
        try {
            return Files.readAllBytes(Paths.get(storagePath + fileName));
        } catch (IOException e) {
            logger.error("Error reading file in function 'transferFileToString()'");
            logger.error(Arrays.toString(e.getStackTrace()));
            return new byte[0];
        }
    }

    public byte[] getUserImage(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) { return new byte[0]; }
        String fileName = user.getImage();
        if (fileName == null || fileName.isEmpty()) { return new byte[0]; }
        return transferFileToByteArray(fileName);
    }

}
