package com.example.demo.service;

import com.example.demo.entity.Job;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.User;
import com.example.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void save(Job job){
        jobRepository.save(job);
    }

    public void remove(Job job){
        jobRepository.delete(job);
    }

    public Job findById(Integer id) {
        Optional<Job> job = jobRepository.findById(id);
        return job.orElse(null);
    }
    public List<Job> findAll(){
        return jobRepository.findAll();
    }

    public List<Job> findByRecruiter(Recruiter recruiter){
        return jobRepository.findByRecruiter(recruiter);
    }

    public List<Job> getJobs(String keyword, String sort) {
        Sort sorting;
        if(sort.equals("salary")){
            sorting = Sort.by("salary").descending();
        }
        else{
            sorting = Sort.unsorted();
        }

        return jobRepository.findByTitleContainingIgnoreCaseOrRecruiterCompanyContainingIgnoreCaseOrLocationContainingIgnoreCase(
          keyword, keyword, keyword, sorting
        );
    }
}
