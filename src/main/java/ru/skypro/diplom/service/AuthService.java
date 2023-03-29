package ru.skypro.diplom.service;

import ru.skypro.diplom.enums.Role;
import ru.skypro.diplom.dto.*;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
