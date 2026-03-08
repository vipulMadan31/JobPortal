package com.example.demo.controller;

import com.example.demo.entity.Job;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.User;
import com.example.demo.service.JobService;
import com.example.demo.service.RecruiterService;
import com.example.demo.service.UserService;
import com.example.demo.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RecruiterController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    private JobService jobService;

    @GetMapping("/postJob")
    public String postJob(Authentication authentication, Model model){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email).get();
        Job job = new Job();
        job.setRecruiter(recruiterService.findByUser(user).get());
        model.addAttribute("job", job);
        return "postJobPage";
    }

    @PostMapping("/saveJob")
    public String saveJob(@ModelAttribute("job") Job job, Model model){
        jobService.save(job);
        System.out.print(job);
        return "redirect:/recruiterDashboard";
    }

    @GetMapping("/seePostedJobs")
    public String seePostedJobs(@AuthenticationPrincipal CustomUserDetails userDetails, Model model){
        User user = userDetails.getUser();
        Recruiter recruiter = recruiterService.findByUser(user).get();
        model.addAttribute("jobs", jobService.findByRecruiter(recruiter));
        return "showJobsPage";
    }
}
