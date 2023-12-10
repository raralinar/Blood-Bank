package com.bloodbank.bloodbank.repository.bank;

import com.bloodbank.bloodbank.model.bank.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Long> {
    @Query("SELECT e.id, e.surname, e.name, e.patronymic FROM Employee e")
    List<String[]> findBySNP();

    @Query("SELECT e FROM Employee e WHERE name=?2 AND surname=?1 AND patronymic=?3")
    List<Employee> findByNames(String surname, String name, String patronymic);

    @Query("SELECT e FROM Employee e WHERE id=?1")
    Optional<Employee> findById(Long id);

    @Query("SELECT e FROM Employee e WHERE phone=?1")
    Employee findByPhone(String phone);

    @Query("SELECT e FROM Employee e WHERE email=?1")
    Employee findByEmail(String email);
}
