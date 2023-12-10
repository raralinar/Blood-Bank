package com.bloodbank.bloodbank.service.bank;

import com.bloodbank.bloodbank.dto.ShiftDTO;
import com.bloodbank.bloodbank.model.bank.Employee;
import com.bloodbank.bloodbank.model.bank.Shift;
import com.bloodbank.bloodbank.repository.bank.EmployeeDAO;
import com.bloodbank.bloodbank.repository.bank.ShiftDAO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShiftService implements IService<Shift, ShiftDTO> {
    private final ShiftDAO shiftDAO;
    private final EmployeeDAO employeeDAO;

    public ShiftService(ShiftDAO shiftDAO, EmployeeDAO employeeService) {
        this.shiftDAO = shiftDAO;
        this.employeeDAO = employeeService;
    }

    @Override
    public List<Shift> getAll() {
        return shiftDAO.findAll();
    }

    public List<Shift> findAllShiftsWithDateRange(LocalDate begin, LocalDate end) {
        return shiftDAO.findAllShiftsWithDateRange(begin, end);
    }

    @Override
    public void add(ShiftDTO shiftDTO) {
        Shift shift = new Shift();
        shift.setDate(shiftDTO.getDate());
        shift.setBegin(shiftDTO.getBegin());
        shift.setEnd(shiftDTO.getEnd());
        if (employeeDAO.findById(Long.parseLong(shiftDTO.getEmployee().split(" ")[0])).isEmpty())
            return;
        Employee employee = employeeDAO.findById(Long.parseLong(shiftDTO.getEmployee().split(" ")[0])).get();
        shift.setEmployee(employee);
        shiftDAO.save(shift);
    }

    @Override
    public void delete(Long id) {
        if (shiftDAO.findById(id).isEmpty())
            return;
        Shift shift = shiftDAO.findById(id).get();
        Employee s = shift.getEmployee();
        shiftDAO.delete(shiftDAO.findById(id).get());
        System.out.println(shift.getEmployee().getId());
        s.setId(shift.getEmployee().getId());
        employeeDAO.save(s);
    }

    @Override
    public void update(ShiftDTO shiftDTO) {
        Shift shift = shiftDAO.findShiftAtDay(shiftDTO.getDate());
        shift.setDate(shiftDTO.getDate());
        shift.setBegin(shiftDTO.getBegin());
        shift.setEnd(shiftDTO.getEnd());
        shift.setEmployee(employeeDAO.findById(Long.parseLong(shiftDTO.getEmployee().split(" ")[0])).get());
        shiftDAO.save(shift);
    }

    public ShiftDTO mapToDTO(Shift shift) {
        ShiftDTO shiftDTO = new ShiftDTO();
        shiftDTO.setDate(shift.getDate());
        shiftDTO.setBegin(shift.getBegin());
        shiftDTO.setEnd(shift.getEnd());
        shiftDTO.setEmployee(shift.getEmployee().getId() + " " + shift.fullNameEmployee());
        return shiftDTO;
    }

    public boolean isEmployeeEngaged(Long id) {
        return !shiftDAO.findByEmployee(id).isEmpty();
    }
    public Shift findByDate(LocalDate date) {
        return shiftDAO.findShiftAtDay(date);
    }
    public boolean isShiftThisDay(LocalDate date) {
        return shiftDAO.findShiftAtDay(date) != null;
    }

    @Override
    public Shift findById(Long id) {
        if (shiftDAO.findById(id).isEmpty())
            return null;
        return shiftDAO.findById(id).get();
    }
}
