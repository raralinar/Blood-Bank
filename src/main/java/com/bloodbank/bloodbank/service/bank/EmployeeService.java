package com.bloodbank.bloodbank.service.bank;

import com.bloodbank.bloodbank.dto.EmployeeDTO;
import com.bloodbank.bloodbank.model.bank.Employee;
import com.bloodbank.bloodbank.repository.bank.EmployeeDAO;
import com.bloodbank.bloodbank.service.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IService<Employee, EmployeeDTO> {
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

    public Employee findByEmail(String email) {
        return employeeDAO.findByEmail(email);
    }

    @Override
    public void add(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        employee.setPatronymic(employeeDTO.getPatronymic());
        employee.setPhone(employeeDTO.getPhone());
        employee.setDob(employeeDTO.getDob());
        employee.setEmployed(employeeDTO.getEmployed());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPassword(employeeDTO.getPassword());
        employeeDAO.save(employee);
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
        if (employeeDAO.findById(employeeDTO.getId()).isEmpty())
            return;
        Employee employee = employeeDAO.findById(employeeDTO.getId()).get();
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        employee.setPatronymic(employeeDTO.getPatronymic());
        employee.setPhone(employeeDTO.getPhone());
        employee.setDob(employeeDTO.getDob());
        employee.setEmployed(employeeDTO.getEmployed());
        employeeDAO.save(employee);
    }

    @Override
    public void delete(Long id) {
        if (employeeDAO.findById(id).isEmpty())
            return;
        employeeDAO.delete(employeeDAO.findById(id).get());
    }

    public EmployeeDTO maptoDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSurname(employee.getSurname());
        employeeDTO.setPatronymic(employee.getPatronymic());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setDob(employee.getDob());
        employeeDTO.setEmployed(employee.getEmployed());
        employeeDTO.setEmail(employee.getEmail());
        System.out.println(employee.getPassword());
        employeeDTO.setPassword(employee.getPassword());
        return employeeDTO;
    }

    @Override
    public Employee findById(Long id) {
        return employeeDAO.findById(id).get();
    }

}
