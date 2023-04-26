package ru.skypro.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.NewPassword;
import ru.skypro.diplom.dto.UserDTO;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDetailsManager manager;
    private User currentUser;

    public UserService(UserRepository userRepository, UserDetailsManager manager) {
        this.userRepository = userRepository;
        this.manager = manager;
        this.currentUser = new User();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public UserDTO getUser() {


        return getUserDTO(getCurrentUser());
    }

    public UserDTO updateUser(UserDTO userUpd) {
        User user = getCurrentUser();
        user.setFirstName(userUpd.getFirstName());
        user.setLastName(userUpd.getLastName());
        user.setPhone(userUpd.getPhone());

        user = userRepository.save(user);
        return getUserDTO(user);
    }

    public boolean updateUserImage(byte[] data) {
        User user = getCurrentUser();
        user.setImage(new String(data));
        user = userRepository.save(user);
        if (user == null) {
            logger.error("Write error into database (function 'updateUser()'");
            return false;
        }
        return true;
    }

    public boolean changePassword(NewPassword newPassword) {
        User user = getCurrentUser();

        if (!user.getPassword().equals(newPassword.getCurrentPassword())) { return false; }
        if (user.getPassword().equals(newPassword.getNewPassword())) { return false; }

        user.setPassword(newPassword.getNewPassword());
        user = userRepository.save(user);
        if (user == null) {
            logger.error("Write error into database (function 'updateUser()'");
            return false;
        }
        return false;
    }

    private UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        if (user == null ) { return userDTO; }
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setImage(user.getImage());
        return userDTO;
    }

    public HttpStatus verifyAuthority(Authentication authentication) {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        if (!manager.userExists(user.getUsername())) { return HttpStatus.NOT_FOUND; }
        if (!authentication.isAuthenticated()) { return HttpStatus.UNAUTHORIZED; }
        return HttpStatus.OK;
    }

}
