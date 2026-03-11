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
		userService.remove("vipul23600@iiitd.ac.in");
	}

}
