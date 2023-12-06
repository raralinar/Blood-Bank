package com.bloodbank.bloodbank.service.bank;

import com.bloodbank.bloodbank.dto.DonorDTO;
import com.bloodbank.bloodbank.model.bank.Donor;
import com.bloodbank.bloodbank.model.bank.blood.Blood;
import com.bloodbank.bloodbank.repository.bank.BloodDAO;
import com.bloodbank.bloodbank.repository.bank.DonorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService implements IService<Donor, DonorDTO> {
    private final DonorDAO donorDAO;
    private final BloodDAO bloodDAO;

    @Autowired
    public DonorService(DonorDAO donorDAO, BloodDAO bloodDAO) {
        this.donorDAO = donorDAO;
        this.bloodDAO = bloodDAO;
    }

    @Override
    public List<Donor> getAll() {
        return donorDAO.findAll();
    }

    @Override
    public void add(DonorDTO donorDTO) {
        Donor donor = new Donor();
        donor.setName(donorDTO.getName());
        donor.setSurname(donorDTO.getSurname());
        donor.setPatronymic(donorDTO.getPatronymic());
        donor.setSex(donorDTO.getSex());
        donor.setDob(donorDTO.getDob());
        donor.setPhone(donorDTO.getPhone());
        donor.setDonation(donorDTO.getDonations());

        System.out.println(donorDTO.getBlood_type() + " " + donorDTO.getRhesus());
        Blood blood = bloodDAO.findByTypeAndRhesus(donorDTO.getBlood_type(), donorDTO.getRhesus());
        if (blood == null) {
            System.out.println(111);
            return;
        }
        donor.setBlood(blood);
        donorDAO.save(donor);
    }

    @Override
    public void delete(Long id) {
        if (donorDAO.findById(id).isEmpty())
            return;
        Blood blood = donorDAO.findById(id).get().getBlood();
        donorDAO.delete(donorDAO.findById(id).get());
        bloodDAO.save(blood);
    }

    @Override
    public void update(DonorDTO donorDTO) {
        Donor donor = donorDAO.findByPhone(donorDTO.getPhone());
        donor.setName(donorDTO.getName());
        donor.setSurname(donorDTO.getSurname());
        donor.setPatronymic(donorDTO.getPatronymic());
        donor.setSex(donorDTO.getSex());
        donor.setDob(donorDTO.getDob());
        Blood blood = bloodDAO.findByTypeAndRhesus(donorDTO.getBlood_type(), donorDTO.getRhesus());
        if (blood == null) {
            return;
        }
        donor.setBlood(blood);
        donorDAO.save(donor);
    }

    public List<String[]> findByNames() {
        return donorDAO.findBySNP();
    }

    public List<Donor> findByNames(String surname, String name, String patronymic) {
        return donorDAO.findByNames(surname, name, patronymic);
    }


    public boolean isPhoneExists(String phone) {
        return donorDAO.findByPhone(phone) != null;
    }

    @Override
    public Donor findById(Long id) {
        return donorDAO.findById(id).get();
    }

    public DonorDTO maptoDTO(Donor donor) {
        DonorDTO donordto = new DonorDTO();
        donordto.setId(donordto.getId());
        donordto.setName(donor.getName());
        donordto.setSurname(donor.getSurname());
        donordto.setPatronymic(donor.getPatronymic());
        donordto.setSex(donor.getSex());
        donordto.setDob(donor.getDob());
        donordto.setPhone(donor.getPhone());
        donordto.setBlood_type(donor.getBlood().getBlood_type());
        donordto.setRhesus(donor.getBlood().getRhesus());
        return donordto;
    }
}
