package com.bloodbank.bloodbank.controllers.bank;

import com.bloodbank.bloodbank.controllers.bank.ManagementController.Date;
import com.bloodbank.bloodbank.dto.BloodSampleDTO;
import com.bloodbank.bloodbank.model.bank.BloodSample;
import com.bloodbank.bloodbank.model.bank.Donor;
import com.bloodbank.bloodbank.model.bank.Shift;
import com.bloodbank.bloodbank.service.bank.BloodSampleService;
import com.bloodbank.bloodbank.service.bank.DonorService;
import com.bloodbank.bloodbank.service.bank.ShiftService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bloodbank.bloodbank.controllers.bank.ManagementController.Search;

@Controller
public class BloodSampleController implements IController<Search, BloodSampleDTO> {

    static String action = "add", mainPage = "admin/sample/samples", editPage = "admin/sample/edit";
    private final BloodSampleService bloodSampleService;
    private final DonorService donorService;
    private final ShiftService shiftService;

    @Autowired
    public BloodSampleController(BloodSampleService bloodSampleService, DonorService donorService, ShiftService shiftService) {
        this.bloodSampleService = bloodSampleService;
        this.donorService = donorService;
        this.shiftService = shiftService;
    }

    @RequestMapping("/sample")
    public String init(Model model) {
        model.addAttribute("samples", bloodSampleService.getAll());
        model.addAttribute("search", new Search());
        model.addAttribute("date", new Date());
        model.addAttribute("condition", shiftService.findByDate(LocalDate.now()) == null);
        return mainPage;
    }


    @Override
    @RequestMapping("/sample/search/name")
    public String search(@Valid @ModelAttribute("search") Search search, Model model) {
        List<String> samples = bloodSampleService.findDonorNames().stream()
                .filter(name -> name.toUpperCase().contains(search.getString().toUpperCase()))
                .distinct().toList();
        List<BloodSample> sampleList = new ArrayList<>();
        for (String s : samples) {
            String[] p = s.split(" ");
            sampleList.addAll(bloodSampleService.findByDonor(donorService.findByNames(p[0], p[1], p[2])));
        }
        if (search.getString().isBlank())
            sampleList = bloodSampleService.getAll();
        model.addAttribute("samples", sampleList);
        model.addAttribute("search", search);
        model.addAttribute("date", new Date());
        model.addAttribute("condition", shiftService.findByDate(LocalDate.now()) == null);
        return mainPage;
    }

    @RequestMapping("/sample/search/date")
    public String search(@Valid @ModelAttribute("date") Date date, Model model) {
        List<BloodSample> samples;
        if (date.getFrom() == null && date.getTo() == null) {
            model.addAttribute("samples", bloodSampleService.getAll());
            model.addAttribute("search", new Search());
            model.addAttribute("date", date);
            model.addAttribute("condition", shiftService.findByDate(LocalDate.now()) == null);
            return mainPage;
        }
        date.setFrom(date.getFrom() == null ? LocalDate.now() : date.getFrom());
        date.setTo(date.getTo() == null ? LocalDate.now() : date.getTo());
        samples = bloodSampleService.findByDateRange(date.getFrom(), date.getTo());
        model.addAttribute("samples", samples);
        model.addAttribute("search", new Search());
        model.addAttribute("date", date);
        model.addAttribute("condition", shiftService.findByDate(LocalDate.now()) == null);
        return mainPage;
    }

    @Override
    @RequestMapping(value = "/sample/add", method = RequestMethod.GET)
    public String add(Model model) {
        action = "add";
        BloodSampleDTO sampleDTO = new BloodSampleDTO();
        model.addAttribute("sample", sampleDTO);
        model.addAttribute("donors", donorService.getAll().stream()
                .map(donor -> donor.getId() + " "  + donor.getSurname() + " " + donor.getName() + " " + donor.getPatronymic())
                .collect(Collectors.toList()));
        return editPage;
    }

    @Override
    @RequestMapping(value = "/sample/add", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("sample") BloodSampleDTO sample, BindingResult bindingResult, Model model) {
        if (!sample.getDonor().isBlank() && action.equals("edit")) {
            List<FieldError> errorsToKeep = bindingResult.getFieldErrors().stream()
                    .filter(fer -> !fer.getField().equals("donor")).toList();

            bindingResult = new BeanPropertyBindingResult(sample, "sample");

            for (FieldError fieldError : errorsToKeep) {
                bindingResult.addError(fieldError);
            }
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("donors", donorService.getAll().stream()
                    .map(donor -> donor.getId() + " "  + donor.getSurname() + " " + donor.getName() + " " + donor.getPatronymic())
                    .collect(Collectors.toList()));
            return editPage;
        }
        if (action.equals("add")) {
            bloodSampleService.add(sample);
            donorService.addDonation(Long.parseLong(sample.getDonor().split(" ")[0]));
        }
        if (action.equals("edit")) {
            bloodSampleService.update(sample);
        }
        return "redirect:/sample";
    }

    @Override
    @RequestMapping(value = "/sample/edit", method = RequestMethod.GET)
    public String edit(@RequestParam Long id, Model model) {
        action = "edit";
        model.addAttribute("sample", bloodSampleService.mapToDTO(bloodSampleService.findById(id)));
        model.addAttribute("donors", donorService.getAll().stream()
                .map(donor -> donor.getId() + " "  + donor.getSurname() + " " + donor.getName() + " " + donor.getPatronymic())
                .collect(Collectors.toList()));
        return editPage;
    }

    @Override
    @RequestMapping(value = "/sample/delete", method = RequestMethod.GET)
    public String delete(@RequestParam Long id) {
        bloodSampleService.delete(id);
        return "redirect:/sample";
    }

}

