package com.demo.model.login;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// This class is an implemtation of class UserDetails of Spring Security,
// not a user-defined
// Spring Security will transfer data with class UserDetails
public class CustomUserDetails implements UserDetails {
    UserLogin user;

    public CustomUserDetails(UserLogin user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singleton(new SimpleGrantedAuthority("USER"));
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (Role r: user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(r.getRole()));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
