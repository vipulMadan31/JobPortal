package com.example.demo.controller;
import com.example.demo.entity.JobSeeker;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.User;
import com.example.demo.service.JobSeekerService;
import com.example.demo.service.RecruiterService;
import com.example.demo.service.UserService;
import com.example.demo.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JobSeekerService jobSeekerService;
    @Autowired
    private RecruiterService recruiterService;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "registerPage";
    }

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    // very imp should be post
    @PostMapping("/save")
    public String save(@ModelAttribute("user") User user, Model model){
        //go back to register Page, and dont save user
        if(userService.isDuplicateEmail(user.getEmail())){
            model.addAttribute("emailError", "This email is already registered");
            return "registerPage";
        }
        System.out.println(user);
        userService.addNew(user);
        if(user.getRole().equals("ROLE_JOB_SEEKER")){
            boolean exists = (jobSeekerService.findByUser(user).isPresent());
            if(exists){
                model.addAttribute("jobSeeker", jobSeekerService.findByUser(user));
                return "jobSeekerDashboardPage";
            }
            else{
                JobSeeker jobSeeker = new JobSeeker();
                jobSeeker.setUser(user);
                model.addAttribute("jobSeeker", jobSeeker);
                return "jobSeekerCompleteRegistrationPage";
            }
        }
        else{
            boolean exists = (recruiterService.findByUser(user).isPresent());
            if(exists){
                model.addAttribute("recruiter", recruiterService.findByUser(user));
                return "recruiterDashboardPage";
            }
            else {
                Recruiter  recruiter = new Recruiter();
                recruiter.setUser(user);
                model.addAttribute("recruiter", recruiter);
                return "recruiterCompleteRegistrationPage";
            }
        }
    }

    @GetMapping("/jobSeekerDashboard")
    public String getJobSeekerDashboard(Model model, @AuthenticationPrincipal CustomUserDetails
            userDetails){
        User user = userDetails.getUser();
        JobSeeker jobSeeker = jobSeekerService.findByUser(user).get();
        model.addAttribute("jobSeeker", jobSeeker);
        return "jobSeekerDashboardPage";
    }

    @GetMapping("/recruiterDashboard")
    public String getRecruiterDashboard(Model model, @AuthenticationPrincipal CustomUserDetails
                                        userDetails){

        User user = userDetails.getUser();
        Recruiter recruiter = recruiterService.findByUser(user).get();
        model.addAttribute("recruiter", recruiter);
        return "recruiterDashboardPage";
    }

    @PostMapping("/saveJobSeeker")
    public String saveJobSeeker(@ModelAttribute("jobSeeker") JobSeeker jobSeeker, Model model){
        jobSeekerService.save(jobSeeker);
        model.addAttribute(jobSeeker);
        System.out.print(jobSeeker);
        return "jobSeekerDashboardPage";
    }

    @PostMapping("/saveRecruiter")
    public String saveRecruiter(@ModelAttribute("recruiter") Recruiter recruiter, Model model){
        recruiterService.save(recruiter);
        model.addAttribute(recruiter);
        System.out.print(recruiter);
        return "recruiterDashboardPage";
    }

}
