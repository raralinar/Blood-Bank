package com.bloodbank.bloodbank.service.bank;

import com.bloodbank.bloodbank.model.Donor;
import com.bloodbank.bloodbank.repository.DonorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService implements IService<Donor> {
    private final DonorDAO donorDAO;

    @Autowired
    public DonorService(DonorDAO donorDAO) {
        this.donorDAO = donorDAO;
    }

    @Override
    public List<Donor> getAll() {
        return donorDAO.findAll();
    }

}
