package com.example.demo.service;

import com.example.demo.entity.Recruiter;
import com.example.demo.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service 
public class RecruiterService {
    @Autowired
    private RecruiterRepository recruiterRepository;

    public RecruiterService(RecruiterRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }

    public void save(Recruiter recruiter){
        recruiterRepository.save(recruiter);
    }

    public Recruiter findById(Integer id) {
        Optional<Recruiter> recruiter = recruiterRepository.findById(id);
        return recruiter.orElseThrow();
    }

    public List<Recruiter> findAll(){
        return recruiterRepository.findAll();
    }
}
