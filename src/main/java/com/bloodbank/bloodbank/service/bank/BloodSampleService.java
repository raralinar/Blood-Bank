package com.bloodbank.bloodbank.service.bank;

import com.bloodbank.bloodbank.dto.BloodSampleDTO;
import com.bloodbank.bloodbank.model.bank.BloodSample;
import com.bloodbank.bloodbank.model.bank.Donor;
import com.bloodbank.bloodbank.repository.bank.BloodSampleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloodSampleService implements IService<BloodSample, BloodSampleDTO> {
    private final BloodSampleDAO bloodSampleDAO;
    private final DonorService donorService;
    private final ShiftService shiftService;

    @Autowired
    public BloodSampleService(BloodSampleDAO bloodSampleDAO, DonorService donorService, ShiftService shiftService) {
        this.bloodSampleDAO = bloodSampleDAO;
        this.donorService = donorService;
        this.shiftService = shiftService;
    }

    @Override
    public List<BloodSample> getAll() {
        return bloodSampleDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        if (bloodSampleDAO.findById(id).isEmpty())
            return;
        bloodSampleDAO.delete(bloodSampleDAO.findById(id).get());
    }

    @Override
    public void update(BloodSampleDTO dto) {
        if (bloodSampleDAO.findById(dto.getId()).isEmpty())
            return;
        BloodSample sample = bloodSampleDAO.findById(dto.getId()).get();
        sample.setTime(dto.getTime());
        sample.setVolume(dto.getVolume());
        sample.setDonor(donorService.findById(Long.parseLong(dto.getDonor().split(" ")[0])));
        sample.setShift(shiftService.findByDate(dto.getDate()));
        bloodSampleDAO.save(sample);
    }


    @Override
    public void add(BloodSampleDTO dto) {
        BloodSample sample = new BloodSample();
        sample.setTime(dto.getTime());
        sample.setVolume(dto.getVolume());
        sample.setDonor(donorService.findById(Long.parseLong(dto.getDonor().split(" ")[0])));
        sample.setShift(shiftService.findByDate(dto.getDate()));
        bloodSampleDAO.save(sample);
    }

    public List<String> findDonorNames() {
        return bloodSampleDAO.findDonorsNames().stream().map(name -> name[0] + " " + name[1] + " " + name[2]).collect(Collectors.toList());
    }

    public List<BloodSample> findByDonor(List<Donor> donor) {
        List<BloodSample> list = new ArrayList<>();
        for (Donor donor1 : donor)
            list.addAll(bloodSampleDAO.findByDonor(donor1));
        return list;
    }

    public List<BloodSample> findByDateRange(LocalDate from, LocalDate to) {
        return bloodSampleDAO.findAllSamplesWithDateRange(from, to);
    }
    public BloodSampleDTO mapToDTO(BloodSample bloodSample) {
        BloodSampleDTO dto = new BloodSampleDTO();
        dto.setId(bloodSample.getId());
        dto.setDate(bloodSample.getShift().getDate());
        dto.setVolume(bloodSample.getVolume());
        dto.setTime(bloodSample.getTime());
        dto.setEmployee(bloodSample.getShift().fullNameEmployee());
        dto.setDonor(bloodSample.getDonor().fullNameDonor());
        return dto;
    }

    @Override
    public BloodSample findById(Long id) {
        return bloodSampleDAO.findById(id).get();
    }

    public List<BloodSample> findByDonor(Long id) {
        return bloodSampleDAO.findDonorById(id);
    }
    public boolean isDonated(LocalDate date, Long id) {
        return bloodSampleDAO.findDonorByDate(id, date) != null;
    }
    public boolean isDonated(Long id) {
        return bloodSampleDAO.findDonorById(id).isEmpty();
    }
}