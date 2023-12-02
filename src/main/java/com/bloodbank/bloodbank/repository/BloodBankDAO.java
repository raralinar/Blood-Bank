package com.bloodbank.bloodbank.repository;

import com.bloodbank.bloodbank.model.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodBankDAO extends JpaRepository<BloodBank, Long> {
}
