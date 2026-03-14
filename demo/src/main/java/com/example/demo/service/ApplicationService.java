package com.example.demo.service;

import com.example.demo.entity.Application;
import com.example.demo.entity.Job;
import com.example.demo.entity.JobSeeker;
import com.example.demo.entity.Recruiter;
import com.example.demo.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public void save(Application application){
        applicationRepository.save(application);
    }

    public Application findById(Integer id) {
        Optional<Application> application = applicationRepository.findById(id);
        return application.orElseThrow();
    }

    public List<Application> findAll(){
        return applicationRepository.findAll();
    }

    public Optional<Application> findByJobAndSeeker(Job job, JobSeeker jobSeeker){
        return applicationRepository.findByJobSeekerAndJob(jobSeeker, job);
    }

    public List<Application> findByJob(Job job){
        return applicationRepository.findByJob(job);
    }

    public List<Application> findByJobSeeker(JobSeeker jobSeeker){
        return applicationRepository.findByJobSeeker(jobSeeker);
    }

    public List<Application> findByJobSeekerSorted(JobSeeker jobSeeker){
        return applicationRepository.findByJobSeekerOrderByCreationTimeDesc(jobSeeker);
    }
}
