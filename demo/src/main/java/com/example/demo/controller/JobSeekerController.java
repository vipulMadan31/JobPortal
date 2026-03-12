package com.example.demo.controller;

import com.example.demo.entity.JobSeeker;
import com.example.demo.entity.User;
import com.example.demo.service.JobSeekerService;
import com.example.demo.util.CustomUserDetails;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    @PostMapping("/uploadResume")
    public String uploadResume(@ModelAttribute("file") MultipartFile file, @AuthenticationPrincipal
                               CustomUserDetails userDetails) throws IOException {

        User user = userDetails.getUser();
        JobSeeker jobSeeker = jobSeekerService.findByUser(user).get();

        System.out.println(file.isEmpty());
        System.out.println(file.getSize());

        String filename = jobSeeker.getId() + ".pdf";
        Path uploadPath = Paths.get("static/photos/JobSeeker");

        Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(filename);

        file.transferTo(filePath);

        jobSeeker.setResume(filename);

        jobSeekerService.save(jobSeeker);

        return "redirect:/jobSeekerDashboard";
    }

    @GetMapping("/viewResume")
    public ResponseEntity<Resource> viewResume(@AuthenticationPrincipal CustomUserDetails userDetails)
            throws MalformedURLException {
        User user = userDetails.getUser();
        JobSeeker jobSeeker = jobSeekerService.findByUser(user).get();

        Path resumePath = Paths.get("static/photos/JobSeeker").resolve(jobSeeker.getResume());
        //convert resume string to path
        Resource resource = new UrlResource(resumePath.toUri());
        //and convert to url
        return ResponseEntity.ok() // say request successful
                .contentType(MediaType.APPLICATION_PDF) // type = pdf
                .body(resource); // its body is resource
    }
}
