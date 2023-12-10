package com.bloodbank.bloodbank.service.login;

import com.bloodbank.bloodbank.model.login.Role;
import com.bloodbank.bloodbank.model.login.User;
import com.bloodbank.bloodbank.repository.login.RoleRepository;
import com.bloodbank.bloodbank.repository.login.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;


    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userRepository.findAdmins().isEmpty()) {
            User user = new User();
            user.setName("admin");
            user.setSurname("admin");
            user.setEmail("admin@ru");
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode("123"));
            Optional<Role> role = roleRepository.findById(1L);
            Role r = new Role(1L);
            if (role.isPresent())
                r = role.get();
            user.setRole(r);
            userRepository.save(user);
        }
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(List.of(user.getRole())));
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}

