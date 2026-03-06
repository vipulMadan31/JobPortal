package com.example.demo.service;

import com.example.demo.entity.JobSeeker;
import com.example.demo.entity.Recruiter;
import com.example.demo.repository.JobSeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobSeekerService {
    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    public JobSeekerService(JobSeekerRepository jobSeekerRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
    }

    public void save(JobSeeker jobSeeker){
        jobSeekerRepository.save(jobSeeker);
    }

    public JobSeeker findById(Integer id) {
        Optional<JobSeeker> jobSeeker = jobSeekerRepository.findById(id);
        return jobSeeker.orElseThrow();
    }

    public List<JobSeeker> findAll(){
        return jobSeekerRepository.findAll();
    }
}
