package com.example.demo.repository;

import com.example.demo.entity.Application;
import com.example.demo.entity.Job;
import com.example.demo.entity.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findByJobSeekerAndJob(JobSeeker jobSeeker, Job job);
    List<Application> findByJob(Job job);
    List<Application> findByJobSeeker(JobSeeker jobSeeker);
    List<Application> findByJobSeekerOrderByCreationTimeDesc(JobSeeker jobSeeker);
    //the order is opposite to what you would expect
}
