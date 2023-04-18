package ru.skypro.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.skypro.diplom.dto.UserUpd;
import ru.skypro.diplom.model.User;
import ru.skypro.diplom.repository.UserRepository;

@Service
@SessionScope
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private User currentUser;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User updateUser(UserUpd userUpd) {
        User user = getCurrentUser();
        user.setFirstName(userUpd.getFirstName());
        user.setLastName(userUpd.getLastName());
        user.setCity(userUpd.getCity());
        user.setEmail(userUpd.getEmail());
        user.setPhone(user.getPhone());

        user = userRepository.save(user);
        if (user == null) logger.error("Write error into database (function 'updateUser()'");
        return user;
    }

}
