package com.bloodbank.bloodbank.service.login;

import com.bloodbank.bloodbank.model.login.Role;
import com.bloodbank.bloodbank.model.login.User;
import com.bloodbank.bloodbank.repository.login.RoleRepository;
import com.bloodbank.bloodbank.repository.login.UserRepository;
import com.bloodbank.bloodbank.service.login.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserIService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Optional<Role> roleNullable = roleRepository.findById(2L);
        Role role = roleNullable.orElseGet(() -> new Role(2L));

        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }

    private UserDTO mapToUserDTO(User user) {
        return new UserDTO(
                user.getName(),
                user.getSurname(),
                user.getEmail()
        );
    }
}
