package com.bloodbank.bloodbank.repository.login;

import com.bloodbank.bloodbank.model.login.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE name=?1")
    Optional<Role> findByName(String name);
}
