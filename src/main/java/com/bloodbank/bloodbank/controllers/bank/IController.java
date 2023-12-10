package com.bloodbank.bloodbank.controllers.bank;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

public interface IController<SearchType, DTO> {
    String search(@Valid @ModelAttribute SearchType t, Model model);

    String add(Model model);

    String add(DTO dto, BindingResult bindingResult, Model model);

    String edit(@RequestParam Long id, Model model);

    String delete(@RequestParam Long id);
}
