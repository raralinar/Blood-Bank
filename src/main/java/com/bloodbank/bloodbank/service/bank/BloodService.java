package com.bloodbank.bloodbank.service.bank;

import com.bloodbank.bloodbank.model.bank.blood.Blood;
import com.bloodbank.bloodbank.repository.bank.BloodDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodService implements IService<Blood, BloodDAO> {

    private final BloodDAO bloodDAO;

    @Autowired
    public BloodService(BloodDAO bloodDAO) {
        this.bloodDAO = bloodDAO;
    }

    @Override
    public List<Blood> getAll() {
        return bloodDAO.findAllBlood();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(BloodDAO dto) {

    }

    @Override
    public void add(BloodDAO dto) {

    }

    @Override
    public Blood findById(Long id) {
        return null;
    }
}
