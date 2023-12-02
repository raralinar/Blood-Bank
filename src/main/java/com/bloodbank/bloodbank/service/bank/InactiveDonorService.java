package com.bloodbank.bloodbank.service.bank;

import com.bloodbank.bloodbank.model.InactiveDonor;
import com.bloodbank.bloodbank.repository.InactiveDonorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InactiveDonorService implements IService<InactiveDonor> {
    private final InactiveDonorDAO inactiveDonorDAO;

    @Autowired
    public InactiveDonorService(InactiveDonorDAO inactiveDonorDAO) {
        this.inactiveDonorDAO = inactiveDonorDAO;
    }

    @Override
    public List<InactiveDonor> getAll() {
        return inactiveDonorDAO.findAll();
    }
}
