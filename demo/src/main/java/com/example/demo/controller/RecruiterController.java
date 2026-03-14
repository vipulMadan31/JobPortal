package com.example.demo.controller;

import com.example.demo.entity.Job;
import com.example.demo.entity.JobSeeker;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.User;
import com.example.demo.service.JobSeekerService;
import com.example.demo.service.JobService;
import com.example.demo.service.RecruiterService;
import com.example.demo.service.UserService;
import com.example.demo.util.CustomUserDetails;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class RecruiterController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobSeekerService jobSeekerService;

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

    @PostMapping("/addProfilePicture")
    public String addProfilePicture(@ModelAttribute("file") MultipartFile file,
                                    @AuthenticationPrincipal CustomUserDetails userDetails,
                                    Model model) throws IOException {
        //MultiPart file gives us methods like get size, get name, etc
        // get recruiter
        User user = userDetails.getUser();
        Recruiter recruiter = recruiterService.findByUser(user).get();
        // name the file
        String fileName = recruiter.getId()+".jpg";
        Path uploadPath = Paths.get("static/photos/Recruiter");
        Files.createDirectories(uploadPath);
        //this creates the directory if its not present
        Path filePath = uploadPath.resolve(fileName);
        //this combines upload path with filename
        file.transferTo(filePath);
        //the filename is written from memory to disk
        if(file.isEmpty()){
            return "redirect:/recruiterDashboard?imageError";
        }
        recruiter.setProfile(fileName);
        recruiterService.save(recruiter);
        return "redirect:/recruiterDashboard";

    }

    @GetMapping("/viewApplicantResume")
    public ResponseEntity<Resource> viewApplicantResume(@ModelAttribute("id") Integer jobSeekerId) throws MalformedURLException {
        JobSeeker jobSeeker = jobSeekerService.findById(jobSeekerId).get();
        String resume = jobSeeker.getResume();

        Path resumePath = Paths.get("static/photos/jobSeeker").resolve(resume);
        Resource resource = new UrlResource(resumePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF).
                body(resource);
    }

}
