package com.bloodbank.bloodbank.repository.bank;

import com.bloodbank.bloodbank.model.bank.InactiveDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InactiveDonorDAO extends JpaRepository<InactiveDonor, Long> {
}
