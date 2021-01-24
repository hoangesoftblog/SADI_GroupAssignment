package com.demo.repository;

import com.demo.model.login.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.Optional;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;

public interface RoleStore extends JpaRepository<Role, Long> {
    @QueryHints(value = {@QueryHint(name = HINT_CACHEABLE, value = "true")})
    Optional<Role> findFirstByRole(String role);
}
