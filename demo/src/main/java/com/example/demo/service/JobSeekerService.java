package com.example.demo.service;

import com.example.demo.entity.JobSeeker;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.User;
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

    public Optional<JobSeeker> findById(Integer id) {
        return jobSeekerRepository.findById(id);
    }

    public List<JobSeeker> findAll(){
        return jobSeekerRepository.findAll();
    }

    public Optional<JobSeeker> findByUser(User user){
        return jobSeekerRepository.findByUser(user);
    }


}
