package com.example.demo.controller;

import com.example.demo.entity.Application;
import com.example.demo.entity.Job;
import com.example.demo.entity.JobSeeker;
import com.example.demo.entity.User;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.EmailService;
import com.example.demo.service.JobSeekerService;
import com.example.demo.service.JobService;
import com.example.demo.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    private EmailService emailService;

    @Autowired
    private JobService jobService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private JobSeekerService jobSeekerService;


    @GetMapping("/seeJobs")
    public String seeJobs(@ModelAttribute("keyword") String keyword, @ModelAttribute("sort") String sort, Model model){
        List<Job> jobs = jobService.getJobs(keyword, sort);
        model.addAttribute("jobs", jobs);
        return "jobsDisplayPage";
    }

    @Async
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
            String body="Dear " + user.getName() + ",\n" + "Your application for " + job.getTitle() +" is successful. You can check your mail";
            emailService.sendEmail(user.getEmail(), "Application Successful", body);
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

    @Async // adding async ensures that even when mail is being sent, code flow does not stop
    //and goes on
    @PostMapping("/updateApplicationStatus")
    public String updateApplicationStatus(@RequestParam Integer applicationId,
                                          @RequestParam String status){

        Application application = applicationService.findById(applicationId);

        application.setStatus(status);


        applicationService.save(application);

        User user = application.getJobSeeker().getUser();
        String body = "Dear " + user.getName() + ",\n" + "Your application status for the Job "
                + application.getJob().getTitle() + " has been updated to " + status;
        emailService.sendEmail(user.getEmail(), "Application Status updated", body);

        return "redirect:/seeApplications?id=" + application.getJob().getId();
    }

    @GetMapping("/search")
    public String getSearchResults(Model model){
        return "searchPage";
    }

}
