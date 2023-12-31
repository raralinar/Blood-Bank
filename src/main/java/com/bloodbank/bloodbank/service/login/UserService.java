package com.bloodbank.bloodbank.service.login;

import com.bloodbank.bloodbank.model.login.Role;
import com.bloodbank.bloodbank.model.login.User;
import com.bloodbank.bloodbank.repository.login.RoleRepository;
import com.bloodbank.bloodbank.repository.login.UserRepository;
import com.bloodbank.bloodbank.dto.login.UserDTO;
import com.bloodbank.bloodbank.service.bank.EmployeeService;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserIService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmployeeService employeeService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeService = employeeService;
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

    public void addEmployee(UserDTO userDTO, Long role_id) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Optional<Role> roleNullable = roleRepository.findById(role_id);
        Role role = roleNullable.orElseGet(() -> new Role(role_id));

        user.setRole(role);
        user.setEmployee(employeeService.findById(userDTO.getEmployee_id()));
        userRepository.save(user);
    }

    public String findByEmployeeId(Long id) {
        List<String[]> p = userRepository.findByEmployee_id(id);
        System.out.println(p);
        return p.get(0)[0] + " " + p.get(0)[1];
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
