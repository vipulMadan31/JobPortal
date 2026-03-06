package com.example.demo;

import com.example.demo.repository.RecruiterRepository;
import com.example.demo.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JobPortalApplicationTests {

	@Autowired
	private RecruiterService recruiterService;
	@Autowired
	private JobSeekerService jobSeekerService;
	@Autowired
	private JobService jobService;
	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationService applicationService;

	@Test
	void contextLoads() {
		System.out.println(recruiterService.findAll());
		System.out.println(jobSeekerService.findAll());
		System.out.println(jobService.findAll());
		System.out.println(userService.findAll());
		System.out.println(applicationService.findAll());

	}

}
