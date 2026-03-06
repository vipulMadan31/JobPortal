package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        System.out.println("The username " + username + " is logged in.");

        boolean hasJobSeekerRole = authentication.getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_JOB_SEEKER"));

        // ie if the successful user is jobSeeker

        boolean hasRecruiterRole = authentication.getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_RECRUITER"));

        if(hasRecruiterRole){
            response.sendRedirect("/recruiterDashboard");
        }
        if (hasJobSeekerRole) {
            response.sendRedirect("/jobSeekerDashboard");
        }
    }
}
