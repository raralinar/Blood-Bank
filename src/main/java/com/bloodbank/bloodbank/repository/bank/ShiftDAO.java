package com.bloodbank.bloodbank.repository.bank;

import com.bloodbank.bloodbank.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftDAO extends JpaRepository<Shift, Long> {
}
