package com.bloodbank.bloodbank.repository.bank;

import com.bloodbank.bloodbank.model.blood.Blood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodDAO extends JpaRepository<Blood, Long> {
    @Query("SELECT b FROM Blood b")
    List<Blood> findAllBlood();
}
