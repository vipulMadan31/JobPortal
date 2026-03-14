package com.example.demo.controller;

import com.example.demo.entity.Application;
import com.example.demo.entity.Job;
import com.example.demo.entity.JobSeeker;
import com.example.demo.entity.User;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.JobSeekerService;
import com.example.demo.service.JobService;
import com.example.demo.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private JobSeekerService jobSeekerService;

    @GetMapping("/seeJobs")
    public String seeJobs(Model model){
        model.addAttribute("jobs", jobService.findAll());
        return "jobsDisplayPage";
    }

    @PostMapping("/applyJob")
    public String applyJob(@ModelAttribute("jobId") Integer id,
                           @AuthenticationPrincipal CustomUserDetails userDetails, Model model){
        Job job = jobService.findById(id);
        User user = userDetails.getUser();
        JobSeeker jobSeeker = jobSeekerService.findByUser(user).get();
        if(applicationService.findByJobAndSeeker(job, jobSeeker).isPresent()){
            model.addAttribute("message", "You have already applied");
        }
        else{
            Application application = new Application("inProcess", LocalDateTime.now(), job, jobSeeker );
            applicationService.save(application);
            System.out.println(application);
            model.addAttribute("message", "Application Successful");
        }
        return "appliedPage";
    }

    @GetMapping("/seeApplications")
    public String seeApplications(@ModelAttribute("id") Integer id, Model model){
        Job job = jobService.findById(id);
        List<Application> applications = applicationService.findByJob(job);
        model.addAttribute("applications", applications);
        return "seeApplicationsPage";
    }

    @PostMapping("/updateApplicationStatus")
    public String updateApplicationStatus(@RequestParam Integer applicationId,
                                          @RequestParam String status){

        Application application = applicationService.findById(applicationId);

        application.setStatus(status);

        applicationService.save(application);

        return "redirect:/seeApplications?id=" + application.getJob().getId();
    }
}
