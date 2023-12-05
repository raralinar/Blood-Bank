package com.bloodbank.bloodbank.controllers.bank;

import com.bloodbank.bloodbank.service.bank.InactiveDonorService;

//@Controller
public class InactiveDonorController {
    private final InactiveDonorService inactiveDonorService;

    public InactiveDonorController(InactiveDonorService inactiveDonorService) {
        this.inactiveDonorService = inactiveDonorService;
    }

//    @GetMapping("/inactiveDonor")
//    public List<InactiveDonor> getAll() {
//        return inactiveDonorService.getAll();
//    }
}
