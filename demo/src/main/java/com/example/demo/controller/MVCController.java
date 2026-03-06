package com.example.demo.controller;

import com.example.demo.entity.Recruiter;
import com.example.demo.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MVCController {

    @Autowired
    private RecruiterService recruiterService;

    @GetMapping("/demo")
    public String display(Model model ){
        model.addAttribute("recruiterList", recruiterService.findAll());
        return "demo";
    }
}
