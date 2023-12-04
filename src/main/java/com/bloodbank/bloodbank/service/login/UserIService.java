package com.bloodbank.bloodbank.service.login;

import com.bloodbank.bloodbank.model.login.User;
import com.bloodbank.bloodbank.service.login.dto.UserDTO;

import java.util.List;

public interface UserIService {
    void saveUser(UserDTO userDto);

    User findUserByEmail(String email);

    List<UserDTO> findAllUsers();
}
