package com.bloodbank.bloodbank.service.bank;

import com.bloodbank.bloodbank.model.Shift;
import com.bloodbank.bloodbank.repository.bank.ShiftDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftService implements IService<Shift> {
    private final ShiftDAO shiftDAO;

    public ShiftService(ShiftDAO shiftDAO) {
        this.shiftDAO = shiftDAO;
    }

    @Override
    public List<Shift> getAll() {
        return shiftDAO.findAll();
    }
}
