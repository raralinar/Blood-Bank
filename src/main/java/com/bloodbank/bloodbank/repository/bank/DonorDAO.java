package com.bloodbank.bloodbank.repository.bank;

import com.bloodbank.bloodbank.model.bank.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorDAO extends JpaRepository<Donor, Long> {
    @Query("SELECT d FROM Donor d where id = ?1")
    Optional<Donor> findById(Long id);

    @Query("SELECT d FROM Donor d where phone = ?1")
    Donor findByPhone(String phone);

    @Query("SELECT d.surname, d.name, d.patronymic FROM Donor d")
    List<String[]> findBySNP();

    @Query("SELECT d FROM Donor d WHERE name=?2 AND surname=?1 AND patronymic=?3")
    List<Donor> findByNames(String surname, String name, String patronymic);
}
