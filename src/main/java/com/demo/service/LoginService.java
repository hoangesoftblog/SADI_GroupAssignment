package com.demo.service;


import com.demo.model.login.CustomUserDetails;
import com.demo.model.login.Role;
import com.demo.model.login.UserLogin;
import com.demo.repository.LoginRepository;
import com.demo.repository.RoleStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@Transactional
@Service
public class LoginService implements UserDetailsService {
    @Autowired
    LoginRepository repository;

    @Autowired
    RoleStore roleRepository;

//    @Autowired
//    PasswordEncoder encoder;

    public UserDetails loadUserByUsername(String username) {
        UserLogin userLogin = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        return new CustomUserDetails(userLogin);
    }

    public void register(UserLogin userLogin) throws UserAlreadyExistedException {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserLogin existed = repository.findByUsername(userLogin.getUsername()).orElse(null);
        if (existed != null) {
            throw new UserAlreadyExistedException("User already existed with username: " + userLogin.getUsername());
        } else {
            userLogin.setPassword(encoder.encode(userLogin.getPassword()));

            Role user = new Role(); user.setRole("USER");
            userLogin.setRoles(Arrays.asList(roleRepository.findFirstByRole("USER").orElse(user)));
            repository.save(userLogin);
        }
    }

    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "User with username already exist.")
    public static class UserAlreadyExistedException extends Exception {
        public UserAlreadyExistedException(String message) {
            super(message);
        }
    }
}
