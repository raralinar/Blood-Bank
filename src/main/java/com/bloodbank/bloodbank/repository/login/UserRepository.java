package com.bloodbank.bloodbank.repository.login;

import com.bloodbank.bloodbank.model.login.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE email=?1")
    User findByEmail(String email);

    @Query("SELECT employee.email, employee.password FROM User u WHERE employee.id=?1")
    List<String[]> findByEmployee_id(Long id);

    @Query("SELECT u FROM User u WHERE role.id=1")
    List<User> findAdmins();
}
