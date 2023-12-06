package com.bloodbank.bloodbank.repository.bank;

import com.bloodbank.bloodbank.model.bank.Shift;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftDAO extends JpaRepository<Shift, Long> {
    @Query("SELECT s FROM Shift s WHERE date BETWEEN :startTime AND :endTime")
    List<Shift> findAllShiftsWithDateRange(
            @Param("startTime") LocalDate startTime, @Param("endTime") LocalDate endTime);

    @Query("SELECT s FROM Shift s WHERE date=?1")
    Shift findShiftAtDay(LocalDate date);

    @Query("SELECT s FROM Shift s WHERE employee.id=?1")
    List<Shift> findByEmployee(Long id);
}
