package com.demo.service;


import com.demo.model.login.CustomUserDetails;
import com.demo.model.login.UserLogin;
import com.demo.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LoginService implements UserDetailsService {
    @Autowired
    LoginRepository repository;

    public UserDetails loadUserByUsername(String username) {
        UserLogin userLogin = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        return new CustomUserDetails(userLogin);
    }
}
