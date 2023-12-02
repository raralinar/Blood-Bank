package com.bloodbank.bloodbank.service.bank;

import com.bloodbank.bloodbank.model.Employee;
import com.bloodbank.bloodbank.repository.bank.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IService<Employee> {
    private final EmployeeDAO employeeDAO;
    @Autowired
    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> getAll() {
        return employeeDAO.findAll();
    }
}
