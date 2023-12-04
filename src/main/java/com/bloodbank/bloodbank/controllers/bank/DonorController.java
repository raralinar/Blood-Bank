package com.bloodbank.bloodbank.controllers.bank;

import com.bloodbank.bloodbank.dto.DonorDTO;
import com.bloodbank.bloodbank.model.bank.Donor;
import com.bloodbank.bloodbank.service.bank.DonorService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DonorController {
    static String action = "add";

    private final DonorService donorService;

    @Autowired
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @RequestMapping("/donors")
    public String init(Model model) {
        model.addAttribute("search", new Search());
        model.addAttribute("donors", donorService.getAll());
        return "admin/donors";
    }

    @GetMapping("/donors/add")
    public String addDonor(Model model) {
        action = "add";
        if (!model.containsAttribute("donor")) {
            DonorDTO donorDTO = new DonorDTO();
            model.addAttribute("donor", donorDTO);
        }
        return "admin/edit";
    }

    @PostMapping("/donors/add")
    public String addDonor(@Valid @ModelAttribute("donor") DonorDTO donorDTO, BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            return "admin/edit";
        }
        if (action.equals("add"))
            donorService.add(donorDTO);
        if (action.equals("edit")) {
            donorService.update(donorDTO);
        }
        model.addAttribute("search", new Search());
        model.addAttribute("donors", donorService.getAll());
        return "admin/donors";
    }

    @RequestMapping(value = "/donors/delete", method = RequestMethod.GET)
    public String delete(Model model,
                         @RequestParam Long id) {
        donorService.delete(id);
        model.addAttribute("search", new Search());
        model.addAttribute("donors", donorService.getAll());
        return "admin/donors";
    }

    @RequestMapping(value = "/donors/edit", method = RequestMethod.GET)
    public String edit(@RequestParam Long id, Model model) {
        action = "edit";
        model.addAttribute("donor", donorService.maptoDTO(donorService.findById((long) id)));
        model.addAttribute("id", id);
        return "admin/edit";
    }

    @RequestMapping(value = "/donors/search", method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("search", new Search());
        return "admin/donors";
    }

    @RequestMapping(value = "/donors/search", method = RequestMethod.POST)
    public String search(@Valid @ModelAttribute(name = "search") Search search, BindingResult bindingResult, Model model) throws InterruptedException {
        List<String> donors = donorService.findByNames().stream()
                .map(donor -> donor[0] + " " + donor[1] + " " + donor[2]).toList()
                .stream().filter(donor -> donor.toUpperCase().contains(search.getString().toUpperCase()))
                .collect(Collectors.toList());
        donors = donors.stream()
                .distinct()
                .collect(Collectors.toList());
        List<Donor> donorList = new ArrayList<>();
        for (String s : donors) {
            String[] nameParts = s.split(" ");
            donorList.addAll(donorService.findByNames(nameParts[0], nameParts[1], nameParts[2]));
        }
        model.addAttribute("donors", donorList);
        return "admin/donors";
    }


    @Getter
    @Setter
    @NoArgsConstructor
    static
    class Search {
        private String string;
    }
}
