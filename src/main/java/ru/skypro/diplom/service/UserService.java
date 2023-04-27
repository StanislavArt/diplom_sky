package ru.skypro.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;

    @Value("${diplom.storage}")
    private String storagePath;

    public UserService(UserRepository userRepository, UserDetailsManager manager) {
        this.userRepository = userRepository;
        this.manager = manager;
        this.encoder = new BCryptPasswordEncoder();
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
        if (!writeFile(file)) { return false; }
        user.setImage(file.getOriginalFilename());
        user = userRepository.save(user);
        return true;
    }

    public boolean changePassword(NewPassword newPassword, Authentication auth) {
        User user = getUserFromAuthentication(auth);
        if (user == null) { return false; }

        String encryptedPassword = user.getPassword();
        String encryptionType = encryptedPassword.substring(0, 8);
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        if (!encoder.matches(newPassword.getCurrentPassword(), encryptedPasswordWithoutEncryptionType)) {
            return false;
        };
        if (encoder.matches(newPassword.getNewPassword(), encryptedPasswordWithoutEncryptionType)) {
            return false;
        };

        String encryptedNewPassword = encryptionType + encoder.encode(newPassword.getNewPassword());
        manager.changePassword(encryptedPassword, encryptedNewPassword);
        user.setPassword(encryptedNewPassword);
        user = userRepository.save(user);
        return true;
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
        User user = userRepository.findUserByUsername(username).orElse(null);
        return user;
    }

    public boolean writeFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
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
            byte[] array = Files.readAllBytes(Paths.get(storagePath + fileName));
            return array;
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
        byte[] data = transferFileToByteArray(fileName);
        return data;
    }

}
