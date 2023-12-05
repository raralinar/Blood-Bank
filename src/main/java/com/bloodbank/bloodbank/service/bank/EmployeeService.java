package com.bloodbank.bloodbank.service.bank;

import com.bloodbank.bloodbank.dto.EmployeeDTO;
import com.bloodbank.bloodbank.model.bank.Employee;
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

    public List<String[]> findByName() {
        return employeeDAO.findBySNP();
    }

    public List<Employee> findByName(String surname, String name, String patronymic) {
        return employeeDAO.findByNames(surname, name, patronymic);
    }

    public void add(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        employee.setPatronymic(employeeDTO.getPatronymic());
        employee.setPhone(employeeDTO.getPhone());
        employee.setDob(employeeDTO.getDob());
        employee.setEmployed(employeeDTO.getEmployed());
        employeeDAO.save(employee);
    }

    public void update(EmployeeDTO employeeDTO) {
        Employee employee = employeeDAO.findByPhone(employeeDTO.getPhone());
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        employee.setPatronymic(employeeDTO.getPatronymic());
        employee.setPhone(employeeDTO.getPhone());
        employee.setDob(employeeDTO.getDob());
        employee.setEmployed(employeeDTO.getEmployed());
        employeeDAO.save(employee);
    }

    public void delete(Long id) {
        if (employeeDAO.findById(id).isEmpty())
            return;
        employeeDAO.delete(employeeDAO.findById(id).get());
    }

    public EmployeeDTO maptoDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employeeDTO.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSurname(employee.getSurname());
        employeeDTO.setPatronymic(employee.getPatronymic());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setDob(employee.getDob());
        employeeDTO.setEmployed(employee.getEmployed());
        return employeeDTO;
    }



    public Employee findById(Long id) {
        return employeeDAO.findById(id).get();
    }




}
