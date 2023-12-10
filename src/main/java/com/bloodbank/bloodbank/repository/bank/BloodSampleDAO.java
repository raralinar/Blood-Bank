package com.bloodbank.bloodbank.repository.bank;

import com.bloodbank.bloodbank.model.bank.BloodSample;
import com.bloodbank.bloodbank.model.bank.Donor;
import com.bloodbank.bloodbank.model.bank.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BloodSampleDAO extends JpaRepository<BloodSample, Long> {
    @Query("SELECT s FROM BloodSample s WHERE donor.id=?1 AND shift.date=?2")
    BloodSample findDonorByDate(Long id, LocalDate date);

    @Query("SELECT s FROM BloodSample s WHERE donor.id=?1")
    List<BloodSample> findDonorById(Long id);

    @Query("SELECT donor.surname, donor.name, donor.patronymic FROM BloodSample b")
    List<String[]> findDonorsNames();

    @Query("SELECT b FROM BloodSample b WHERE donor=?1")
    List<BloodSample> findByDonor(Donor donor);

    @Query("SELECT b FROM BloodSample b WHERE shift.date BETWEEN :startTime AND :endTime")
    List<BloodSample> findAllSamplesWithDateRange(
            @Param("startTime") LocalDate startTime, @Param("endTime") LocalDate endTime);
}
