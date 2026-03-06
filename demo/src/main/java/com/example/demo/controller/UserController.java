package com.example.demo.controller;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "registerPage";
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
            return "redirect:/jobSeekerDashboard";
        }
        return "redirect:/recruiterDashboard";
    }

    @GetMapping("/jobSeekerDashboard")
    public String getJobSeekerDashboard(){
        return "jobSeekerDashboardPage";
    }

    @GetMapping("/recruiterDashboard")
    public String getRecruiterDashboard(){
        return "recruiterDashboardPage";
    }
}
