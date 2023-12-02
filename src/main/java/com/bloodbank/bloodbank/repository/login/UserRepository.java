package com.bloodbank.bloodbank.repository.login;

import com.bloodbank.bloodbank.model.login.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE email=?1")
    User findByEmail(String email);
}
