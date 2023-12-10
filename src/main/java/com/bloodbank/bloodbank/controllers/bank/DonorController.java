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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DonorController implements IController<DonorController.Search, DonorDTO> {
    static String action = "add", mainPage = "admin/donor/donors", editPage = "admin/donor/edit";

    private final DonorService donorService;

    @Autowired
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping("/donors")
    public String init(Model model) {
        model.addAttribute("search", new Search());
        model.addAttribute("donors", donorService.getAll());
        return mainPage;
    }

    @GetMapping("/donors/add")
    public String add(Model model) {
        action = "add";
        if (!model.containsAttribute("donor")) {
            DonorDTO donorDTO = new DonorDTO();
            model.addAttribute("donor", donorDTO);
        }
        return editPage;
    }

    @PostMapping("/donors/add")
    public String add(@Valid @ModelAttribute("donor") DonorDTO donorDTO, BindingResult result,
                           Model model) {
        if (!donorDTO.getPhone().isBlank() && action.equals("edit")) {
            List<FieldError> errorsToKeep = result.getFieldErrors().stream()
                    .filter(fer -> !fer.getField().equals("phone")).toList();

            result = new BeanPropertyBindingResult(donorDTO, "donor");

            for (FieldError fieldError : errorsToKeep) {
                result.addError(fieldError);
            }
        }
        if (result.hasErrors()) {
            return editPage;
        }
        if (action.equals("add"))
            donorService.add(donorDTO);
        if (action.equals("edit")) {
            donorService.update(donorDTO);
        }
        return "redirect:/donors";
    }

    @RequestMapping(value = "/donors/delete", method = RequestMethod.GET)
    public String delete(@RequestParam Long id) {
        donorService.delete(id);
        return "redirect:/donors";
    }

    @RequestMapping(value = "/donors/edit", method = RequestMethod.GET)
    public String edit(@RequestParam Long id, Model model) {
        action = "edit";
        model.addAttribute("donor", donorService.maptoDTO(donorService.findById(id)));
        return editPage;
    }

    @RequestMapping(value = "/donors/search")
    public String search(@Valid @ModelAttribute(name = "search") Search search, Model model) {
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
        return mainPage;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    static
    class Search {
        private String string;
    }
}
