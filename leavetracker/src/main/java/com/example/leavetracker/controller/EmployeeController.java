package com.example.leavetracker.controller;

import com.example.leavetracker.model.Employee;

import com.example.leavetracker.model.Leave;
import com.example.leavetracker.repository.EmployeeRepository;
import com.example.leavetracker.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.validation.Valid;


import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @GetMapping("/employee")
    public String employee(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee";
    }

    @GetMapping("/employee/{id}")
    public String employeeHome(@PathVariable String id, Model model) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        model.addAttribute("employee", employee);
        List<Leave> leaves = leaveRepository.findByEmployeeId(id);
        model.addAttribute("leaves", leaves);
        model.addAttribute("leave", new Leave());
        return "employee_home";
    }

    @PostMapping("/employee/{id}/applyLeave")
    public String applyLeave(@PathVariable String id, @Valid Leave leave, BindingResult result, Model model) {
        if (result.hasErrors()) {
            Employee employee = employeeRepository.findById(id).orElse(null);
            model.addAttribute("employee", employee);
            List<Leave> leaves = leaveRepository.findByEmployeeId(id);
            model.addAttribute("leaves", leaves);
            return "employee_home";
        }
        try {
            leave.setEmployeeId(id);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            leave.setStartDate(sdf.parse(sdf.format(leave.getStartDate())));
            leave.setEndDate(sdf.parse(sdf.format(leave.getEndDate())));
            leaveRepository.save(leave);
            return "redirect:/employee/" + id;
        } catch (ParseException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to apply leave. Please try again.");
            Employee employee = employeeRepository.findById(id).orElse(null);
            model.addAttribute("employee", employee);
            List<Leave> leaves = leaveRepository.findByEmployeeId(id);
            model.addAttribute("leaves", leaves);
            return "employee_home";
        }
    }
}
