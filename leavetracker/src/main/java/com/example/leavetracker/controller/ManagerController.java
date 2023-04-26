package com.example.leavetracker.controller;


import com.example.leavetracker.model.Leave;
import com.example.leavetracker.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ManagerController {

    @Autowired
    private LeaveRepository leaveRepository;

    @GetMapping("/manager")
    public String showAllLeaves(Model model) {
        List<Leave> leaves = leaveRepository.findAll();
        model.addAttribute("leaves", leaves);
        return "manager";
    }

    @GetMapping("/manager/validate/{id}")
    public String validateLeave(@PathVariable String id, Model model) {
        Optional<Leave> leaveOptional = leaveRepository.findById(id);
        if (leaveOptional.isPresent()) {
            Leave leave = leaveOptional.get();
            model.addAttribute("leave", leave);
            return "validate_leave";
        } else {
            return "redirect:/manager";
        }
    }

    @PostMapping("/manager/validate/{id}")
    public String processLeaveValidation(@PathVariable String id, @RequestParam String status) {
        Optional<Leave> leaveOptional = leaveRepository.findById(id);
        if (leaveOptional.isPresent()) {
            Leave leave = leaveOptional.get();
            leave.setStatus(status);
            leaveRepository.save(leave);
        }
        return "redirect:/manager";
    }
}
