package com.example.leavetracker.controller;

import com.example.leavetracker.model.Employee;
import com.example.leavetracker.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("employee", new Employee());
        return "admin";
    }

    @PostMapping("/admin/addEmployee")
    public String addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/admin";
    }
}
