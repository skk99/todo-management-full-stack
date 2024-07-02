package com.shankarstudy.todo.service.impl;

import com.shankarstudy.todo.dto.JwtAuthResponse;
import com.shankarstudy.todo.dto.LoginDto;
import com.shankarstudy.todo.dto.RegisterDto;
import com.shankarstudy.todo.entity.Role;
import com.shankarstudy.todo.entity.User;
import com.shankarstudy.todo.exception.TodoAPIException;
import com.shankarstudy.todo.repository.RoleRepository;
import com.shankarstudy.todo.repository.UserRepository;
import com.shankarstudy.todo.security.JwtTokenProvider;
import com.shankarstudy.todo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {

        // Check whether username is already registered in DB
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists !");
        }
        // Check whether email is already registered in DB
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email already exists !");
        }

        User user = new User();
        // Convert RegisterDto to User Entity
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Set the roles for the user object
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);

        // Save the user object into DB
        userRepository.save(user);

        return "User Registered Successfully";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        // Authenticate Username and Password
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        // Save the Authentication object
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate token
        String token = jwtTokenProvider.generateToken(authentication);

        // Get role for that user
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());

        String role = null;
        if (userOptional.isPresent()) {
            User loggedInUser = userOptional.get();
            Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();
            if (optionalRole.isPresent()) {
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRole(role);

        return jwtAuthResponse;
    }
}
