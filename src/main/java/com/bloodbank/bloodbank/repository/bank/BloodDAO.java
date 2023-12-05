package com.bloodbank.bloodbank.repository.bank;

import com.bloodbank.bloodbank.model.bank.blood.Blood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodDAO extends JpaRepository<Blood, Long> {
    @Query("SELECT b FROM Blood b")
    List<Blood> findAllBlood();

    @Query("SELECT b FROM Blood b WHERE blood_type=?1 AND rhesus=?2")
    Blood findByTypeAndRhesus(Integer type, String rhesus);

    @Query("SELECT b.kell FROM Blood b WHERE blood_type=?1")
    String findKell(Integer type);
}
